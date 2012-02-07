package org.appa.planning.util;

import org.appa.planning.bo.Utilisateur;
import org.springframework.security.core.Authentication;

public class UserContext {

	private Utilisateur utilisateur;

	private Authentication authentication;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
}
