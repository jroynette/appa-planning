// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.appa.planning.bo;

import java.lang.String;

privileged aspect Projet_Roo_ToString {
    
    public String Projet.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Annee: ").append(getAnnee()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Nom: ").append(getNom()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
