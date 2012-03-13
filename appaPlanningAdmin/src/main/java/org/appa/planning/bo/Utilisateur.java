package org.appa.planning.bo;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.appa.planning.bo.Site;
import org.hibernate.validator.constraints.Email;

import javax.persistence.FetchType;
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

//    @ManyToOne(fetch=FetchType.EAGER)
//    @NotNull
//    private Site site;

    @Max(99)
    private Integer deltaJoursConges = 0;

    @Max(99)
    private Integer deltaJoursRTT = 0;

	public Integer getDeltaJoursConges() {
		return deltaJoursConges;
	}

	public void setDeltaJoursConges(Integer deltaJoursConges) {
		this.deltaJoursConges = deltaJoursConges;
	}

	public Integer getDeltaJoursRTT() {
		return deltaJoursRTT;
	}

	public void setDeltaJoursRTT(Integer deltaJoursRTT) {
		this.deltaJoursRTT = deltaJoursRTT;
	}
}
