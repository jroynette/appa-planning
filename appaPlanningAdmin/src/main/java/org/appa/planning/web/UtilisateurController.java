package org.appa.planning.web;

import org.appa.planning.bo.Utilisateur;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "utilisateurs", formBackingObject = Utilisateur.class)
@RequestMapping("/utilisateurs")
@Controller
public class UtilisateurController {
}
