package org.appa.planning.service;

import java.util.Date;

import org.appa.planning.AbstractJunitTests;
import org.appa.planning.bo.DemandeAbsence;
import org.appa.planning.bo.TypeAbsence;
import org.appa.planning.bo.Utilisateur;
import org.appa.planning.service.mail.IMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MailServiceTests extends AbstractJunitTests {

	@Autowired
	private IMailService service;

	@Test
	public void testsaveSaisieTemps() throws Exception {

		DemandeAbsence absence = new DemandeAbsence();

		absence.setType(TypeAbsence.RTT);
		absence.setDateDebut(new Date(2012,02,01));
		absence.setDateFin(new Date(2012,02,05));

		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPrenom("Julien");
		absence.setUtilisateur(utilisateur);

		service.notifierCreationAbsence(absence);
	}
}
