package org.appa.planning.service.mail;

import org.appa.planning.bo.Absence;

public interface IMailService {

	public void notifierCreationAbsence(Absence a);

	public void notifierSuppressionAbsence(Absence a);

}
