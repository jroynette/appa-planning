package org.appa.planning.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Objet métier représentant un utilisateur
 * 
 * @author jroynett
 *
 */
@Entity
public class Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;

	@NotNull
	@Column(unique = true)
	@Size(max = 20)
	private String login;

	@Column
	@Size(max = 10)
	private String password;

	@NotNull
	@Column
	@Size(max = 30)
	private String nom;

	@NotNull
	@Column
	@Size(max = 30)
	private String prenom;

	@NotNull
	@Column
	@Size(max = 10)
	private String role;

	@NotNull
	@Column
	@Max(99)
	private Integer nbConges;

	@NotNull
	@Column
	@Max(99)
	private Integer nbRTT;

	@NotNull
	@Column
	private Boolean quatrevingt = false;

	@NotNull
	@Column
	private Boolean actif = true;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getNbConges() {
		return nbConges;
	}

	public void setNbConges(Integer nbConges) {
		this.nbConges = nbConges;
	}

	public Integer getNbRTT() {
		return nbRTT;
	}

	public void setNbRTT(Integer nbRTT) {
		this.nbRTT = nbRTT;
	}

	public Boolean getQuatrevingt() {
		return quatrevingt;
	}

	public void setQuatrevingt(Boolean quatrevingt) {
		this.quatrevingt = quatrevingt;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public String getDisplayName() {
		return prenom + " " + nom;
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
		Utilisateur other = (Utilisateur) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", login=" + login + ", nom=" + nom
				+ ", prenom=" + prenom + ", role=" + role + ", nbConges="
				+ nbConges + ", nbRTT=" + nbRTT + ", quatrevingt="
				+ quatrevingt + ", actif=" + actif + "]";
	}
}
