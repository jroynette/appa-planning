package org.appa.planning.bo;

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
