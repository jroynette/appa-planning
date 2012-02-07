package org.appa.planning.bo;

import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findProjetsByAnnee" })
public class Projet {

    @NotNull
    @Size(max = 30)
    private String nom;

    @Size(max = 100)
    private String description;

    @NotNull
    private Integer annee;
}
