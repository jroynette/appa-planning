package org.appa.planning.vo;

import java.util.ArrayList;
import java.util.List;

import org.appa.planning.bo.DemandeAbsence;
import org.appa.planning.bo.Utilisateur;

public class SessionUtilisateur {

	private Utilisateur utilisateur;

	private List<DemandeAbsence> absences = new ArrayList<DemandeAbsence>();

	private List<DemandeAbsence> absencesFutures = new ArrayList<DemandeAbsence>();

	private CompteurConges compteurs;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<DemandeAbsence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<DemandeAbsence> absences) {
		this.absences = absences;
	}

	public CompteurConges getCompteurs() {
		return compteurs;
	}

	public void setCompteurs(CompteurConges compteurs) {
		this.compteurs = compteurs;
	}

	public List<DemandeAbsence> getAbsencesFutures() {
		return absencesFutures;
	}

	public void setAbsencesFutures(List<DemandeAbsence> absencesFutures) {
		this.absencesFutures = absencesFutures;
	}
}
