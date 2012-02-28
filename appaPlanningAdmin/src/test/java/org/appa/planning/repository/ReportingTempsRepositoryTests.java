package org.appa.planning.repository;

import java.util.List;

import junit.framework.Assert;

import org.appa.planning.AbstractJunitTests;
import org.appa.planning.vo.LigneReportingProjet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportingTempsRepositoryTests extends AbstractJunitTests {

	@Autowired
	private ReportingTempsRepository repository;

	@Before
	public void setup() throws Exception {

		dataGenerator.createProjet("projet1", 2010);
		dataGenerator.createProjet("projet2", 2010);

		dataGenerator.createUtilisateur("login");
		dataGenerator.createUtilisateur("login2");

		dataGenerator.createSaisieTemps("login", "projet1", "01/01/2012", 2);
		dataGenerator.createSaisieTemps("login", "projet1", "02/01/2012", 4);
		dataGenerator.createSaisieTemps("login", "projet2", "01/01/2012", 1);
		dataGenerator.createSaisieTemps("login", "projet2", "02/01/2012", 3);
		dataGenerator.createSaisieTemps("login", "projet2", "03/01/2012", 3);

		dataGenerator.createSaisieTemps("login2", "projet2", "01/01/2012", 1);
		dataGenerator.createSaisieTemps("login2", "projet2", "02/01/2012", 1);
	}

	@Test
	@Transactional
	public void testReportForProjectsByDates() throws Exception {

		List<LigneReportingProjet> lignes = repository.reportForProjectsByDates(sdf.parse("01/01/2012"), sdf.parse("02/01/2012"));
		Assert.assertEquals(3, lignes.size());

		LigneReportingProjet ligne1 = lignes.get(0);
		Assert.assertEquals("projet1", ligne1.getProjet());
		Assert.assertEquals("loginprenom loginnom", ligne1.getUtilisateur());
		Assert.assertEquals(new Integer(6), ligne1.getTemps());

		LigneReportingProjet ligne3 = lignes.get(2);
		Assert.assertEquals("projet2", ligne3.getProjet());
		Assert.assertEquals("loginprenom loginnom", ligne3.getUtilisateur());
		Assert.assertEquals(new Integer(4), ligne3.getTemps());

		LigneReportingProjet ligne2 = lignes.get(1);
		Assert.assertEquals("projet2", ligne2.getProjet());
		Assert.assertEquals("login2prenom login2nom", ligne2.getUtilisateur());
		Assert.assertEquals(new Integer(2), ligne2.getTemps());
	}

	@Test
	@Transactional
	public void testReportForProjectsByDates2() throws Exception {

		List<LigneReportingProjet> lignes = repository.reportForProjectsByDates(sdf.parse("03/01/2012"), sdf.parse("03/01/2012"));
		Assert.assertEquals(1, lignes.size());

		LigneReportingProjet ligne1 = lignes.get(0);
		Assert.assertEquals("projet2", ligne1.getProjet());
		Assert.assertEquals("loginprenom loginnom", ligne1.getUtilisateur());
		Assert.assertEquals(new Integer(3), ligne1.getTemps());
	}

	@Test
	@Transactional
	public void testReportForProjectsByDates3() throws Exception {

		List<LigneReportingProjet> lignes = repository.reportForProjectsByDates(sdf.parse("04/01/2012"), sdf.parse("31/01/2012"));
		Assert.assertEquals(0, lignes.size());
	}


	@Test
	@Transactional
	public void testReportForUserByDates() throws Exception {

		Long userId = super.dataGenerator.getUtilisateurs().get("login").getId();
		List<LigneReportingProjet> lignes = repository.reportForUserByDates(userId, sdf.parse("01/01/2012"), sdf.parse("02/01/2012"));
		Assert.assertEquals(2, lignes.size());

		LigneReportingProjet ligne1 = lignes.get(0);
		Assert.assertEquals("projet1", ligne1.getProjet());
		Assert.assertEquals("loginprenom loginnom", ligne1.getUtilisateur());
		Assert.assertEquals(new Integer(6), ligne1.getTemps());

		LigneReportingProjet ligne2 = lignes.get(1);
		Assert.assertEquals("projet2", ligne2.getProjet());
		Assert.assertEquals("loginprenom loginnom", ligne2.getUtilisateur());
		Assert.assertEquals(new Integer(4), ligne2.getTemps());
	}

	@Test
	@Transactional
	public void testReportForUserByDates2() throws Exception {

		Long userId = super.dataGenerator.getUtilisateurs().get("login").getId();
		List<LigneReportingProjet> lignes = repository.reportForUserByDates(userId, sdf.parse("03/01/2012"), sdf.parse("03/01/2012"));
		Assert.assertEquals(1, lignes.size());

		LigneReportingProjet ligne1 = lignes.get(0);
		Assert.assertEquals("projet2", ligne1.getProjet());
		Assert.assertEquals("loginprenom loginnom", ligne1.getUtilisateur());
		Assert.assertEquals(new Integer(3), ligne1.getTemps());
	}

	@Test
	@Transactional
	public void testReportForUserByDates3() throws Exception {

		Long userId = super.dataGenerator.getUtilisateurs().get("login2").getId();
		List<LigneReportingProjet> lignes = repository.reportForUserByDates(userId, sdf.parse("03/01/2012"), sdf.parse("03/01/2012"));
		Assert.assertEquals(0, lignes.size());
	}


	@Test
	@Transactional
	public void testReportForUserByDates4() throws Exception {

		Long userId = super.dataGenerator.getUtilisateurs().get("login").getId();
		List<LigneReportingProjet> lignes = repository.reportForUserByDates(userId, sdf.parse("04/01/2012"), sdf.parse("15/01/2012"));
		Assert.assertEquals(0, lignes.size());
	}
}
