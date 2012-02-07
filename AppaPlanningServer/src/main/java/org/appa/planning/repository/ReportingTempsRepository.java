package org.appa.planning.repository;

import java.util.Date;
import java.util.List;

import org.appa.planning.bo.LigneReportingProjet;
import org.appa.planning.bo.SaisieTemps;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe permettant de gérer la persistance pour les objets de type {@link SaisieTemps}
 * 
 * @author jroynett
 *
 */
@Repository
public interface ReportingTempsRepository extends CrudRepository<SaisieTemps, Long> {

	/**s
	 * @return la repartition des saisies de temps par projet et utilisateur entre les 2 dates
	 */
	@Query("select s.projet.nom as projet, s.utilisateur.prenom || ' ' || s.utilisateur.nom as utilisateur, sum(s.temps) as temps " +
			"from SaisieTemps s where s.date >= ?1 and s.date <= ?2 " +
			"group by s.projet.nom, s.utilisateur")
	List<LigneReportingProjet> reportForProjectsByDates(Date dateDebut, Date dateFin );

	/**
	 * @return la repartition des saisies de temps par projet pour un utilisateur entre les 2 dates
	 */
	@Query("select s.projet.nom as projet, s.utilisateur.prenom || ' ' || s.utilisateur.nom as utilisateur, sum(s.temps) as temps " +
			"from SaisieTemps s where s.utilisateur.id = ?1 and s.date >= ?2 and s.date <= ?3 " +
			"group by s.projet.nom, s.utilisateur")
	List<LigneReportingProjet> reportForUserByDates(Integer utilisateurId, Date dateDebut, Date dateFin );

}
