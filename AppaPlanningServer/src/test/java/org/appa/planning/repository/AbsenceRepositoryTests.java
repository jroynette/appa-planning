package org.appa.planning.repository;

import junit.framework.Assert;

import org.appa.planning.AbstractJunitTests;
import org.appa.planning.bo.StatutAbsence;
import org.appa.planning.bo.TypeAbsence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
public class AbsenceRepositoryTests extends AbstractJunitTests {

	@Autowired
	private AbsenceRepository absenceRepository;

	@Override
	@Before
	public void setup() throws Exception {

		super.setup();

		dataGenerator.createAbsence(TypeAbsence.CONGE, StatutAbsence.POSE, "01/01/2011", "05/01/2011", "user1");
		dataGenerator.createAbsence(TypeAbsence.RTT, StatutAbsence.VALIDE, "10/01/2011", "11/01/2011", "user1");
		dataGenerator.createAbsence(TypeAbsence.CONGE, StatutAbsence.REFUSE, "01/01/2011", "03/01/2011", "user2");
	}

	@Test
	@Transactional
	public void testFindAllByDates() throws Exception {

		Assert.assertEquals(3,absenceRepository.findAll(sdf.parse("01/01/2011"), sdf.parse("01/02/2011")));
		Assert.assertEquals(3,absenceRepository.findAll(sdf.parse("01/01/2011"), sdf.parse("05/01/2011")));
		Assert.assertEquals(0,absenceRepository.findAll(sdf.parse("01/02/2011"), sdf.parse("01/03/2011")));

	}

}
