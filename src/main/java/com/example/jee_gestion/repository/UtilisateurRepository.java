package com.example.jee_gestion.repository;

import com.example.jee_gestion.Model.Role;
import com.example.jee_gestion.Model.Utilisateur;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query("""
        SELECT u 
        FROM Utilisateur u 
        LEFT JOIN u.enseignant e
        LEFT JOIN e.matieres
        WHERE u.username = :username AND u.password = :password
    """)
    Utilisateur findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    @Modifying
    @Query("DELETE FROM Utilisateur u WHERE u.etudiant.id = :etudiantId")
    void deleteAllByEtudiantId(@Param("etudiantId") Long etudiantId);



    @Query("""
        SELECT u
        FROM Utilisateur u
        WHERE u.username = :username
    """)
    Utilisateur findByUsername(@Param("username") String username);
    
    @Query("""
    	    SELECT u
    	    FROM Utilisateur u
    	    WHERE (u.enseignant.id = :id OR u.etudiant.id = :id)
    	""")
    	Optional<Utilisateur> findByLinkedId(@Param("id") Long id);

    Optional<Utilisateur> findByEnseignantId(Long enseignantId);
    
    @Query("""
            SELECT u
            FROM Utilisateur u
            WHERE u.role = :role
            AND ((u.enseignant.nom = :nom AND u.enseignant.prenom = :prenom AND u.enseignant.contact = :contact)
                 OR (u.etudiant.nom = :nom AND u.etudiant.prenom = :prenom AND u.etudiant.contact = :contact))
        """)
    Optional<Utilisateur> findByRoleAndDetails(@Param("role") Role role, 
                                               @Param("nom") String nom, 
                                               @Param("prenom") String prenom, 
                                               @Param("contact") String contact);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Utilisateur u WHERE u.enseignant.id = :enseignantId")
    void deleteByEnseignantId(@Param("enseignantId") Long enseignantId);    
}
