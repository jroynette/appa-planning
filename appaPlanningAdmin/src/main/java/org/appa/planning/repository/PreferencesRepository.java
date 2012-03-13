package org.appa.planning.repository;

import org.appa.planning.bo.Absence;
import org.appa.planning.bo.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de g�rer la persistance pour les objets de type {@link Absence}
 * 
 * @author jroynett
 *
 */
@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
}
