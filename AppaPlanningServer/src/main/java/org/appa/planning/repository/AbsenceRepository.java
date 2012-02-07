package org.appa.planning.repository;

import java.util.Date;
import java.util.List;

import org.appa.planning.bo.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de gérer la persistance pour les objets de type {@link Absence}
 * 
 * @author jroynett
 *
 */
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

	/**
	 * @return la liste des absences de l'utilisateur
	 */
	@Query("from Absence a where a.utilisateur.id = ?1")
	List<Absence> findByUtilisateur(Integer userId);

	/**
	 * @return la liste des absences de l'utilisateur après la date spécifiée
	 */
	@Query("from Absence a where a.utilisateur.id = ?1 and a.dateDebut >= ?2")
	List<Absence> findByUtilisateur(Integer userId, Date dateDebut);

	/**
	 * @return la liste des absences de l'utilisateur à afficher entre 2 dates
	 * (les absences partielles, qui ne rentrent pas intégralement dans la plage sont récupérées)
	 */
	@Query("from Absence a where a.utilisateur.id = ?1 and a.dateFin >= ?2 and a.dateDebut <= ?3")
	List<Absence> findByUtilisateur(Integer userId, Date dateDebut, Date dateFin );

	/**
	 * @return la liste des absences de l'utilisateur à afficher entre 2 dates
	 * (les absences partielles, qui ne rentrent pas intégralement dans la plage sont récupérées)
	 */
	@Query("from Absence a where a.dateFin >= ?1 and a.dateDebut <= ?2")
	List<Absence> findAll(Date dateDebut, Date dateFin );



}
