package org.appa.planning.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.appa.planning.bo.Preferences;
import org.appa.planning.bo.Utilisateur;
import org.appa.planning.repository.PreferencesRepository;
import org.appa.planning.repository.UtilisateurRepository;
import org.appa.planning.vo.SessionUtilisateur;
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
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private PreferencesRepository prefsRepo;

	@Autowired
	private AbsenceService absenceService;

	public SessionUtilisateur login(String login, String password) throws Exception{

		Utilisateur user = utilisateurRepository.findByLogin(login);

		if(user == null){
			throw new RuntimeException("login incorrect");
		}

		//				if(user.getPassword() != null && !user.getPassword().equals(password)){
		//					throw new RuntimeException("password incorrect");
		//				}
		return absenceService.loadSessionUtilisateur(user.getId(),new Date());
	}

	public List<Utilisateur> loadAllUtilisateurs(){

		return utilisateurRepository.findAll();
	}

	public List<String> loadFiltresProjets(Long userId) throws Exception{

		Preferences prefs = prefsRepo.findOne(userId);

		if(prefs == null){
			return new ArrayList<String>();
		}

		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(prefs.getDatas()));
		return (List<String>) ois.readObject();
	}

	public void saveFiltresProjets(Long userId, List<String> projets) throws Exception{

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
		oos.writeObject(projets);
		oos.flush();
		oos.close();

		Preferences prefs = new Preferences();
		prefs.setUserId(userId);
		prefs.setDatas(byteArrayOutputStream.toByteArray());

		prefsRepo.save(prefs);
	}
}
