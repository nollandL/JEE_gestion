package com.example.jee_gestion.repository;

import java.util.List;

import com.example.jee_gestion.Model.Cours;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;




@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    // Trouver les cours sans enseignant
    List<Cours> findByEnseignantIsNull();

    // Trouver les cours attribués à un enseignant spécifique
    List<Cours> findByEnseignantId(Long enseignantId);
    
    @Query("SELECT c FROM Cours c WHERE c.enseignant IS NULL AND c.matiere IN " +
            "(SELECT m FROM Matiere m JOIN m.enseignants e WHERE e.id = :enseignantId)")
     List<Cours> findCoursSansProfesseurByEnseignantMatiere(@Param("enseignantId") Long enseignantId);
    
    @Modifying
    @Query("UPDATE Cours c SET c.enseignant = NULL WHERE c.enseignant.id = :enseignantId")
    void dissocierCours(@Param("enseignantId") Long enseignantId);
}
