package org.appa.planning.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;
import org.appa.planning.bo.DejeunerExterne;
import org.appa.planning.bo.DemandeAbsence;
import org.appa.planning.bo.JourSemaine;
import org.appa.planning.bo.Projet;
import org.appa.planning.bo.SaisieTemps;
import org.appa.planning.bo.StatutAbsence;
import org.appa.planning.bo.Utilisateur;
import org.appa.planning.repository.AbsenceRepository;
import org.appa.planning.repository.DejeunerExterneRepository;
import org.appa.planning.repository.ProjectRepository;
import org.appa.planning.repository.SaisieTempsRepository;
import org.appa.planning.repository.UtilisateurRepository;
import org.appa.planning.vo.SaisieTempsProjet;
import org.appa.planning.vo.SaisieTempsProjetJour;
import org.appa.planning.vo.SaisieTempsSemaine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des saisie de temps sur les projets
 * 
 * @author jroynett
 *
 */
@Service
@RemotingDestination("SaisieTempsService")
public class SaisieTempsService {

	@Autowired
	private SaisieTempsRepository saisieTempsRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DejeunerExterneRepository dejeunerExterneRepository;

	@Autowired
	private AbsenceRepository  absenceRepository;

	@Autowired
	private GoogleCalendarService  calendarService;


	/**
	 * sauvegarde du planning hebo
	 * @param login le login de l'utilisateur
	 * @param planning le planning hebdo � sauvegarder
	 */
	public void saveSaisieTemps(String login, SaisieTempsSemaine planning){

		Date dateDebutSemaine = planning.getDate();
		Date dateFinSemaine = DateUtils.addDays(dateDebutSemaine, 7);

		//r�cup�ration de l'utilisateur
		Utilisateur user = utilisateurRepository.findByLogin(login);

		//suppression des temps saisis du lundi au dimanche sur la semaine
		List<SaisieTemps> saisies = saisieTempsRepository.findByUtilisateur(user.getId(), dateDebutSemaine, dateFinSemaine);
		saisieTempsRepository.deleteInBatch(saisies);

		//sauvegarde des nouveaux temps
		for (SaisieTempsProjet saisieProjet : planning.getProjets()) {
			for (SaisieTempsProjetJour saisieProjetJour : saisieProjet.getTemps().values()) {
				if(saisieProjetJour.getHeures() != null && saisieProjetJour.getHeures() > 0){

					SaisieTemps saisieTemps = new SaisieTemps();
					saisieTemps.setProjet(projectRepository.findOne(saisieProjet.getProjet().getId()));
					saisieTemps.setUtilisateur(user);
					saisieTemps.setHeures(saisieProjetJour.getHeures());
					saisieTemps.setCommentaire(saisieProjetJour.getCommentaire());
					saisieTemps.setDate(DateUtils.addDays(dateDebutSemaine, saisieProjetJour.getJour().ordinal()-1));

					saisieTempsRepository.save(saisieTemps);
				}
			}
		}

		//suppression des dej externes du lundi au dimanche sur la semaine
		List<DejeunerExterne> dejeunerExternes = dejeunerExterneRepository.findByUtilisateur(user.getId(), dateDebutSemaine, dateFinSemaine);
		dejeunerExterneRepository.deleteInBatch(dejeunerExternes);

		//sauvegarde des nouveaux dej externes
		Iterator iter = planning.getDejeunersExterne().keySet().iterator();
		while(iter.hasNext()){

			String jour = (String)iter.next();
			JourSemaine jourSemaine = JourSemaine.valueOf(JourSemaine.class,jour);
			boolean isDejExterne = planning.getDejeunersExterne().get(jour);
			if(isDejExterne){
				DejeunerExterne dejExterne = new DejeunerExterne();
				dejExterne.setUtilisateur(user);
				dejExterne.setDate(DateUtils.addDays(dateDebutSemaine, jourSemaine.ordinal()-1));

				dejeunerExterneRepository.save(dejExterne);
			}
		}
	}

