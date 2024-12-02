package com.example.jee_gestion.service;

import java.util.Date;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jee_gestion.repository.CoursRepository;
import com.example.jee_gestion.repository.MatiereRepository;
import com.example.jee_gestion.repository.EnseignantRepository;
import com.example.jee_gestion.Model.Cours;
import com.example.jee_gestion.Model.Enseignant;

@Service
public class CoursService {
	@Autowired
    private CoursRepository coursRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Cours> getCoursSansProfesseurByEnseignantMatiere(Long enseignantId) {
        return coursRepository.findCoursSansProfesseurByEnseignantMatiere(enseignantId);
    }
    
    public void affecterProfesseurAuCours(Long coursId, Long enseignantId) {
        // Récupérer le cours par son ID
        Cours cours = coursRepository.findById(coursId).orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // Récupérer l'enseignant par son ID
        Enseignant enseignant = enseignantRepository.findById(enseignantId).orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        // Affecter l'enseignant au cours
        cours.setEnseignant(enseignant);

        // Sauvegarder les modifications
        coursRepository.save(cours);
    }
    
    
    
    public void ajouterCours(LocalDateTime dateCours, Long matiereId, Long enseignantId) {
        Cours cours = new Cours();

        // Conversion de LocalDateTime en java.util.Date
        Date date = java.sql.Timestamp.valueOf(dateCours);
        cours.setDateCours(date);

        // Récupération des relations
        cours.setMatiere(matiereRepository.findById(matiereId).orElseThrow());
        cours.setEnseignant(enseignantRepository.findById(enseignantId).orElseThrow());

        // Sauvegarde en base
        coursRepository.save(cours);
    }
    
    public List<Cours> getTousLesCours() {
        return coursRepository.findAll();
    }
    
    // Cours sans professeur
    public List<Cours> getCoursSansProfesseur() {
        return coursRepository.findByEnseignantIsNull();
    }

    // Cours attribués à un professeur spécifique
    public List<Cours> getCoursParProfesseur(Long enseignantId) {
        return coursRepository.findByEnseignantId(enseignantId);
    }
    
    public void supprimerAffectationProfesseurAuCours(Long coursId) {
        // Récupérer le cours par son ID
        Cours cours = coursRepository.findById(coursId).orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // Supprimer l'affectation de l'enseignant
        cours.setEnseignant(null);

        // Sauvegarder les modifications
        coursRepository.save(cours);
    }
    
    public void desassocierCoursParEnseignant(Long enseignantId) {
        List<Cours> coursAssocies = coursRepository.findByEnseignantId(enseignantId);
        for (Cours cours : coursAssocies) {
            cours.setEnseignant(null); // Désassocier l'enseignant
            coursRepository.save(cours); // Sauvegarder les modifications
        }
    }
    public void supprimerEnseignant(Long enseignantId) {
        // Désassocier tous les cours de cet enseignant
        desassocierCoursParEnseignant(enseignantId);

        // Supprimer l'enseignant
        enseignantRepository.deleteById(enseignantId);
    }

    
    public void supprimerCoursComplet(Long coursId) {
        // Vérifiez l'existence du cours
        if (!coursRepository.existsById(coursId)) {
            throw new RuntimeException("Cours non trouvé");
        }

        // Supprimez directement le cours
        coursRepository.deleteById(coursId);
    }
    
    public Cours getCoursById(Long coursId) {
        return coursRepository.findById(coursId).orElseThrow(() -> new RuntimeException("Cours non trouvé"));
    }

    public void modifierDateCours(Long coursId, LocalDateTime nouvelleDate) {
        // Récupérer le cours
        Cours cours = getCoursById(coursId);

        // Modifier la date
        cours.setDateCours(java.sql.Timestamp.valueOf(nouvelleDate));

        // Sauvegarder le cours
        coursRepository.save(cours);
    }
    
    
    public void ajouterCoursSansProfesseur(LocalDateTime dateCours, Long matiereId) {
        Cours cours = new Cours();

        // Conversion de LocalDateTime en java.util.Date
        Date date = java.sql.Timestamp.valueOf(dateCours);
        cours.setDateCours(date);

        // Récupération de la matière
        cours.setMatiere(matiereRepository.findById(matiereId)
                .orElseThrow(() -> new RuntimeException("Matière non trouvée")));

        // Pas d'enseignant assigné
        cours.setEnseignant(null);

        // Sauvegarde
        coursRepository.save(cours);
    }
}
