package com.example.jee_gestion.repository;

import java.util.List;
import java.util.Optional;

import com.example.jee_gestion.Model.Cours;
import com.example.jee_gestion.Model.Inscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    Optional<Inscription> findByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
    
    void deleteAllByEtudiantId(Long etudiantId);
}

