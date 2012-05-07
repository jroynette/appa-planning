package org.appa.planning.repository;

import java.util.Date;
import java.util.List;

import org.appa.planning.bo.DemandeAbsence;
import org.appa.planning.bo.StatutAbsence;
import org.appa.planning.bo.TypeAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de g�rer la persistance pour les objets de type {@link DemandeAbsence}
 * 
 * @author jroynett
 *
 */
@Repository
@RemotingDestination("AbsenceRepository")
public interface AbsenceRepository extends JpaRepository<DemandeAbsence, Long> {

	/**
	 * @return la liste des absences de l'utilisateur
	 */
	@Query("from DemandeAbsence a where a.utilisateur.id = ?1")
	List<DemandeAbsence> findByUtilisateur(Long userId);

	/**
	 * @return la liste des absences de l'utilisateur apr�s la date sp�cifi�e
	 */
	@Query("from DemandeAbsence a where a.utilisateur.id = ?1 and a.dateDebut >= ?2 order by a.dateDebut")
	List<DemandeAbsence> findByUtilisateur(Long userId, Date dateDebut);

	/**
	 * @return la liste des absences de l'utilisateur � afficher entre 2 dates
	 * (les absences partielles, qui ne rentrent pas int�gralement dans la plage sont r�cup�r�es)
	 */
	@Query("from DemandeAbsence a where a.utilisateur.id = ?1 and a.dateFin >= ?2 and a.dateDebut <= ?3 order by a.dateDebut")
	List<DemandeAbsence> findByUtilisateur(Long userId, Date dateDebut, Date dateFin );

	/**
	 * @return la liste des absences de l'utilisateur � afficher entre 2 dates
	 * (les absences partielles, qui ne rentrent pas int�gralement dans la plage sont r�cup�r�es)
	 */
	@Query("from DemandeAbsence a where a.utilisateur.id = ?1 and a.dateFin >= ?2 and a.dateDebut <= ?3 and a.type = ?4 order by a.dateDebut")
	List<DemandeAbsence> findByUtilisateur(Long userId, Date dateDebut, Date dateFin, TypeAbsence type);

	/**
	 * @return la liste des absences de l'utilisateur � afficher entre 2 dates
	 * (les absences partielles, qui ne rentrent pas int�gralement dans la plage sont r�cup�r�es)
	 */
	@Query("from DemandeAbsence a where a.dateFin >= ?1 and a.dateDebut <= ?2")
	List<DemandeAbsence> findAll(Date dateDebut, Date dateFin );

	/**
	 * @return la liste des absences d'un statut précis
	 */
	@Query("from DemandeAbsence a where a.statut = ?1 order by a.dateDebut")
	List<DemandeAbsence> findByStatut(StatutAbsence statut);



}
