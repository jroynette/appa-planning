package org.appa.planning.service.mail;

import org.appa.planning.bo.DemandeAbsence;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value={"dev","export-schema"})
public class DummyMailService implements IMailService{

	@Override
	public void notifierCreationAbsence(DemandeAbsence a){

	}

	@Override
	public void notifierSuppressionAbsence(DemandeAbsence a){

	}
}
