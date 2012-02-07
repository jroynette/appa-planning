package org.appa.planning.web;

import org.appa.planning.bo.Site;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "sites", formBackingObject = Site.class)
@RequestMapping("/sites")
@Controller
public class SiteController {
}
