package org.appa.planning.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.appa.planning.bo.DemandeAbsence;
import org.appa.planning.bo.StatutAbsence;
import org.appa.planning.bo.TypeAbsence;
import org.appa.planning.bo.UserRole;
import org.appa.planning.bo.Utilisateur;
import org.appa.planning.repository.AbsenceRepository;
import org.appa.planning.repository.UtilisateurRepository;
import org.appa.planning.service.mail.IMailService;
import org.appa.planning.vo.CompteurConges;
import org.appa.planning.vo.SessionUtilisateur;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de gestion des absences (RTT, Congés)
 * 
 * @author jroynett
 *
 */
@Service
@RemotingDestination("AbsenceService")
public class AbsenceService {

	@Autowired
	private AbsenceRepository absenceRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private IMailService mailService;

	@Autowired
	private GoogleCalendarService  calendarService;


	/**
	 * permet de poser un nouvelle absence
	 * @throws AbsenceException
	 */
	@Transactional
	public void poserAbsence(DemandeAbsence absence) throws Exception{

		absence.setStatut(StatutAbsence.POSE);

		//recuperation de l'objet utilisateur
		Utilisateur utilisateur = utilisateurRepository.findOne(absence.getUtilisateur().getId());
		absence.setUtilisateur(utilisateur);

		//verification des dates pour traiter le chevauchement de périodes pour les RTT
		if(absence.getType().equals(TypeAbsence.RTT)
				&& isPeriodeCouranteRTT(absence.getDateDebut()) && !isPeriodeCouranteRTT(absence.getDateFin())){

			//division en 2 absences (1 par période)
			DemandeAbsence absencePeriodeCourante = absence.cloneAbsence();
			DemandeAbsence absencePeriodeSuivante = absence.cloneAbsence();
			absencePeriodeCourante.setDateFin(getDateFinPeriodeRTT(absence.getDateDebut()));
			absencePeriodeSuivante.setDateDebut(getDateDebutPeriodeRTT(absence.getDateFin()));

			poserAbsence(absencePeriodeSuivante);
			//return poserAbsence(absencePeriodeCourante);
		}

		//verification des dates pour traiter le chevauchement de périodes pour les Congés
		if((absence.getType().equals(TypeAbsence.CONGE) || absence.getType().equals(TypeAbsence.AUTRE))
				&& isPeriodeCouranteConges(absence.getDateDebut()) && !isPeriodeCouranteConges(absence.getDateFin())){

			//division en 2 absences (1 par période)
			DemandeAbsence absencePeriodeCourante = absence.cloneAbsence();
			DemandeAbsence absencePeriodeSuivante = absence.cloneAbsence();
			absencePeriodeCourante.setDateFin(getDateFinPeriodeConges(absence.getDateDebut()));
			absencePeriodeSuivante.setDateDebut(getDateDebutPeriodeConges(absence.getDateFin()));

			poserAbsence(absencePeriodeSuivante);
			//return poserAbsence(absencePeriodeCourante);
		}

		//date du jour
		Date now = new Date();
		now = DateUtils.truncate(now, Calendar.DATE);

		//recupération des infos utilisateur
		SessionUtilisateur session = loadSessionUtilisateur(absence.getUtilisateur().getId(),absence.getDateDebut());
		CompteurConges compteurs = session.getCompteurs();

		//verification des chevauchements de conges / rtt

		long valDebutTime = absence.getDateDebut().getTime();
		//on ajoute 12H si départ dans l'après midi (12 * 3600 * 1000)
		valDebutTime += (absence.getDebutPM())?43200000:0;
		//permet de simplifier les comparaisons
		valDebutTime += 1;

		long valFinTime = DateUtils.addDays(absence.getDateFin(),1).getTime();
		//on retire 12H si départ dans l'après midi (12 * 3600 * 1000)
		valFinTime -= (absence.getFinAM())?43200000:0;
		valFinTime -= 1;

		List<DemandeAbsence> absencesPeriode = absenceRepository.findByUtilisateur(utilisateur.getId(), absence.getDateDebut(), absence.getDateFin());

		for (DemandeAbsence absencePeriode : absencesPeriode) {

			long tmpValDebutTime = absencePeriode.getDateDebut().getTime();
			tmpValDebutTime += (absencePeriode.getDebutPM())?43200000:0;

			long tmpValFinTime = DateUtils.addDays(absencePeriode.getDateFin(),1).getTime();
			tmpValFinTime -= (absencePeriode.getFinAM())?43200000:0;

			if((valDebutTime >= tmpValDebutTime && valDebutTime <= tmpValFinTime) ||
					(valFinTime >= tmpValDebutTime && valFinTime <= tmpValFinTime) ||
					(valDebutTime <= tmpValDebutTime && valFinTime >= tmpValFinTime)){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				throw new AbsenceException("L'absence ne peut etre posee car elle se chevauche avec l'absence suivante " +
						"[ "+absencePeriode.getType() + " du " + sdf.format(absencePeriode.getDateDebut()) + " au " + sdf.format(absencePeriode.getDateFin()) + "]");
			}
		}

		//nombre de jours d'écart entre datedeb et dateFin
		float nbJours = getNbJoursForAbsence(absence);

		//cas des RTT
		if(absence.getType().equals(TypeAbsence.RTT)){
			if(compteurs.getRttAposer() < nbJours){
				throw new AbsenceException("Le nombre de RTT restants est insuffisant");
			}
		}

		//cas des Congés
		if(absence.getType().equals(TypeAbsence.CONGE)){
			if(compteurs.getCongesAposer() < nbJours){
				throw new AbsenceException("Le nombre de conges restants est insuffisant");
			}
		}

		//si date debut absence < date du jour
		if(absence.getDateDebut().before(now) || absence.getUtilisateur().getRole().equals(UserRole.responsable)){
			//validation automatique
			absence.setStatut(StatutAbsence.VALIDE);
		}

		//sauvegarde de l'absence
		absenceRepository.saveAndFlush(absence);

		//envoi du mail au responsable
		if(!absence.getStatut().equals(StatutAbsence.VALIDE)){
			mailService.notifierCreationAbsence(absence);
		}

		//création de l'événement dans la calendrier google
		sendToGoogleCalendar(utilisateur, absence, false);
	}

