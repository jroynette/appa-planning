package org.appa.planning.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.appa.planning.bo.LigneReportingProjet;
import org.appa.planning.bo.Projet;
import org.appa.planning.bo.SaisieTemps;
import org.appa.planning.bo.SaisieTempsPeriode;
import org.appa.planning.bo.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@RunWith(SpringJUnit4ClassRunner.class)
public class SaisieTempsRepositoryTests {

	@Autowired
	private ReportingTempsRepository repository;

	@PersistenceContext
	private EntityManager entityManager;

	private Utilisateur utilisateur;

	@Before
	public void setup() throws Exception {

		Projet projet = new Projet();
		projet.setAnnee(2011);
		projet.setNom("projet1");

		Projet projet2 = new Projet();
		projet2.setAnnee(2011);
		projet2.setNom("projet2");

		utilisateur = new Utilisateur();
		utilisateur.setLogin("login");
		utilisateur.setNom("nom");
		utilisateur.setPrenom("prenom");
		utilisateur.setRole("role1");
		utilisateur.setNbConges(25);
		utilisateur.setNbRTT(15);

		Utilisateur utilisateur2 = new Utilisateur();
		utilisateur2.setLogin("login2");
		utilisateur2.setNom("nom2");
		utilisateur2.setPrenom("prenom2");
		utilisateur2.setRole("role1");
		utilisateur2.setNbConges(25);
		utilisateur2.setNbRTT(15);

		entityManager.persist(utilisateur);
		entityManager.persist(utilisateur2);
		entityManager.persist(projet);
		entityManager.persist(projet2);

		for (int i = 0; i < 10000 ; i++) {
			SaisieTemps saisie = new SaisieTemps();
			if(i%2 == 0){
				saisie.setProjet(projet);
				saisie.setUtilisateur(utilisateur);
				saisie.setPeriode(SaisieTempsPeriode.AM);
			}else{
				saisie.setProjet(projet2);
				saisie.setUtilisateur(utilisateur2);
				saisie.setPeriode(SaisieTempsPeriode.PM);
			}
			saisie.setTemps(4);
			saisie.setDate(DateUtils.addDays(new Date(),i));

			entityManager.persist(saisie);
		}
		entityManager.flush();
		entityManager.clear();
	}

	@Test
	@Transactional
	public void testReportForProjectsByDates() throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		Date dateDeb = new Date();
		Date dateFin = DateUtils.addDays(new Date(),10000);

		List<LigneReportingProjet> lignes = repository.reportForProjectsByDates(dateDeb, dateFin);
		Assert.assertFalse(lignes.isEmpty());
		watch.stop();
		System.out.println(watch.shortSummary());

	}

	@Test
	@Transactional
	public void testReportForUserByDates() throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		Date dateDeb = new Date();
		Date dateFin = DateUtils.addDays(new Date(),100);

		List<LigneReportingProjet> lignes = repository.reportForUserByDates(utilisateur.getId(), dateDeb, dateFin);
		Assert.assertFalse(lignes.isEmpty());
		watch.stop();
		System.out.println(watch.shortSummary());

	}
}
