package org.appa.planning.service.mail;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.appa.planning.bo.Absence;
import org.appa.planning.bo.TypeAbsence;
import org.appa.planning.util.PostmarkMailSender;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Profile("cloud")
public class MailService implements IMailService {

	private MailSender mailSender;

	//TODO à remplacer
	private static final String MAIL_RESPONSABLE =  "jroynette@gmail.com";

	@PostConstruct
	private void init(){
		mailSender = new PostmarkMailSender("d1d49b70-af43-4ab9-b69b-2d54aea42930");
	}

	@Override
	public void notifierCreationAbsence(Absence a){

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String type = ((a.getType().equals(TypeAbsence.RTT))?"RTT":"congés");
		String dateDeb = sdf.format(a.getDateDebut());
		if(a.getDebutPM()){
			dateDeb += " (Après-Midi)";
		}
		String dateFin = sdf.format(a.getDateDebut());
		if(a.getFinAM()){
			dateDeb += " (Matin)";
		}

		String subject = "Demande de " + type;

		String text = a.getUtilisateur().getPrenom() + " a posé une nouvelle demande de " + type + " du "+ dateDeb +" au "+ dateFin+".";

		sendMail(MAIL_RESPONSABLE, subject, text);
	}

	@Override
	public void notifierSuppressionAbsence(Absence a){

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String type = ((a.getType().equals(TypeAbsence.RTT))?"RTT":"congés");
		String dateDeb = sdf.format(a.getDateDebut());
		if(a.getDebutPM()){
			dateDeb += " (Après-Midi)";
		}
		String dateFin = sdf.format(a.getDateDebut());
		if(a.getFinAM()){
			dateDeb += " (Matin)";
		}

		String subject = "Demande de " + type;

		String text = a.getUtilisateur().getPrenom() + " a annulée sa demande de " + type + " du "+ dateDeb +" au "+ dateFin+".";

		sendMail(MAIL_RESPONSABLE, subject, text);

	}

	private void sendMail(String to, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();
		//TODO à remplacer
		message.setFrom("adeconinck@appanpc.fr");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}
}