	/**
	 * permet de valider une absence (par le responsable)
	 * @param id l'id de l'absence
	 * @throws Exception
	 */
	@Transactional
	public void validerAbsence(Long id) throws Exception{

		DemandeAbsence absence = absenceRepository.findOne(id);

		if(absence == null){
			throw new AbsenceException("l'absence a été annulee");
		}

		if(!absence.getStatut().equals(StatutAbsence.POSE)){
			throw new AbsenceException("l'absence ne peut pas être validee car son statut actuel est " + absence.getStatut());
		}

		absence.setStatut(StatutAbsence.VALIDE);
		absenceRepository.save(absence);

		//maj de l'événement dans la calendrier google
		sendToGoogleCalendar(absence.getUtilisateur(), absence, true);
	}

	/**
	 * permet de refuser une absence (par le responsable)
	 * @param id l'id de l'absence
	 * @throws AbsenceException
	 */
	@Transactional
	public void refuserAbsence(Long id) throws Exception{

		DemandeAbsence absence = absenceRepository.findOne(id);

		if(absence == null){
			throw new AbsenceException("l'absence a été annulee");
		}

		if(!absence.getStatut().equals(StatutAbsence.POSE)){
			throw new AbsenceException("l'absence ne peut pas etre validee car son statut actuel est " + absence.getStatut());
		}

		absence.setStatut(StatutAbsence.REFUSE);
		absenceRepository.save(absence);

		//maj du calendrier google
		removeFromGoogleCalendar(absence.getUtilisateur(), absence);

	}

