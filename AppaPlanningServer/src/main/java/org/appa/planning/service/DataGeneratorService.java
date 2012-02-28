package org.appa.planning.service;

import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataGeneratorService {

	@Autowired
	private DataTestGenerator dataGenerator;

	@PostConstruct
	public void initDatas() throws ParseException{

		//
		//		dataGenerator.createProjet("TPQVH");
		//		dataGenerator.createProjet("Projets Radiance
		//				dataGenerator.createProjet("Projets Mutualité
		//						dataGenerator.createProjet("FEES
		//								dataGenerator.createProjet("QAI lycées NPC
		//										dataGenerator.createProjet("QAI lycées Picardie
		//												dataGenerator.createProjet("GT CO
		//														dataGenerator.createProjet("Coordination CMEI
		//																dataGenerator.createProjet("MP3
		//		Réseau tabac 2012
		//		Réseau tabac 2011
		//		Biosurveillance bureaux
		//		Végétation et climat urbain 
		//		Besoins des collectivités Biosurveillance
		//		Biosurveillance site industriel 
		//		Carto lichens
		//		Poll-K
		//		Pollens
		//		Pollinier sentinelle
		//		Parcours découverte
		//		Portail biosurveillance
		//		Rencontres 2012
		//		JIQA 2012
		//		Bulles d'air
		//		Air Pur
		//		Site internet
		//		Newsletter
		//		WebQAI
		//		GRPS
		//		Sessions d'outils
		//		Pédagothèque
		//		Communiquer vers les professionnels
		//		Communiquer vers le grand public
		//		Soutien porteurs de projets
		//		Ville de Lille
		//		Site web siège 
		//		PRSE, S3PI, CLI…
		//		Coord. Interne (équipe, note de frais…)
		//		Coord. QAI
		//		Montage projet
		//		Veille
		//		Divers
		//		Projets Sivom

		//
		//		dataGenerator.createProjet("projet1", 2012);
		//		dataGenerator.createProjet("projet2", 2012);
		//		dataGenerator.createProjet("projet3", 2012);

		//dataGenerator.createUtilisateur("user1");
		//dataGenerator.createUtilisateur("user2", "responsable");

		//		dataGenerator.createSaisieTemps("user1", "projet1","02/01/2012", 2);
		//		dataGenerator.createSaisieTemps("user1", "projet1","03/01/2012", 4);
		//		dataGenerator.createSaisieTemps("user1", "projet2","02/01/2012", 5);
		//
		//		dataGenerator.createSaisieTemps("user2", "projet1","01/01/2012", 5);
		//		dataGenerator.createSaisieTemps("user2", "projet1","02/01/2012", 7);
		//		dataGenerator.createSaisieTemps("user2", "projet2","01/01/2012", 3);
	}

}
