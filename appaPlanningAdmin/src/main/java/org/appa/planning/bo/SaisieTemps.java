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
 * Objet m�tier repr�sentant une ligne de saisie de temps sur un projet.
 * 
 * <p>une saisie de temps se fait une demi journ�e. les temps saisis pour la matin�e et l'apr�s-midi sont dissoci�s</p>
 * 
 * <p>pour une journ�e, une information d�jeuner externe peut �tre renseign�e (calcul des tickets restaurant)</p>
 * 
 * <p>un commentaire peut �tre ajout� sur une demi journ�e pr�cise</p>
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

	//@NotNull
	//@Column
	//private SaisieTempsPeriode periode;

	@NotNull
	@Column
	private Integer heures;

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

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public Integer getHeures() {
		return heures;
	}

	public void setHeures(Integer heures) {
		this.heures = heures;
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

	@Override
	public String toString() {
		if(projet != null){
			return "["+ projet.getNom() + " - " + date + " - "
					+ heures + "]";
		}
		return "";

	}
}
