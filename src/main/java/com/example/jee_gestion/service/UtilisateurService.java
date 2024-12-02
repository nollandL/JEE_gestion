package com.example.jee_gestion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jee_gestion.Model.Enseignant;
import com.example.jee_gestion.Model.Etudiant;
import com.example.jee_gestion.Model.Role;
import com.example.jee_gestion.Model.Utilisateur;
import com.example.jee_gestion.repository.EnseignantRepository;
import com.example.jee_gestion.repository.EtudiantRepository;
import com.example.jee_gestion.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final EtudiantRepository etudiantRepository;
    private final EnseignantRepository enseignantRepository; 

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository, EtudiantRepository etudiantRepository, EnseignantRepository enseignantRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.etudiantRepository = etudiantRepository;
        this.enseignantRepository = enseignantRepository;
    }
    
    @Transactional
    public Utilisateur findByIdWithDetails(Long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (utilisateur.getRole() == Role.ENSEIGNANT) {
            utilisateur.getEnseignant().getMatieres().size(); // Force le chargement des spécialités
        } else if (utilisateur.getRole() == Role.ETUDIANT) {
            utilisateur.getEtudiant().getInscriptions().size(); // Force le chargement des inscriptions
        }

        return utilisateur;
    }


    @Transactional(readOnly = true)
    public Utilisateur findById(Long userId) {
        return utilisateurRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
    }

    @Transactional
    public Utilisateur findByIdWithSpecialites(Long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (utilisateur.getRole() == Role.ENSEIGNANT) {
            utilisateur.getEnseignant().getMatieres().size(); // Force le chargement des spécialités
        }
        return utilisateur;
    }
    
    @Transactional
    public Utilisateur creerCompte(String username, String password, Role role, String nom, String prenom, String contact) {
        // Vérifier si un compte existe déjà pour cet utilisateur et ce rôle
        Optional<Utilisateur> existingUser = utilisateurRepository.findByRoleAndDetails(role, nom, prenom, contact);

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Un compte existe déjà pour cet utilisateur avec le rôle spécifié.");
        }

        // Identifier si c'est un enseignant ou un étudiant
        if (role == Role.ENSEIGNANT) {
            Enseignant enseignant = enseignantRepository.findByNomPrenomContact(nom, prenom, contact)
                    .orElseThrow(() -> new IllegalArgumentException("L'enseignant spécifié n'existe pas."));
            return utilisateurRepository.save(new Utilisateur(username, password, role, enseignant, null));
        } else if (role == Role.ETUDIANT) {
            Etudiant etudiant = etudiantRepository.findByNomPrenomContact(nom, prenom, contact)
                    .orElseThrow(() -> new IllegalArgumentException("L'étudiant spécifié n'existe pas."));
            return utilisateurRepository.save(new Utilisateur(username, password, role, null, etudiant));
        } else {
            throw new IllegalArgumentException("Rôle non pris en charge.");
        }
    }


}
