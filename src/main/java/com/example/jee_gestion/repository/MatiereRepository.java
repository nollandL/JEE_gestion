package com.example.jee_gestion.repository;

import com.example.jee_gestion.Model.Matiere;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
	@Query("SELECT m FROM Matiere m JOIN m.enseignants e WHERE e.id = :enseignantId")
	List<Matiere> findMatieresByEnseignantId(Long enseignantId);
	
    @Query("SELECT m FROM Matiere m JOIN m.enseignants e WHERE e.id = :enseignantId")
    List<Matiere> findByEnseignantId(@Param("enseignantId") Long enseignantId);
}

