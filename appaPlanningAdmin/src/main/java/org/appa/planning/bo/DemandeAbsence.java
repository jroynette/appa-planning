package org.appa.planning.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Objet m�tier repr�sentant une absence (RTT, Cong�s)
 * 
 * @author jroynett
 *
 */
@Entity
public class DemandeAbsence implements Serializable,Cloneable,Comparable<DemandeAbsence> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@ManyToOne
	private Utilisateur utilisateur;

	@NotNull
	@Column
	private Date dateDebut;

	@Column
	private boolean debutPM = false;

	@NotNull
	@Column
	private Date dateFin;

	@Column
	private boolean finAM = false;

	@NotNull
	@Column
	private StatutAbsence statut;

	@NotNull
	@Column
	private TypeAbsence type;

	@Column
	private String commentaire;

	//champ non persisté
	@Transient
	private float nbJours;

	public float getNbJours() {
		return nbJours;
	}

	public void setNbJours(float nbJours) {
		this.nbJours = nbJours;
	}

	public String getLibelleType(){
		if(TypeAbsence.AUTRE.equals(type)){
			return commentaire;
		}
		return type.toString();
	}

	public boolean getDebutPM() {
		return debutPM;
	}

	public void setDebutPM(boolean debutPM) {
		this.debutPM = debutPM;
	}

	public boolean getFinAM() {
		return finAM;
	}

	public void setFinAM(boolean finAM) {
		this.finAM = finAM;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public StatutAbsence getStatut() {
		return statut;
	}

	public void setStatut(StatutAbsence statut) {
		this.statut = statut;
	}

	public TypeAbsence getType() {
		return type;
	}

	public void setType(TypeAbsence type) {
		this.type = type;
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
		DemandeAbsence other = (DemandeAbsence) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public DemandeAbsence cloneAbsence() {
		try {
			return (DemandeAbsence)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int compareTo(DemandeAbsence o) {
		return this.dateDebut.compareTo(o.dateDebut);
	}
}
