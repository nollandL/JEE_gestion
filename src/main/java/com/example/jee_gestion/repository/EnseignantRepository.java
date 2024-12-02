package com.example.jee_gestion.repository;

import com.example.jee_gestion.Model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
	
	 @Query("""
		        SELECT e
		        FROM Enseignant e
		        WHERE e.nom = :nom AND e.prenom = :prenom AND e.contact = :contact
	 """)
	 Optional<Enseignant> findByNomPrenomContact(@Param("nom") String nom, @Param("prenom") String prenom, @Param("contact") String contact);

	 @Modifying
	 @Query("DELETE FROM Enseignant e JOIN e.matieres m WHERE e.id = :enseignantId")
	 void supprimerRelationsAvecMatieres(@Param("enseignantId") Long enseignantId);
	 
	  @Modifying
    @Query(value = "DELETE FROM enseignants_matieres WHERE enseignant_id = :enseignantId", nativeQuery = true)
    void supprimerRelationsDansEnseignantsMatieres(@Param("enseignantId") Long enseignantId);


}
