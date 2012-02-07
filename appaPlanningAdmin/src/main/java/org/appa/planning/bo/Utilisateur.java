package org.appa.planning.bo;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.appa.planning.bo.Site;
import org.hibernate.validator.constraints.Email;

import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class Utilisateur {

    @NotNull
    @Size(max = 30)
    private String login;

    @NotNull
    @Size(max = 30)
    private String nom;

    @NotNull
    @Size(max = 30)
    private String prenom;

    @NotNull
    @Email
    @Size(max = 80)
    private String email;

    @NotNull
    private UserRole role;

    @NotNull
    @Max(99)
    private Integer nbConges;

    @NotNull
    @Max(99)
    private Integer nbRTT;

    private Boolean quatrevingt = false;

    private Boolean actif = true;

    @ManyToOne
    @NotNull
    private Site site;
}
