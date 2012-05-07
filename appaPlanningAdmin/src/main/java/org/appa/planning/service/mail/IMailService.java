package org.appa.planning.service.mail;

import org.appa.planning.bo.DemandeAbsence;

public interface IMailService {

	public void notifierCreationAbsence(DemandeAbsence a);

	public void notifierSuppressionAbsence(DemandeAbsence a);

}
