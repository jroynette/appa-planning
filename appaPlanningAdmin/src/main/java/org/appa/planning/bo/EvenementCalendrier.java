package org.appa.planning.bo;

import java.util.Date;

/**
 * correspond à un evenement dans les calendrier google
 * @author jroynette
 *
 */
public class EvenementCalendrier implements Comparable<EvenementCalendrier>{

	private String titre;

	private String description;

	private Date dateDebut;

	private Date dateFin;

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public int compareTo(EvenementCalendrier e) {
		return this.dateDebut.compareTo(e.dateDebut);
	}



}
