// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.appa.planning.web;

import java.lang.String;
import org.appa.planning.bo.Projet;
import org.appa.planning.bo.Site;
import org.appa.planning.bo.Utilisateur;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new ProjetConverter());
        registry.addConverter(new SiteConverter());
        registry.addConverter(new UtilisateurConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
    static class org.appa.planning.web.ApplicationConversionServiceFactoryBean.ProjetConverter implements Converter<Projet, String> {
        public String convert(Projet projet) {
            return new StringBuilder().append(projet.getNom()).append(" ").append(projet.getDescription()).append(" ").append(projet.getAnnee()).toString();
        }
        
    }
    
    static class org.appa.planning.web.ApplicationConversionServiceFactoryBean.SiteConverter implements Converter<Site, String> {
        public String convert(Site site) {
            return new StringBuilder().append(site.getNom()).toString();
        }
        
    }
    
    static class org.appa.planning.web.ApplicationConversionServiceFactoryBean.UtilisateurConverter implements Converter<Utilisateur, String> {
        public String convert(Utilisateur utilisateur) {
            return new StringBuilder().append(utilisateur.getLogin()).append(" ").append(utilisateur.getNom()).append(" ").append(utilisateur.getPrenom()).append(" ").append(utilisateur.getEmail()).toString();
        }
        
    }
    
}
