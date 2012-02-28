package org.appa.planning.vo;

import org.appa.planning.bo.JourSemaine;

/**
 * Classe repr�sentant une saisie de temps sur un projet et un jour pr�cis
 * 
 * @author jroynette
 *
 */
public class SaisieTempsProjetJour {

	private JourSemaine jour;

	private Integer heures;

	private String commentaire;


	public SaisieTempsProjetJour() {
	}

	public SaisieTempsProjetJour(JourSemaine jour) {
		super();
		this.jour = jour;
	}

	public JourSemaine getJour() {
		return jour;
	}

	public void setJour(JourSemaine jour) {
		this.jour = jour;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Integer getHeures() {
		return heures;
	}

	public void setHeures(Integer heures) {
		this.heures = heures;
	}
}
