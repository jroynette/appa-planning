package org.appa.planning.service;

import java.util.List;

import org.appa.planning.bo.Utilisateur;
import org.appa.planning.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;


/**
 * 
 * Service de gestion des infos utilisateurs
 * @author jroynett
 *
 */
@Service
@RemotingDestination("UtilisateurService")
public class UtilisateurService {

	@Autowired
	private  UtilisateurRepository utilisateurRepository;

	public Utilisateur login(String login, String password){

		Utilisateur user = utilisateurRepository.findByLogin(login);

		if(user == null){
			throw new RuntimeException("login incorrect");
		}

		if(user.getPassword() != null && !user.getPassword().equals(password)){
			throw new RuntimeException("password incorrect");
		}
		return user;
	}

	public List<Utilisateur> loadAllUtilisateurs(){

		return utilisateurRepository.findAll();
	}

}
