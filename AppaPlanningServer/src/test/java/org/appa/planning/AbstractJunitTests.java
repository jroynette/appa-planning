package org.appa.planning;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.appa.planning.service.DataTestGenerator;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations={"/spring/app-context.xml","/spring/datasource.xml"})
@ActiveProfiles(profiles={"test","testdatas"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true)
public class AbstractJunitTests {

	@Autowired
	protected DataTestGenerator dataGenerator;

	@PersistenceContext
	protected EntityManager entityManager;

	protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Before
	public void setup() throws Exception {

		//		dataGenerator.createProjet("projet1", 2010);
		//		dataGenerator.createProjet("projet2", 2010);
		//		dataGenerator.createProjet("projet1", 2011);
		//		dataGenerator.createProjet("projet2", 2011);
		//		dataGenerator.createProjet("projet3", 2012);
		//
		//		dataGenerator.createUtilisateur("user1");
		//		dataGenerator.createUtilisateur("user2");
		//		dataGenerator.createUtilisateur("user3");
	}
}
