package org.appa.planning.service;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.appa.planning.bo.EvenementCalendrier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;

@Service
@RemotingDestination("CalendarService")
public class GoogleCalendarService {

	private static final int TIME_OFFSET = 60;


	@Cacheable("jourFeries")
	public List<Date> getJourFeries() throws Exception{

		List<Date> result = new ArrayList<Date>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		result.add(sdf.parse("01/01/2012"));
		result.add(sdf.parse("09/04/2012"));
		result.add(sdf.parse("01/05/2012"));
		result.add(sdf.parse("08/05/2012"));
		result.add(sdf.parse("17/05/2012"));
		result.add(sdf.parse("28/05/2012"));
		result.add(sdf.parse("14/07/2012"));
		result.add(sdf.parse("15/08/2012"));
		result.add(sdf.parse("01/11/2012"));
		result.add(sdf.parse("11/11/2012"));
		result.add(sdf.parse("25/12/2012"));

		result.add(sdf.parse("01/01/2013"));
		result.add(sdf.parse("01/04/2013"));
		result.add(sdf.parse("01/05/2013"));
		result.add(sdf.parse("08/05/2013"));
		result.add(sdf.parse("09/05/2013"));
		result.add(sdf.parse("20/05/2013"));
		result.add(sdf.parse("14/07/2013"));
		result.add(sdf.parse("15/08/2013"));
		result.add(sdf.parse("01/11/2013"));
		result.add(sdf.parse("11/11/2013"));
		result.add(sdf.parse("25/12/2013"));

		result.add(sdf.parse("01/01/2014"));
		result.add(sdf.parse("21/04/2014"));
		result.add(sdf.parse("01/05/2014"));
		result.add(sdf.parse("08/05/2014"));
		result.add(sdf.parse("29/05/2014"));
		result.add(sdf.parse("09/06/2014"));
		result.add(sdf.parse("14/07/2014"));
		result.add(sdf.parse("15/08/2014"));
		result.add(sdf.parse("01/11/2014"));
		result.add(sdf.parse("11/11/2014"));
		result.add(sdf.parse("25/12/2014"));

		return result;
	}

	/**
	 * chargement des évéments d'1 calendrier
	 * @param email
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 * @throws Exception
	 */
	public List<EvenementCalendrier> loadEvenements(String email, Date dateDebut, Date dateFin) throws Exception{

		List<EvenementCalendrier> result = new ArrayList<EvenementCalendrier>();

		try{
			URL feedUrl = new URL("https://www.google.com/calendar/feeds/"+email+"/private/full");

			CalendarQuery myQuery = new CalendarQuery(feedUrl);
			myQuery.setMinimumStartTime(new DateTime(dateDebut.getTime(),TIME_OFFSET));
			myQuery.setMaximumStartTime(new DateTime(dateFin.getTime(),TIME_OFFSET));

			CalendarEventFeed resultFeed = getService().query(myQuery, CalendarEventFeed.class);
			for (CalendarEventEntry entry : resultFeed.getEntries()) {
				if(!entry.getTimes().isEmpty()){

					long dateDebEvent = entry.getTimes().get(0).getStartTime().getValue();
					long dateFinEvent = entry.getTimes().get(0).getEndTime().getValue();
					String desc = entry.getContent().toString();
					String titre = entry.getTitle().getPlainText();

					//on ne recupère pas les entrées créées par l'application
					if(titre.startsWith("RTT") || titre.startsWith("Congés")){
						break;
					}

					EvenementCalendrier event = new EvenementCalendrier();
					event.setTitre(titre);
					event.setDescription(desc);
					event.setDateDebut(new Date(dateDebEvent));
					event.setDateFin(new Date(dateFinEvent));
					result.add(event);
				}
			}
			Collections.sort(result);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * créée un nouvl évenement dans un calendrier
	 * @param email
	 * @param dateDebut
	 * @param dateFin
	 * @param titre
	 * @param description
	 * @throws Exception
	 */
	public void createEvent(String email, Date dateDebut, Date dateFin, String titre, String description) throws Exception{

		try{
			CalendarEventEntry myEntry = new CalendarEventEntry();

			myEntry.setTitle(new PlainTextConstruct(titre));
			myEntry.setContent(new PlainTextConstruct(description));

			When eventTimes = new When();
			eventTimes.setStartTime(new DateTime(dateDebut.getTime(),TIME_OFFSET));
			eventTimes.setEndTime(new DateTime(dateFin.getTime(),TIME_OFFSET));
			myEntry.addTime(eventTimes);

			getService().insert(new URL("https://www.google.com/calendar/feeds/"+email+"/private/full"), myEntry);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * créée un nouvl évenement dans un calendrier
	 * @param email
	 * @param dateDebut
	 * @param dateFin
	 * @param titre
	 * @param description
	 * @throws Exception
	 */
	public void updateEvent(String email, Date dateDebut, Date dateFin, String oldTitre, String newTitre, String description) throws Exception{
		deleteEvent(email, dateDebut, dateFin, oldTitre);
		createEvent(email, dateDebut, dateFin, newTitre, description);

	}

	/**
	 * supprime un événement
	 * @param email
	 * @param dateDebut
	 * @param dateFin
	 * @param titre
	 * @throws Exception
	 */
	public void deleteEvent(String email, Date dateDebut, Date dateFin, String titre) throws Exception{

		try{
			URL feedUrl = new URL("https://www.google.com/calendar/feeds/"+email+"/private/full");

			CalendarQuery myQuery = new CalendarQuery(feedUrl);
			myQuery.setMinimumStartTime(new DateTime(dateDebut.getTime(),TIME_OFFSET));
			myQuery.setMaximumStartTime(new DateTime(dateFin.getTime(),TIME_OFFSET));

			CalendarEventFeed resultFeed = getService().query(myQuery, CalendarEventFeed.class);

			for (CalendarEventEntry entry : resultFeed.getEntries()) {
				if(entry.getTitle().getPlainText().startsWith(titre)){
					entry.delete();
					break;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private CalendarService getService() throws Exception{

		CalendarService myService = new CalendarService("calendar");
		//le compte utilisé doit être autorisé à accéder au calendrier privé de l'email spécifié
		myService.setUserCredentials(GoogleCredentials.CONNEXION_ACCOUNT, GoogleCredentials.CONNEXION_PASSWORD);

		return myService;
	}
}
