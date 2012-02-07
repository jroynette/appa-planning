package org.appa.planning.service;

import org.appa.planning.bo.Utilisateur;
import org.appa.planning.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 
 * Service de gestion des infos utilisateurs
 * @author jroynett
 *
 */
@Service
public class UtilisateurService {

	@Autowired
	private  UtilisateurRepository utilisateurRepository;

	public Utilisateur login(String login, String password){

		Utilisateur user = utilisateurRepository.findByLogin(login);

		if(!user.getPassword().equals(password)){
			throw new RuntimeException("password incorrect");
		}
		return user;
	}

}
