package com.example.jee_gestion.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jee_gestion.repository.EtudiantRepository;
import com.example.jee_gestion.repository.InscriptionRepository;
import com.example.jee_gestion.repository.NoteRepository;
import com.example.jee_gestion.repository.UtilisateurRepository;
import com.example.jee_gestion.repository.CoursRepository;

import jakarta.transaction.Transactional;

import com.example.jee_gestion.Model.Cours;
import com.example.jee_gestion.Model.Etudiant;
import com.example.jee_gestion.Model.Inscription;

@Service
public class EtudiantService {

	private final EtudiantRepository etudiantRepository;
	private final CoursRepository coursRepository;
	private final InscriptionRepository inscriptionRepository;
	private final NoteRepository noteRepository;
	private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public EtudiantService(EtudiantRepository etudiantRepository, CoursRepository coursRepository, InscriptionRepository inscriptionRepository, NoteRepository noteRepository, UtilisateurRepository utilisateurRepository) {
        this.etudiantRepository = etudiantRepository;
        this.coursRepository = coursRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.noteRepository = noteRepository;
        this.utilisateurRepository = utilisateurRepository;
    }
    
    @Transactional
    public void inscrireCours(Long etudiantId, Long coursId) {
        // Récupérer l'étudiant depuis la base de données
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        // Récupérer le cours depuis la base de données
        Cours cours = coursRepository.findById(coursId)
            .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // Créer une nouvelle inscription
        Inscription inscription = new Inscription(etudiant, cours);

        // Sauvegarder l'inscription
        inscriptionRepository.save(inscription);
    }
    
    @Transactional
    public List<Cours> getCoursInscrits(Long etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        return etudiant.getInscriptions().stream()
            .map(Inscription::getCours)
            .toList();
    }

    
    @Transactional
    public void desinscrireCours(Long etudiantId, Long coursId) {
        Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
        if (etudiantOpt.isEmpty()) {
            throw new IllegalArgumentException("Étudiant non trouvé");
        }

        Etudiant etudiant = etudiantOpt.get();

        // Trouver l'inscription correspondante
        Inscription inscription = etudiant.getInscriptions().stream()
            .filter(i -> i.getCours().getId().equals(coursId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("L'étudiant n'est pas inscrit à ce cours"));

        // Supprimer l'inscription
        etudiant.getInscriptions().remove(inscription);

        // Persist les changements
        etudiantRepository.save(etudiant);
    }


    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public List<Etudiant> findEtudiantsByCoursOrNotesMatiere(Long matiereId) {
        // Récupérer les étudiants via les cours
        List<Etudiant> etudiantsParCours = etudiantRepository.findEtudiantsByCoursMatiereId(matiereId);

        // Récupérer les étudiants via les notes
        List<Etudiant> etudiantsParNotes = etudiantRepository.findByMatiere(matiereId);

        // Fusionner les deux listes et enlever les doublons
        return Stream.concat(etudiantsParCours.stream(), etudiantsParNotes.stream())
                     .distinct()
                     .toList();
    }

    public List<Etudiant> searchEtudiants(String keyword) {
        return etudiantRepository.searchByNomOrPrenom(keyword);
    }
    
    public void saveEtudiant(Etudiant etudiant) {
        etudiantRepository.save(etudiant);
    }

    @Transactional
    public void deleteEtudiant(Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        // Supprimer les utilisateurs associés
        utilisateurRepository.deleteAllByEtudiantId(id);

        // Supprimer explicitement les inscriptions associées
        inscriptionRepository.deleteAllByEtudiantId(id);

        // Supprimer explicitement les résultats associés
        noteRepository.deleteAllByEtudiantId(id);

        // Supprimer l'étudiant
        etudiantRepository.delete(etudiant);
    }


    
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id).orElse(null);
    }

    public void updateEtudiant(Etudiant etudiant) {
        Etudiant existingEtudiant = etudiantRepository.findById(etudiant.getId())
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        // Mettre à jour uniquement les champs nécessaires
        existingEtudiant.setNom(etudiant.getNom());
        existingEtudiant.setPrenom(etudiant.getPrenom());
        existingEtudiant.setDateNaissance(etudiant.getDateNaissance());
        existingEtudiant.setContact(etudiant.getContact());
        
        // Ne pas écraser les collections existantes
        // Les inscriptions et résultats restent inchangés

        etudiantRepository.save(existingEtudiant);
    }

}
