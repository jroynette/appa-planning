package org.appa.planning.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Objet métier représentant une ligne de saisie de temps sur un projet.
 * 
 * <p>une saisie de temps se fait une demi journée. les temps saisis pour la matinée et l'après-midi sont dissociés</p>
 * 
 * <p>pour une journée, une information déjeuner externe peut être renseignée (calcul des tickets restaurant)</p>
 * 
 * <p>un commentaire peut être ajouté sur une demi journée précise</p>
 * 
 * @author jroynett
 *
 */
@Entity
public class SaisieTemps implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;

	@NotNull
	@ManyToOne
	private Utilisateur utilisateur;

	@NotNull
	@ManyToOne
	private Projet projet;

	@NotNull
	@Column
	private Date date;

	@NotNull
	@Column
	private SaisieTempsPeriode periode;

	@NotNull
	@Column
	private Integer temps;

	@Column
	private Boolean dejExterne = false;

	@Column
	@Size(max = 100)
	private String commentaire;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public Boolean getDejExterne() {
		return dejExterne;
	}

	public void setDejExterne(Boolean dejExterne) {
		this.dejExterne = dejExterne;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public SaisieTempsPeriode getPeriode() {
		return periode;
	}

	public void setPeriode(SaisieTempsPeriode periode) {
		this.periode = periode;
	}

	public Integer getTemps() {
		return temps;
	}

	public void setTemps(Integer temps) {
		this.temps = temps;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaisieTemps other = (SaisieTemps) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
