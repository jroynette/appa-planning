package org.appa.planning.repository;

import org.appa.planning.bo.Utilisateur;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Classe permettant de gérer la persistance pour les objets de type {@link Utilisateur}
 * 
 * @author jroynett
 *
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

	/**
	 * @return l'utilisateur par rapport à son login
	 */
	@Cacheable("org.appa.planning.bo.Utilisateur")
	Utilisateur findByLogin(String login);

}
