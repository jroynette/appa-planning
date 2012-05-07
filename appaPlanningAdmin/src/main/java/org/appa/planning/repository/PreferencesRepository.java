package org.appa.planning.repository;

import org.appa.planning.bo.DemandeAbsence;
import org.appa.planning.bo.PrefsUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de g�rer la persistance pour les objets de type {@link DemandeAbsence}
 * 
 * @author jroynett
 *
 */
@Repository
public interface PreferencesRepository extends JpaRepository<PrefsUtilisateur, Long> {
}
