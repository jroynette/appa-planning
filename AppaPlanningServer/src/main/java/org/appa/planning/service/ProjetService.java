package org.appa.planning.service;

import org.appa.planning.repository.SaisieTempsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des saisie de temps sur les projets
 * 
 * @author jroynett
 *
 */
@Service
public class ProjetService {

	@Autowired
	private SaisieTempsRepository saisieTempsRepository;

	/**
	 * @param date une date sur la semaine souhait�e (lundi au dimanche)
	 * @return le planning hebdo de la semaine s�lectionn�e
	 */
	//	public PlanningHebdo loadPlanningHebdo(Integer userId, Date date){
	//
	//		PlanningHebdo planningHebdo = new PlanningHebdo();
	//
	//		//r�cup�ration du 1er lundi avant la date s�lectionn�e
	//		GregorianCalendar calendar = new GregorianCalendar();
	//		calendar.setTime(date);
	//		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
	//			calendar.add(Calendar.DATE, -1);
	//		}
	//
	//		Date dateDebutSemaine = calendar.getTime();
	//		Date dateFinSemaine = DateUtils.addDays(dateDebutSemaine, 7);
	//
	//		//r�cup�ration des temps saisis du lundi au dimanche sur la semaine
	//		List<SaisieTemps> temps = saisieTempsRepository.findByUtilisateur(userId, dateDebutSemaine, dateFinSemaine);
	//		planningHebdo.setSaisies(temps);
	//
	//		return planningHebdo;
	//	}
	//
	//	/**
	//	 * sauvegarde le planning hebdomadaire
	//	 */
	//	public void savePlanningHebdo(PlanningHebdo planning){
	//		//TODO voir si des controles sont n�cessaires ici
	//		for (SaisieTemps saisie : planning.getSaisies()) {
	//			saisieTempsRepository.save(saisie);
	//		}
	//	}

}