	/**
	 * permet d'annuler une absence
	 * @param id l'id de l'absence
	 * @throws Exception
	 */
	@Transactional
	public void annulerAbsence(Long id) throws Exception{

		DemandeAbsence absence = absenceRepository.findOne(id);

		if(absence == null){
			throw new AbsenceException("l'absence a ete supprimee");
		}

		//suppression de l'absence
		absenceRepository.delete(absence);

		//envoi mail au responsable
		if(absence.getStatut().equals(StatutAbsence.VALIDE) && !absence.getUtilisateur().getRole().equals(UserRole.responsable)){
			mailService.notifierSuppressionAbsence(absence);
		}

		//maj du calendrier google
		removeFromGoogleCalendar(absence.getUtilisateur(), absence);
	}

	/**
	 * permet de recupérer les dernières infos d'absences lié à un utilisateur et de calculer les nb de jours de congés/rtt restant
	 * @param utilisateur
	 * @return
	 * @throws AbsenceException
	 */
	public SessionUtilisateur loadSessionUtilisateur(Long utilisateurId, Date dateAbsence) throws Exception{

		//date du jour
		Date now = new Date();
		now = DateUtils.truncate(now, Calendar.DATE);

		Utilisateur utilisateur = utilisateurRepository.findOne(utilisateurId);

		SessionUtilisateur session = new SessionUtilisateur();
		session.setUtilisateur(utilisateur);

		//recuperation des rtt de l'utilisateur sur la période liée à la date d'absence souhaitée
		List<DemandeAbsence> rtts = absenceRepository.findByUtilisateur(utilisateur.getId(), getDateDebutPeriodeRTT(dateAbsence), getDateFinPeriodeRTT(dateAbsence), TypeAbsence.RTT);

		//recuperation des conges de l'utilisateur sur la période liée à la date d'absence souhaitée
		List<DemandeAbsence> conges = absenceRepository.findByUtilisateur(utilisateur.getId(), getDateDebutPeriodeConges(dateAbsence), getDateFinPeriodeConges(dateAbsence), TypeAbsence.CONGE);

		//recuperation des conges de l'utilisateur sur la période liée à la date d'absence souhaitée
		List<DemandeAbsence> autresConges = absenceRepository.findByUtilisateur(utilisateur.getId(), now, DateUtils.addYears(now, 1), TypeAbsence.AUTRE);

		//init des compteurs par rapport aux infos utilisateurs (congés/RTT de l'année + deltas éventuels)
		CompteurConges compteurs = new CompteurConges();
		session.setCompteurs(compteurs);
		compteurs.setCongesAposer(utilisateur.getNbConges());
		compteurs.setRttAposer(utilisateur.getNbRTT());

		//prise en compte des deltas si la date de l'absence se trouve dans la période courante
		if(isPeriodeCouranteConges(dateAbsence)){
			compteurs.setCongesAposer(compteurs.getCongesAposer() + utilisateur.getDeltaJoursConges());
		}
		if(isPeriodeCouranteRTT(dateAbsence)){
			compteurs.setRttAposer(compteurs.getRttAposer() + utilisateur.getDeltaJoursRTT());
		}

		//maj des compteurs par rapport à la liste des absences
		//TODO gérer les cas de 80%
		for (DemandeAbsence rtt : rtts) {
			if(!rtt.getStatut().equals(StatutAbsence.REFUSE)){
				float nbJours = getNbJoursForAbsence(rtt);
				compteurs.setRttAposer(compteurs.getRttAposer() - nbJours);
				if(rtt.getStatut().equals(StatutAbsence.POSE)){
					compteurs.setRttEnAttente(compteurs.getRttEnAttente() + nbJours);
				}
			}
			if(rtt.getDateDebut().after(now)){
				session.getAbsences().add(rtt);
			}
		}

		for (DemandeAbsence conge : conges) {
			if(!conge.getStatut().equals(StatutAbsence.REFUSE)){
				float nbJours = getNbJoursForAbsence(conge);
				compteurs.setCongesAposer(compteurs.getCongesAposer() - nbJours);
				if(conge.getStatut().equals(StatutAbsence.POSE)){
					compteurs.setCongesEnAttente(compteurs.getCongesEnAttente() + nbJours);
				}
			}
			if(conge.getDateDebut().after(now)){
				session.getAbsences().add(conge);
			}
		}

		for (DemandeAbsence conge : autresConges) {
			if(conge.getDateDebut().after(now)){
				session.getAbsences().add(conge);
			}
		}

		Collections.sort(session.getAbsences());

		//ajout des absences sur la période suivante
		List<DemandeAbsence> rttFuturs = absenceRepository.findByUtilisateur(utilisateur.getId(), DateUtils.addDays(getDateFinPeriodeRTT(dateAbsence),1), TypeAbsence.RTT);
		List<DemandeAbsence> congesFuturs = absenceRepository.findByUtilisateur(utilisateur.getId(), DateUtils.addDays(getDateFinPeriodeConges(dateAbsence),1), TypeAbsence.CONGE);
		List<DemandeAbsence> autresCongesFuturs = absenceRepository.findByUtilisateur(utilisateur.getId(), DateUtils.addDays(getDateFinPeriodeConges(dateAbsence),1), TypeAbsence.AUTRE);
		session.getAbsencesFutures().addAll(rttFuturs);
		session.getAbsencesFutures().addAll(congesFuturs);
		session.getAbsencesFutures().addAll(autresCongesFuturs);
		Collections.sort(session.getAbsencesFutures());

		return session;
	}

