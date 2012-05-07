package org.appa.planning.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.appa.planning.bo.DejeunerExterne;
import org.appa.planning.bo.DemandeAbsence;
import org.appa.planning.bo.Projet;
import org.appa.planning.bo.SaisieTemps;
import org.appa.planning.bo.Utilisateur;
import org.appa.planning.repository.AbsenceRepository;
import org.appa.planning.repository.DejeunerExterneRepository;
import org.appa.planning.repository.ProjectRepository;
import org.appa.planning.repository.SaisieTempsRepository;
import org.appa.planning.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private AbsenceRepository absenceRepository;

	@Autowired
	private SaisieTempsRepository saisieTempsRepository;

	@Autowired
	private DejeunerExterneRepository dejeunerExterneRepository;

	private SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");


	@PostConstruct
	public void init() throws ParseException {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
		//Locale.setDefault(new Locale("en"));
	}

	public StringBuffer createBackup(String databaseType) throws Exception{

		StringBuffer out = new StringBuffer();

		//users
		List<Utilisateur> users = this.utilisateurRepository.findAll();
		for (Utilisateur user : users) {
			String actif = user.getActif()?"1":"0";
			String quatreVingt = user.getQuatrevingt()?"1":"0";

			String sql = "insert into Utilisateur(id,actif,email,login,nbConges,nbRTT,nom,prenom,quatrevingt,role,version,deltaJoursConges,deltaJoursRTT) values(" +
					user.getId()+","+actif+",'"+ user.getEmail() + "','" + user.getLogin() +"'," + user.getNbConges() + "," + user.getNbRTT() + ",'" +
					user.getNom() + "','" + user.getPrenom() + "'," + quatreVingt + "," + user.getRole().ordinal() + "," + user.getVersion() + "," +
					user.getDeltaJoursConges() + "," + user.getDeltaJoursRTT() + ");\r\n";
			out.append(sql);
		}

		//projets
		List<Projet> projets = this.projectRepository.findAll();
		for (Projet projet : projets) {

			String sql = "insert into Projet(id,annee,nom,description,version) values (" +
					projet.getId() + "," + projet.getAnnee() + ",'" + projet.getNom() + "','" + projet.getDescription() + "'," + projet.getVersion() +");\r\n";
			out.append(sql);
		}

		//absences
		List<DemandeAbsence> absences = this.absenceRepository.findAll();
		for (DemandeAbsence absence : absences) {

			String debutPM = absence.getDebutPM()?"1":"0";
			String finAM = absence.getFinAM()?"1":"0";
			String comment = (absence.getCommentaire() != null)?absence.getCommentaire():"";

			String sql = "insert into Absence(id,commentaire,dateDebut,dateFin,debutPM,finAM,statut,type,utilisateur_id) values (" +
					absence.getId() + ",'" + comment + "'," + dateParse(absence.getDateDebut(),databaseType) + "," + dateParse(absence.getDateFin(),databaseType) + "," +
					debutPM + "," + finAM + "," + absence.getStatut().ordinal() + "," + absence.getType().ordinal() + "," + absence.getUtilisateur().getId() +");\r\n";
			out.append(sql);
		}

		//saisies
		List<SaisieTemps> saisies = this.saisieTempsRepository.findAll();
		for (SaisieTemps saisie : saisies) {
			String comment = (saisie.getCommentaire() != null)?saisie.getCommentaire():"";
			String sql = "insert into SaisieTemps(id,commentaire,date,heures,projet_id,utilisateur_id) values (" +
					saisie.getId() + ",'" + comment + "'," + dateParse(saisie.getDate(),databaseType) + "," +
					saisie.getHeures() + "," + saisie.getProjet().getId() + "," + saisie.getUtilisateur().getId() +");\r\n";
			out.append(sql);
		}

		//dej externes
		List<DejeunerExterne> dejeuners = this.dejeunerExterneRepository.findAll();
		for (DejeunerExterne dejeuner : dejeuners) {
			String sql = "insert into DejeunerExterne(id,date,utilisateur_id) values (" +
					dejeuner.getId() + "," + dateParse(dejeuner.getDate(),databaseType) + "," + dejeuner.getUtilisateur().getId() +");\r\n";
			out.append(sql);
		}

		return out;
	}

	private String dateParse(Date date, String databaseType){
		if(databaseType.equals("H2")){
			return "PARSEDATETIME('" + sdfSQL.format(date) + "','yyyy-MM-d')";
		}

		return "STR_TO_DATE('" + sdfSQL.format(date) + "','%Y-%m-%d')";


	}

}
