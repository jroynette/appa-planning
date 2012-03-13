package org.appa.planning.service;

import java.net.URL;
import java.text.SimpleDateFormat;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;


public class GoogleAgendaServiceTest {

	public static void main(String[] args) throws Exception {

		// Create a CalenderService and authenticate
		CalendarService myService = new CalendarService("feries");
		myService.setUserCredentials("jroynette@gmail.com", "faceback");


		URL feedUrl = new URL("https://www.google.com/calendar/feeds/french__fr%40holiday.calendar.google.com/public/full");
		//URL feedUrl = new URL("https://www.google.com/calendar/feeds/aurore.deconinck%40gmail.com/private/full");
		CalendarEventFeed resultFeed = myService.getFeed(feedUrl, CalendarEventFeed.class);
		for (int i = 0; i < resultFeed.getEntries().size(); i++) {
			CalendarEventEntry entry = resultFeed.getEntries().get(i);
			System.out.println("\t" + entry.getTitle().getPlainText() + "-" + entry.getTimes().get(0).getStartTime() + " - " + entry.getTimes().get(0).getEndTime() );
		}


		// Send the request and print the response
		//		URL feedUrl = new URL("https://www.google.com/calendar/feeds/aurore.deconinck%40gmail.com/private/full");
		//		CalendarFeed resultFeed = myService.getFeed(feedUrl, CalendarFeed.class);
		//		System.out.println("Your calendars:");
		//		System.out.println();
		//		for (int i = 0; i < resultFeed.getEntries().size(); i++) {
		//			CalendarEntry entry = resultFeed.getEntries().get(i);
		//			System.out.println("\t" + entry.getTitle().getPlainText());
		//		}


		CalendarEventEntry myEntry = new CalendarEventEntry();

		myEntry.setTitle(new PlainTextConstruct("Cin� avec ch�ri"));
		myEntry.setContent(new PlainTextConstruct("S�ance de cin�"));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		DateTime startTime =  new DateTime(sdf.parse("19/11/2011 15:00"));
		DateTime endTime =  new DateTime(sdf.parse("19/11/2011 17:00"));
		When eventTimes = new When();
		eventTimes.setStartTime(startTime);
		eventTimes.setEndTime(endTime);
		myEntry.addTime(eventTimes);

		//		// Send the request and receive the response:
		//
		//		//CalendarEventEntry insertedEntry = myService.insert(new URL("https://www.google.com/calendar/feeds/jroynette%40gmail.com/public/basic"), myEntry);
		//		CalendarEventEntry insertedEntry = myService.insert(new URL("https://www.google.com/calendar/feeds/aurore.deconinck%40gmail.com/private/full"), myEntry);



	}

}
