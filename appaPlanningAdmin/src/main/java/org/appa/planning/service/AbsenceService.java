package org.appa.planning.service;

import org.appa.planning.bo.Absence;
import org.appa.planning.bo.StatutAbsence;
import org.appa.planning.repository.AbsenceRepository;
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
@RemotingDestination
public class AbsenceService {

	@Autowired
	private AbsenceRepository absenceRepository;

	/**
	 * permet de poser un nouvelle absence
	 */
	@Transactional
	public void poserAbsence(Absence absence){
		//TODO exception si impossible
		//TODO impact sur les jours cong�s / RTT restants
		//TODO envoi de mail ?
		absenceRepository.save(absence);
	}

	/**
	 * permet de modifier une absence (nb de jours, type, ...)
	 */
	@Transactional
	public void modifierAbsence(Absence absence){
		//TODO exception si impossible
		//TODO impact sur les jours cong�s / RTT restants
		//TODO envoi de mail ?
		absenceRepository.save(absence);
	}

	/**
	 * permet de valider une absence (par le responsable)
	 * @param id l'id de l'absence
	 */
	@Transactional
	public void validerAbsence(Long id){
		//TODO envoi de mail
		Absence absence = absenceRepository.findOne(id);
		if(absence != null){
			if(!absence.getStatut().equals(StatutAbsence.POSE)){
				throw new RuntimeException("l'absence ne peut pas être validée car son statut actuel est " + absence.getStatut());
			}
			absence.setStatut(StatutAbsence.VALIDE);
			absenceRepository.save(absence);
		}
	}

	/**
	 * permet de refuser une absence (par le responsable)
	 * @param id l'id de l'absence
	 */
	@Transactional
	public void refuserAbsence(Long id){
		//TODO envoi de mail
		Absence absence = absenceRepository.findOne(id);
		if(absence != null){
			if(!absence.getStatut().equals(StatutAbsence.POSE)){
				throw new RuntimeException("l'absence ne peut pas être validée car son statut actuel est " + absence.getStatut());
			}
			absence.setStatut(StatutAbsence.REFUSE);
			absenceRepository.save(absence);
		}
	}

	/**
	 * permet d'annuler une absence
	 * @param id l'id de l'absence
	 */
	@Transactional
	public void annulerAbsence(Long id){
		//TODO exception si impossible
		//TODO impact sur les jours cong�s / RTT restants
		Absence absence = absenceRepository.findOne(id);
		if(absence != null){
			//TODO voir les RG
			absence.setStatut(StatutAbsence.ANNULE);
			absenceRepository.save(absence);
		}
	}

}
