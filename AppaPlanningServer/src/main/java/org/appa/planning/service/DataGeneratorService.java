package org.appa.planning.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("testdatas")
public class DataGeneratorService {

	@Autowired
	private DataTestGenerator dataGenerator;

	@PostConstruct
	public void initDatas(){

		dataGenerator.createProjet("projet1", 2010);
		dataGenerator.createProjet("projet2", 2010);
		dataGenerator.createProjet("projet1", 2011);
		dataGenerator.createProjet("projet2", 2011);
		dataGenerator.createProjet("projet3", 2012);

		dataGenerator.createUtilisateur("user1");
		dataGenerator.createUtilisateur("user2");
		dataGenerator.createUtilisateur("user3");
	}

}
