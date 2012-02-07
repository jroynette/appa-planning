// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.appa.planning.web;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.appa.planning.bo.Projet;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ProjetController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String ProjetController.create(@Valid Projet projet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("projet", projet);
            return "projets/create";
        }
        uiModel.asMap().clear();
        projet.persist();
        return "redirect:/projets/" + encodeUrlPathSegment(projet.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String ProjetController.createForm(Model uiModel) {
        uiModel.addAttribute("projet", new Projet());
        return "projets/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String ProjetController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("projet", Projet.findProjet(id));
        uiModel.addAttribute("itemId", id);
        return "projets/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String ProjetController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("projets", Projet.findProjetEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Projet.countProjets() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("projets", Projet.findAllProjets());
        }
        return "projets/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ProjetController.update(@Valid Projet projet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("projet", projet);
            return "projets/update";
        }
        uiModel.asMap().clear();
        projet.merge();
        return "redirect:/projets/" + encodeUrlPathSegment(projet.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String ProjetController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("projet", Projet.findProjet(id));
        return "projets/update";
    }
    
    @ModelAttribute("projets")
    public Collection<Projet> ProjetController.populateProjets() {
        return Projet.findAllProjets();
    }
    
    String ProjetController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}