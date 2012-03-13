package org.appa.planning.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.appa.planning.bo.Absence;
import org.appa.planning.bo.JourSemaine;


/**
 * Classe repre�sentant l'ensemble des saisies de temps par projet sur une semaine
 * 
 * @author jroynette
 *
 */
public class SaisieTempsSemaine {

	private Date date;

	private Collection<SaisieTempsProjet> projets = new ArrayList<SaisieTempsProjet>();

	private Map<JourSemaine,Boolean> dejeunersExterne = new HashMap<JourSemaine, Boolean>();

	private Map<JourSemaine,Boolean> joursFeries = new HashMap<JourSemaine, Boolean>();

	private Map<JourSemaine,Absence> absences = new HashMap<JourSemaine, Absence>();

	public SaisieTempsSemaine(){
		for (JourSemaine jour : JourSemaine.values()) {
			dejeunersExterne.put(jour, false);
			joursFeries.put(jour, false);
		}
	}

	public Collection<SaisieTempsProjet> getProjets() {
		return projets;
	}

	public void setProjets(Collection<SaisieTempsProjet> projets) {
		this.projets = projets;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map<JourSemaine, Boolean> getDejeunersExterne() {
		return dejeunersExterne;
	}

	public void setDejeunersExterne(Map<JourSemaine, Boolean> dejeunersExterne) {
		this.dejeunersExterne = dejeunersExterne;
	}

	public Map<JourSemaine, Boolean> getJoursFeries() {
		return joursFeries;
	}

	public void setJoursFeries(Map<JourSemaine, Boolean> joursFeries) {
		this.joursFeries = joursFeries;
	}

	public Map<JourSemaine,Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(Map<JourSemaine,Absence> absences) {
		this.absences = absences;
	}
}
