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
	 * @param date une date sur la semaine souhaitée (lundi au dimanche)
	 * @return le planning hebdo de la semaine sélectionnée
	 */
	//	public PlanningHebdo loadPlanningHebdo(Integer userId, Date date){
	//
	//		PlanningHebdo planningHebdo = new PlanningHebdo();
	//
	//		//récupération du 1er lundi avant la date sélectionnée
	//		GregorianCalendar calendar = new GregorianCalendar();
	//		calendar.setTime(date);
	//		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
	//			calendar.add(Calendar.DATE, -1);
	//		}
	//
	//		Date dateDebutSemaine = calendar.getTime();
	//		Date dateFinSemaine = DateUtils.addDays(dateDebutSemaine, 7);
	//
	//		//récupération des temps saisis du lundi au dimanche sur la semaine
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
	//		//TODO voir si des controles sont nécessaires ici
	//		for (SaisieTemps saisie : planning.getSaisies()) {
	//			saisieTempsRepository.save(saisie);
	//		}
	//	}

}
