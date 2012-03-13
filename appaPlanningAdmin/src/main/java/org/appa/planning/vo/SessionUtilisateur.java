package org.appa.planning.vo;

import java.util.ArrayList;
import java.util.List;

import org.appa.planning.bo.Absence;
import org.appa.planning.bo.Utilisateur;

public class SessionUtilisateur {
	
	private Utilisateur utilisateur;
	
	private List<Absence> absences = new ArrayList<Absence>();
	
	private CompteurConges compteurs;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}

	public CompteurConges getCompteurs() {
		return compteurs;
	}

	public void setCompteurs(CompteurConges compteurs) {
		this.compteurs = compteurs;
	}
}
