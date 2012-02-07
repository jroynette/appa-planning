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
import org.appa.planning.bo.Site;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect SiteController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String SiteController.create(@Valid Site site, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("site", site);
            return "sites/create";
        }
        uiModel.asMap().clear();
        site.persist();
        return "redirect:/sites/" + encodeUrlPathSegment(site.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String SiteController.createForm(Model uiModel) {
        uiModel.addAttribute("site", new Site());
        return "sites/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String SiteController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("site", Site.findSite(id));
        uiModel.addAttribute("itemId", id);
        return "sites/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String SiteController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("sites", Site.findSiteEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Site.countSites() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("sites", Site.findAllSites());
        }
        return "sites/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String SiteController.update(@Valid Site site, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("site", site);
            return "sites/update";
        }
        uiModel.asMap().clear();
        site.merge();
        return "redirect:/sites/" + encodeUrlPathSegment(site.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String SiteController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("site", Site.findSite(id));
        return "sites/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String SiteController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Site.findSite(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/sites";
    }
    
    @ModelAttribute("sites")
    public Collection<Site> SiteController.populateSites() {
        return Site.findAllSites();
    }
    
    String SiteController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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