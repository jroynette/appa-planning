package org.appa.planning.repository;

import java.util.Date;
import java.util.List;

import org.appa.planning.bo.SaisieTemps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de g�rer la persistance pour les objets de type {@link SaisieTemps}
 * 
 * @author jroynett
 *
 */
@Repository
public interface SaisieTempsRepository extends JpaRepository<SaisieTemps, Long> {

	/**
	 * @return la liste des saisies de l'utilisateur entre les 2 dates
	 */
	@Query("from SaisieTemps s where s.utilisateur.id = ?1 and s.date >= ?2 and s.date <= ?3")
	List<SaisieTemps> findByUtilisateur(Long userId, Date dateDebut, Date dateFin );

	/**
	 * @return la liste des saisies d'un projet entre les 2 dates
	 */
	@Query("from SaisieTemps s where s.projet.nom = ?1 and s.date >= ?2 and s.date <= ?3 order by s.date")
	List<SaisieTemps> findByProjet(String nomProjet, Date dateDebut, Date dateFin );

	/**
	 * @return la liste des saisies d'un projet et d'un utilisateur entre les 2 dates
	 */
	@Query("from SaisieTemps s where s.projet.nom = ?1 and s.utilisateur.id = ?2 and s.date >= ?3 and s.date <= ?4 order by s.date")
	List<SaisieTemps> findByProjetAndUtilisateur(String nomProjet, Long userId, Date dateDebut, Date dateFin );
}
