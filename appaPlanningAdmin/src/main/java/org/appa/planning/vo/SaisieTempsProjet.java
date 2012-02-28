package org.appa.planning.vo;

import java.util.HashMap;
import java.util.Map;

import org.appa.planning.bo.JourSemaine;
import org.appa.planning.bo.Projet;

/**
 * Classe representant les saisies de temps sur une semaine pour un projet précis
 * 
 * @author jroynette
 *
 */
public class SaisieTempsProjet {

	private Projet projet;

	private Map<JourSemaine,SaisieTempsProjetJour> temps = new HashMap<JourSemaine, SaisieTempsProjetJour>();

	public SaisieTempsProjet(){

	}

	public SaisieTempsProjet(Projet projet){

		this.projet = projet;
		for (JourSemaine jour : JourSemaine.values()) {
			temps.put(jour, new SaisieTempsProjetJour(jour));
		}
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public Map<JourSemaine, SaisieTempsProjetJour> getTemps() {
		return temps;
	}

	public void setTemps(Map<JourSemaine, SaisieTempsProjetJour> temps) {
		this.temps = temps;
	}
}
