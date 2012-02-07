package org.appa.planning.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.appa.planning.bo.Absence;
import org.appa.planning.bo.Projet;
import org.appa.planning.bo.StatutAbsence;
import org.appa.planning.bo.TypeAbsence;
import org.appa.planning.bo.Utilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service permettant de générer des données de tests
 * 
 * @author jroynett
 *
 */
@Service
@Profile("testdatas")
public class DataTestGenerator {

	@PersistenceContext
	private EntityManager entityManager;

	public Map<String,Utilisateur> utilisateurs = new HashMap<String, Utilisateur>();

	public Map<String,Projet> projets = new HashMap<String, Projet>();

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Transactional
	public Projet createProjet(String name, Integer annee){

		Projet projet = new Projet();
		projet.setAnnee(annee);
		projet.setNom(name);
		entityManager.persist(projet);

		projets.put(name, projet);

		return projet;

	}

	@Transactional
	public Utilisateur createUtilisateur(String login){

		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setLogin(login);
		utilisateur.setNom(login + "nom");
		utilisateur.setPrenom(login + "prenom");
		utilisateur.setRole("role1");
		utilisateur.setNbConges(25);
		utilisateur.setNbRTT(15);
		entityManager.persist(utilisateur);

		utilisateurs.put(login, utilisateur);

		return utilisateur;
	}

	@Transactional
	public Absence createAbsence(TypeAbsence type, StatutAbsence statut, String dateDeb,  String dateFin, String user) throws ParseException{

		Absence absence = new Absence();
		absence.setStatut(statut);
		absence.setType(type);
		absence.setUtilisateur(utilisateurs.get(user));
		absence.setDateDebut(sdf.parse(dateDeb));
		absence.setDateFin(sdf.parse(dateFin));

		entityManager.persist(absence);

		return absence;
	}
}