	/**
	 * chargement du planning pour une semaine
	 * @param login le login de l'utilisateur
	 * @param date une date sur la semaine
	 * @return le planning des temps hebdo
	 * @throws AbsenceException
	 */
	public SaisieTempsSemaine loadSaisieTemps(String login, Date date) throws Exception{

		SaisieTempsSemaine planningHebdo = new SaisieTempsSemaine();
		Map<String,SaisieTempsProjet> projetsSaisis = new TreeMap<String,SaisieTempsProjet>();

		//recup du 1er lundi avant la date selectionnee
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		//		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
		//			calendar.add(Calendar.DATE, -1);
		//		}

		//plage de dates entre debut et fin de semaine de saisie (samedi)
		Date dateDebutSemaine = calendar.getTime();
		Date dateFinSemaine = DateUtils.addDays(dateDebutSemaine, 6);
		planningHebdo.setDate(dateDebutSemaine);

		//recup de l'utilisateur
		Utilisateur user = utilisateurRepository.findByLogin(login);

		//recup des temps saisis du lundi au samedi sur la semaine
		List<SaisieTemps> saisies = saisieTempsRepository.findByUtilisateur(user.getId(), dateDebutSemaine, dateFinSemaine);

		//recuperation de la liste des projets de l'annee selectionn�e
		//TODO voir le pb des semaines sur 2 ann�es
		List<Projet> projets = projectRepository.findByAnnee(calendar.get(Calendar.YEAR));
		for (Projet projet : projets) {
			projetsSaisis.put(projet.getNom(), new SaisieTempsProjet(projet));
		}

		//recup des jours feries
		List<Date> joursFeries = null;
		try {
			joursFeries = calendarService.getJourFeries();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		GregorianCalendar calendarTmp = new GregorianCalendar();
		calendarTmp.setTime(dateDebutSemaine);
		while(!DateUtils.isSameDay(calendarTmp.getTime(),dateFinSemaine)){
			Date dateTmp = calendarTmp.getTime();
			if(joursFeries.contains(dateTmp)){
				planningHebdo.getJoursFeries().put(JourSemaine.values()[calendarTmp.get(Calendar.DAY_OF_WEEK)-1],new Boolean(true));
			}
			calendarTmp.add(Calendar.DATE, 1);
		}

		//recup des jours de conges
		List<DemandeAbsence> absences = absenceRepository.findByUtilisateur(user.getId(), dateDebutSemaine, dateFinSemaine);
		for (DemandeAbsence absence : absences) {
			if(absence.getStatut().equals(StatutAbsence.VALIDE)){

				GregorianCalendar calendarAbsence = new GregorianCalendar();
				calendarAbsence.setTime(absence.getDateDebut());
				Date dateFinPlusUn = DateUtils.addDays(absence.getDateFin(), 1);
				while(!DateUtils.isSameDay(calendarAbsence.getTime(), dateFinPlusUn)){

					if(calendarAbsence.getTime().before(dateDebutSemaine) || calendarAbsence.getTime().after(dateFinSemaine)){
						//on vérifie si le jour de l'absence est bien compris dans la semaine courante
						calendarAbsence.add(Calendar.DATE, 1);
						continue;

					}

					JourSemaine jourAbsence = JourSemaine.values()[calendarAbsence.get(Calendar.DAY_OF_WEEK)-1];

					DemandeAbsence existingAbsence = planningHebdo.getAbsences().get(jourAbsence);
					if(existingAbsence != null){
						//si une absence existe deja sur la meme journee, alors on merge les 2 demi journees d'absences en une absence d'1 journee
						existingAbsence.setDebutPM(false);
						existingAbsence.setFinAM(false);
					}else{
						DemandeAbsence absenceTmp = absence.cloneAbsence();
						if(absenceTmp.getDebutPM() && !DateUtils.isSameDay(calendarAbsence.getTime(), absenceTmp.getDateDebut())){
							absenceTmp.setDebutPM(false);
						}
						if(absenceTmp.getFinAM() && !DateUtils.isSameDay(calendarAbsence.getTime(), absenceTmp.getDateFin())){
							absenceTmp.setFinAM(false);
						}
						planningHebdo.getAbsences().put(jourAbsence, absenceTmp);
					}
					calendarAbsence.add(Calendar.DATE, 1);
				}
			}
		}

		//traitement des saisies
		for (SaisieTemps saisie : saisies) {

			Date dateSaisie = saisie.getDate();
			GregorianCalendar calendarSaisie = new GregorianCalendar();
			calendarSaisie.setTime(dateSaisie);
			Projet projet = saisie.getProjet();
			Integer nbHeures = saisie.getHeures();
			String comment = saisie.getCommentaire();

			//System.out.println("!!!!" + saisie.getProjet().getNom() + " - " + calendarSaisie.getTime() + " -" + calendarSaisie.get(Calendar.DAY_OF_WEEK) + " - " + nbHeures);

			SaisieTempsProjet saisieProjet = projetsSaisis.get(projet.getNom());
			JourSemaine jourSaisie = JourSemaine.values()[calendarSaisie.get(Calendar.DAY_OF_WEEK)-1];
			SaisieTempsProjetJour saisieProjetJour = saisieProjet.getTemps().get(jourSaisie);

			saisieProjetJour.setHeures(nbHeures);
			saisieProjetJour.setCommentaire(comment);
		}

		//recuperation des dejeuners externes
		List<DejeunerExterne> dejeunerExternes = dejeunerExterneRepository.findByUtilisateur(user.getId(), dateDebutSemaine, dateFinSemaine);
		for (DejeunerExterne dejeunerExterne : dejeunerExternes) {

			GregorianCalendar calenderDej = new GregorianCalendar();
			calenderDej.setTime(dejeunerExterne.getDate());
			JourSemaine jourDej = JourSemaine.values()[calenderDej.get(Calendar.DAY_OF_WEEK)-1];
			planningHebdo.getDejeunersExterne().put(jourDej, new Boolean(true));
		}

		planningHebdo.setProjets(projetsSaisis.values());

		return planningHebdo;

	}

	public List<SaisieTemps> findByProjetAndUtilisateur(String nomProjet, Long userId, Date dateDebut, Date dateFin ){
		if(userId != null){
			return saisieTempsRepository.findByProjetAndUtilisateur(nomProjet, userId, dateDebut, dateFin);
		}else{
			return saisieTempsRepository.findByProjet(nomProjet, dateDebut, dateFin);
		}
	}

}
