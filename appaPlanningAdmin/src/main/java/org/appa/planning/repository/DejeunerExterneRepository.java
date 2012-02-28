package org.appa.planning.repository;

import java.util.Date;
import java.util.List;

import org.appa.planning.bo.DejeunerExterne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de g�rer la persistance pour les objets de type {@link DejeunerExterne}
 * 
 * @author jroynett
 *
 */
@Repository
public interface DejeunerExterneRepository extends JpaRepository<DejeunerExterne, Long> {

	/**
	 * @return la liste des dejeuner externe de l'utilisateur � afficher entre 2 dates
	 */
	@Query("from DejeunerExterne a where a.utilisateur.id = ?1 and a.date >= ?2 and a.date <= ?3")
	List<DejeunerExterne> findByUtilisateur(Long userId, Date dateDebut, Date dateFin );
}
