package org.appa.planning.repository;

import java.util.Date;
import java.util.List;

import org.appa.planning.bo.SaisieTemps;
import org.appa.planning.vo.LigneReportingProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de gérer la persistance pour les objets de type {@link SaisieTemps}
 * 
 * @author jroynett
 *
 */
@Repository
@RemotingDestination("ReportingTempsRepository")
public interface ReportingTempsRepository extends JpaRepository<SaisieTemps, Long> {

	/**s
	 * @return la repartition des saisies de temps par projet et utilisateur entre les 2 dates
	 */
	@Query("select new org.appa.planning.vo.LigneReportingProjet(s.projet.nom, s.utilisateur.prenom || ' ' || s.utilisateur.nom, sum(s.heures)) " +
			"from SaisieTemps s where s.date >= ?1 and s.date <= ?2 " +
			"group by s.projet.nom, s.utilisateur order by s.projet.nom")
	List<LigneReportingProjet> reportForProjectsByDates(Date dateDebut, Date dateFin );

	/**
	 * @return la repartition des saisies de temps par projet pour un utilisateur entre les 2 dates (triee de manière decroissante sur le nombre d'heures)
	 */
	@Query("select new org.appa.planning.vo.LigneReportingProjet(s.projet.nom, s.utilisateur.prenom || ' ' || s.utilisateur.nom, sum(s.heures)) " +
			"from SaisieTemps s where s.utilisateur.id = ?1 and s.date >= ?2 and s.date <= ?3 " +
			"group by s.projet.nom, s.utilisateur " +
			"order by s.projet.nom")
	List<LigneReportingProjet> reportForUserByDates(Integer utilisateurId, Date dateDebut, Date dateFin );

}
