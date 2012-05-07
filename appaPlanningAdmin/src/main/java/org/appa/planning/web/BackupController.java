package org.appa.planning.web;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.appa.planning.repository.DejeunerExterneRepository;
import org.appa.planning.repository.SaisieTempsRepository;
import org.appa.planning.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BackupController {

	@Autowired
	private BackupService service;

	@Autowired
	private SaisieTempsRepository repository;

	@Autowired
	private DejeunerExterneRepository repository2;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//List<SaisieTemps> list = repository.findAll();
		//for (SaisieTemps saisieTemps : list) {

		//			GregorianCalendar calendar = new GregorianCalendar();
		//			calendar.setTime(saisieTemps.getDate());
		//			calendar.add(Calendar.DATE, -1);
		//
		//			saisieTemps.setDate(calendar.getTime());
		//			repository.save(saisieTemps);
		//}


		//		List<DejeunerExterne> list = repository2.findAll();
		//		for (DejeunerExterne dej : list) {
		//
		//			GregorianCalendar calendar = new GregorianCalendar();
		//			calendar.setTime(dej.getDate());
		//			calendar.add(Calendar.DATE, -1);
		//
		//			dej.setDate(calendar.getTime());
		//			repository2.save(dej);
		//		}

		System.out.println("!!!! " + new GregorianCalendar() + " > " + new GregorianCalendar().getFirstDayOfWeek() + " > " + new GregorianCalendar().get(Calendar.DAY_OF_WEEK));

		return null;
	}

	@RequestMapping(value = "/backup", method = RequestMethod.GET)
	public ModelAndView backup(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String bdd = "MySQL";
		if(request.getParameter("bdd") != null){
			bdd = request.getParameter("bdd");
		}

		StringBuffer out = service.createBackup(bdd);

		response.setContentType("text/plain");
		response.setContentLength(out.toString().getBytes().length);
		response.setHeader( "Content-Disposition", "attachment;filename=backup" );

		response.getOutputStream().write(out.toString().getBytes());
		response.getOutputStream().flush();
		response.getOutputStream().close();

		return null;
	}

}
