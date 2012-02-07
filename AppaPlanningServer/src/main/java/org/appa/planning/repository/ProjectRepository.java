package org.appa.planning.repository;

import java.util.List;

import org.appa.planning.bo.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de gérer la persistance pour les objets de type {@link Projet}
 * 
 * @author jroynett
 *
 */
@Repository
public interface ProjectRepository extends JpaRepository<Projet, Long> {

	/**
	 * @return la liste des projets pour une année donnée
	 */
	List<Projet> findByAnnee(Integer annee);

}