	private float getNbJoursForAbsence(DemandeAbsence absence) throws Exception{

		//nombre de jours d'écart entre datedeb et dateFin
		float nbJours = Days.daysBetween(new DateTime(absence.getDateDebut()),new DateTime(absence.getDateFin())).getDays();
		nbJours += 1;

		if(absence.getDebutPM()){
			nbJours -= 0.5;
		}
		if(absence.getFinAM()){
			nbJours -= 0.5;
		}

		//recup des jours feries
		List<Date> joursFeries = null;
		try {
			joursFeries = calendarService.getJourFeries();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		//les samedis et dimanche ne sont pas pris en compte
		//supprimer les jours feries
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(absence.getDateDebut());
		//gestion des dates différentes dans le cloud
		while(!DateUtils.isSameDay(calendar.getTime(),absence.getDateFin())){
			//System.out.println("!!!! " + calendar.getTime() + ">" + calendar.get(Calendar.DAY_OF_WEEK));
			if(calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7){
				nbJours -= 1;
			}
			if(joursFeries.contains(calendar.getTime())){
				nbJours -= 1;
			}
			calendar.add(Calendar.DATE, 1);
		}

		return nbJours;
	}

	private Date getDateDebutPeriodeConges(Date date){
		//doit correspondre au 1 juin de l'année courante ou de l'année précédente si date < mois juin
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		if(calendar.get(Calendar.MONTH) < 5){
			calendar.add(Calendar.YEAR, -1);
		}
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	private Date getDateFinPeriodeConges(Date date){
		//doit correspondre au 31 mai de l'année courante ou de l'année suivante si date > mois juin
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		if(calendar.get(Calendar.MONTH) >= 5){
			calendar.add(Calendar.YEAR, 1);
		}
		calendar.set(Calendar.MONTH, 4);
		calendar.set(Calendar.DATE, 31);
		return calendar.getTime();
	}

	private boolean isPeriodeCouranteConges(Date date){
		Date dateFinPeriodeCourante = getDateFinPeriodeConges(DateUtils.truncate(new Date(), Calendar.DATE));
		//ajout d'1 jur car la comparaison des dates doit être <= et la méthode before n'a pas ce comportement par défaut
		return date.before(DateUtils.addSeconds(dateFinPeriodeCourante, 1));
	}

	private Date getDateDebutPeriodeRTT(Date date){
		//doit correspondre au 1 janvier de l'année courante
		return DateUtils.truncate(new Date(), Calendar.YEAR);
	}

	private Date getDateFinPeriodeRTT(Date date){
		//doit correspondre au 31 decembre de l'année courante
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DATE, 31);
		return calendar.getTime();
	}

	private boolean isPeriodeCouranteRTT(Date date){
		Date dateFinPeriodeCourante = getDateFinPeriodeRTT(DateUtils.truncate(new Date(), Calendar.DATE));
		//ajout d'1 jur car la comparaison des dates doit être <= et la méthode before n'a pas ce comportement par défaut

		return date.before(DateUtils.addSeconds(dateFinPeriodeCourante, 1));
	}

	private void sendToGoogleCalendar(Utilisateur utilisateur, DemandeAbsence absence, boolean update) throws Exception{

		Date dateDebutEvent = absence.getDateDebut();
		if(absence.getDebutPM()){
			dateDebutEvent = DateUtils.setHours(dateDebutEvent, 14);
		}
		Date dateFinEvent = absence.getDateFin();
		if(absence.getFinAM()){
			dateFinEvent = DateUtils.setHours(dateFinEvent, 14);
		}else{
			dateFinEvent = DateUtils.addDays(dateFinEvent, 1);
		}

		if(update){
			String oldTitre = absence.getLibelleType().toString() + "?";
			String titre = absence.getLibelleType().toString();
			calendarService.updateEvent(utilisateur.getEmail(), dateDebutEvent, dateFinEvent, oldTitre, titre, "");
		}else{
			String titre = absence.getLibelleType().toString();
			titre += (absence.getStatut().equals(StatutAbsence.VALIDE))?"":"?";
			calendarService.createEvent(utilisateur.getEmail(), dateDebutEvent, dateFinEvent, titre, "");
		}
	}

	private void removeFromGoogleCalendar(Utilisateur utilisateur, DemandeAbsence absence) throws Exception{

		Date dateDebutEvent = absence.getDateDebut();
		if(absence.getDebutPM()){
			dateDebutEvent = DateUtils.setHours(dateDebutEvent, 14);
		}
		Date dateFinEvent = absence.getDateFin();
		if(absence.getFinAM()){
			dateFinEvent = DateUtils.setHours(dateFinEvent, 14);
		}else{
			dateFinEvent = DateUtils.addDays(dateFinEvent, 1);
		}

		String titre = absence.getLibelleType().toString();
		calendarService.deleteEvent(utilisateur.getEmail(), dateDebutEvent, dateFinEvent, titre);

	}

	/**
	 * permet de récupérer la liste des absences sur une période donée
	 * @param utilisateurId
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 * @throws Exception
	 */
	public List<DemandeAbsence> loadAbsences(Long userId, Date dateDebut, Date dateFin) throws Exception{

		List<DemandeAbsence> absences = null;

		if(userId == null){
			absences = this.absenceRepository.findAll(dateDebut, dateFin);
		}
		else{
			absences = this.absenceRepository.findByUtilisateur(userId, dateDebut, dateFin);
		}

		//calcul du nb de jours par absence
		for (DemandeAbsence absence : absences) {
			//dans le cas des absences qui sortent de la période, on 'coupe' les périodes d'absences concernées
			//le calcul du nb de jour devant se faire sur la période sélectionnée uniquement
			if(absence.getDateDebut().before(dateDebut)){
				absence.setDateDebut(dateDebut);
			}
			if(absence.getDateFin().after(dateFin)){
				absence.setDateFin(dateFin);
			}

			absence.setNbJours(getNbJoursForAbsence(absence));
		}

		return absences;

	}
}
