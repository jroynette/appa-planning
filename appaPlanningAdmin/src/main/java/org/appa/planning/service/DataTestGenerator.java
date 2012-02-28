package org.appa.planning.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.appa.planning.bo.Absence;
import org.appa.planning.bo.Projet;
import org.appa.planning.bo.SaisieTemps;
import org.appa.planning.bo.StatutAbsence;
import org.appa.planning.bo.TypeAbsence;
import org.appa.planning.bo.UserRole;
import org.appa.planning.bo.Utilisateur;
import org.appa.planning.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service permettant de g�n�rer des donn�es de tests
 * 
 * @author jroynett
 *
 */
@Service
public class DataTestGenerator {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	private Map<String,Utilisateur> utilisateurs = new HashMap<String, Utilisateur>();

	private Map<String,Projet> projets = new HashMap<String, Projet>();

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Transactional
	public Projet createProjet(String name, Integer annee){
		
		Projet projet = new Projet();
		projet.setAnnee(annee);
		projet.setNom(name);
		projet.setDescription(name + "desc");
		entityManager.persist(projet);

		projets.put(name, projet);
		
		return projet;

	}

	@Transactional
	public Utilisateur createUtilisateur(String login){

		return createUtilisateur(login, "utilisateur");
	}

	@Transactional
	public Utilisateur createUtilisateur(String login, String role){

		Utilisateur existUser = utilisateurRepository.findByLogin(login);
		if(existUser != null){
			return existUser;
		}
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setLogin(login);
		utilisateur.setNom(login + "nom");
		utilisateur.setPrenom(login + "prenom");
		utilisateur.setRole(UserRole.valueOf(role));
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

	@Transactional
	public SaisieTemps createSaisieTemps(String user, String projet, String date, Integer nbHeures) throws ParseException{

		SaisieTemps saisieTemps = new SaisieTemps();
		saisieTemps.setProjet(projets.get(projet));
		saisieTemps.setUtilisateur(utilisateurs.get(user));
		saisieTemps.setHeures(nbHeures);
		saisieTemps.setDate(sdf.parse(date));

		entityManager.persist(saisieTemps);

		return saisieTemps;
	}


	public Map<String, Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(Map<String, Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public Map<String, Projet> getProjets() {
		return projets;
	}

	public void setProjets(Map<String, Projet> projets) {
		this.projets = projets;
	}


}
