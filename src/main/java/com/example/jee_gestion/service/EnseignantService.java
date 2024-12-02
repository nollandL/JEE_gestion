package com.example.jee_gestion.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jee_gestion.Model.Cours;
import com.example.jee_gestion.Model.Enseignant;
import com.example.jee_gestion.Model.Matiere;
import com.example.jee_gestion.Model.Utilisateur;
import com.example.jee_gestion.repository.CoursRepository;
import com.example.jee_gestion.repository.EnseignantRepository;
import com.example.jee_gestion.repository.MatiereRepository;
import com.example.jee_gestion.repository.UtilisateurRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private MatiereRepository matiereRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private CoursRepository coursRepository;

    
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    public void ajouterEnseignantAvecMatieres(Enseignant enseignant, Long[] matiereIds) {
        if (matiereIds != null) {
            List<Matiere> matieres = matiereRepository.findAllById(Arrays.asList(matiereIds));
            enseignant.setMatieres(matieres);
        }
        enseignantRepository.save(enseignant);
    }
    
    public Enseignant getEnseignantById(Long enseignantId) {
        return enseignantRepository.findById(enseignantId)
            .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));
    }


    
    public void modifierContact(Long enseignantId, String nouveauContact) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
            .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));
        enseignant.setContact(nouveauContact);
        enseignantRepository.save(enseignant);
    }

    public List<Enseignant> findAll() {
        return enseignantRepository.findAll();
    }
}
