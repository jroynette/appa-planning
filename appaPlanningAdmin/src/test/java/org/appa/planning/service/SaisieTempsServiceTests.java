package org.appa.planning.service;

import org.appa.planning.AbstractJunitTests;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SaisieTempsServiceTests extends AbstractJunitTests {

	@Autowired
	private SaisieTempsService service;

	@Before
	public void setup() throws Exception {

		dataGenerator.createProjet("projet1", 2012);
		dataGenerator.createProjet("projet2", 2012);

		dataGenerator.createUtilisateur("login");

		dataGenerator.createSaisieTemps("login", "projet1", "02/01/2012", 4);
		dataGenerator.createSaisieTemps("login", "projet2", "02/01/2012", 3);
		dataGenerator.createSaisieTemps("login", "projet2", "03/01/2012", 2);
	}


	//	@Test
	//	@Transactional
	//	public void testsaveSaisieTemps() throws Exception {
	//
	//		//recuperation d'un planning vierge
	//		SaisieTempsSemaine saisies = service.loadSaisieTemps("login", sdf.parse("09/01/2012"));
	//
	//		SaisieTempsProjet tempsProjet1 = saisies.getProjets().get("projet1");
	//		tempsProjet1.getTemps().get(JourSemaine.LUNDI).setHeures(4);
	//		tempsProjet1.getTemps().get(JourSemaine.LUNDI).setCommentaire("comment");
	//		tempsProjet1.getTemps().get(JourSemaine.MARDI).setHeures(2);
	//
	//		SaisieTempsProjet tempsProjet2 = saisies.getProjets().get("projet2");
	//		tempsProjet2.getTemps().get(JourSemaine.LUNDI).setHeures(4);
	//		tempsProjet2.getTemps().get(JourSemaine.LUNDI).setCommentaire("comment2");
	//
	//		service.saveSaisieTemps("login", saisies);
	//
	//		saisies = service.loadSaisieTemps("login", sdf.parse("09/01/2012"));
	//
	//		//date du lundi de la semaine selectionnée
	//		Assert.assertEquals(sdf.parse("09/01/2012"),saisies.getDate());
	//		Assert.assertEquals(2,saisies.getProjets().size());
	//
	//		tempsProjet1 = saisies.getProjets().get("projet1");
	//		Assert.assertEquals(new Integer(4),tempsProjet1.getTemps().get(JourSemaine.LUNDI).getHeures());
	//		Assert.assertEquals("comment",tempsProjet1.getTemps().get(JourSemaine.LUNDI).getCommentaire());
	//		Assert.assertEquals(new Integer(2),tempsProjet1.getTemps().get(JourSemaine.MARDI).getHeures());
	//
	//		tempsProjet2 = saisies.getProjets().get("projet2");
	//		Assert.assertEquals(new Integer(4),tempsProjet2.getTemps().get(JourSemaine.LUNDI).getHeures());
	//		Assert.assertEquals("comment2",tempsProjet2.getTemps().get(JourSemaine.LUNDI).getCommentaire());
	//	}
	//
	//	@Test
	//	@Transactional
	//	public void testLoadSaisieTemps() throws Exception {
	//
	//		SaisieTempsSemaine saisies = service.loadSaisieTemps("login", sdf.parse("03/01/2012"));
	//
	//		//date du lundi de la semaine selectionnée
	//		Assert.assertEquals(sdf.parse("02/01/2012"),saisies.getDate());
	//		Assert.assertEquals(2,saisies.getProjets().size());
	//
	//		SaisieTempsProjet tempsProjet1 = saisies.getProjets().get("projet1");
	//		Assert.assertEquals(new Integer(4),tempsProjet1.getTemps().get(JourSemaine.LUNDI).getHeures());
	//
	//		SaisieTempsProjet tempsProjet2 = saisies.getProjets().get("projet2");
	//		Assert.assertEquals(new Integer(3),tempsProjet2.getTemps().get(JourSemaine.LUNDI).getHeures());
	//		Assert.assertEquals(new Integer(2),tempsProjet2.getTemps().get(JourSemaine.MARDI).getHeures());
	//	}
}
