package org.appa.planning.web;

import org.appa.planning.bo.Projet;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "projets", formBackingObject = Projet.class)
@RequestMapping("/projets")
@Controller
public class ProjetController {
}
