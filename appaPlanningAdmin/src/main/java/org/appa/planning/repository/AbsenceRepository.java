package org.appa.planning.repository;

import java.util.Date;
import java.util.List;

import org.appa.planning.bo.Absence;
import org.appa.planning.bo.StatutAbsence;
import org.appa.planning.bo.TypeAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de g�rer la persistance pour les objets de type {@link Absence}
 * 
 * @author jroynett
 *
 */
@Repository
@RemotingDestination("AbsenceRepository")
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

	/**
	 * @return la liste des absences de l'utilisateur
	 */
	@Query("from Absence a where a.utilisateur.id = ?1")
	List<Absence> findByUtilisateur(Long userId);

	/**
	 * @return la liste des absences de l'utilisateur apr�s la date sp�cifi�e
	 */
	@Query("from Absence a where a.utilisateur.id = ?1 and a.dateDebut >= ?2")
	List<Absence> findByUtilisateur(Long userId, Date dateDebut);

	/**
	 * @return la liste des absences de l'utilisateur � afficher entre 2 dates
	 * (les absences partielles, qui ne rentrent pas int�gralement dans la plage sont r�cup�r�es)
	 */
	@Query("from Absence a where a.utilisateur.id = ?1 and a.dateFin >= ?2 and a.dateDebut <= ?3")
	List<Absence> findByUtilisateur(Long userId, Date dateDebut, Date dateFin );

	/**
	 * @return la liste des absences de l'utilisateur � afficher entre 2 dates
	 * (les absences partielles, qui ne rentrent pas int�gralement dans la plage sont r�cup�r�es)
	 */
	@Query("from Absence a where a.utilisateur.id = ?1 and a.dateFin >= ?2 and a.dateDebut <= ?3 and a.type = ?4")
	List<Absence> findByUtilisateur(Long userId, Date dateDebut, Date dateFin, TypeAbsence type);

	/**
	 * @return la liste des absences de l'utilisateur � afficher entre 2 dates
	 * (les absences partielles, qui ne rentrent pas int�gralement dans la plage sont r�cup�r�es)
	 */
	@Query("from Absence a where a.dateFin >= ?1 and a.dateDebut <= ?2")
	List<Absence> findAll(Date dateDebut, Date dateFin );

	/**
	 * @return la liste des absences d'un statut précis
	 */
	@Query("from Absence a where a.statut = ?1 order by a.dateDebut")
	List<Absence> findByStatut(StatutAbsence statut);



}
