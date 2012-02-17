package org.appa.planning.vo;

import java.io.Serializable;

/**
 * Objet métier représentant une ligne pour un reporting projet
 * 
 * @author jroynett
 *
 */
public class LigneReportingProjet implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * nom du projet
	 */
	private String projet;

	/**
	 * nom du projet
	 */
	private String utilisateur;

	/**
	 * nom du projet
	 */
	private Integer temps;

	public LigneReportingProjet() {
	}

	public LigneReportingProjet(String projet, String utilisateur, Long temps) {
		super();
		this.projet = projet;
		this.utilisateur = utilisateur;
		this.temps = temps.intValue();
	}

	public String getProjet() {
		return projet;
	}

	public void setProjet(String projet) {
		this.projet = projet;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Integer getTemps() {
		return temps;
	}

	public void setTemps(Integer temps) {
		this.temps = temps;
	}
}
