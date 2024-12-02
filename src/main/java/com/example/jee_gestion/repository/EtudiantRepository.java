package com.example.jee_gestion.repository;

import com.example.jee_gestion.Model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    @Query("SELECT DISTINCT i.etudiant FROM Inscription i WHERE i.cours.matiere.id = :matiereId")
    List<Etudiant> findEtudiantsByCoursMatiereId(@Param("matiereId") Long matiereId);

    @Query("SELECT DISTINCT n.etudiant FROM Note n WHERE n.matiere.id = :matiereId")
    List<Etudiant> findByMatiere(@Param("matiereId") Long matiereId);

    @Query("SELECT DISTINCT e FROM Etudiant e WHERE e.nom LIKE %:keyword% OR e.prenom LIKE %:keyword%")
    List<Etudiant> searchByNomOrPrenom(@Param("keyword") String keyword);
    
    @Query("""
            SELECT e
            FROM Etudiant e
            WHERE e.nom = :nom AND e.prenom = :prenom AND e.contact = :contact
        """)
        Optional<Etudiant> findByNomPrenomContact(@Param("nom") String nom, @Param("prenom") String prenom, @Param("contact") String contact);

}
