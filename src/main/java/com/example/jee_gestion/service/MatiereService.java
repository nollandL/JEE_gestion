package com.example.jee_gestion.service;

import com.example.jee_gestion.Model.Matiere;
import com.example.jee_gestion.Model.Role;
import com.example.jee_gestion.Model.Utilisateur;
import com.example.jee_gestion.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatiereService {

	@Autowired
	private MatiereRepository matiereRepository;

    public List<Matiere> getMatieresByEnseignant(Long enseignantId) {
        return matiereRepository.findByEnseignantId(enseignantId);
    }

    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }
    
    public List<Matiere> getMatieresByEnseignantId(Long enseignantId) {
        return matiereRepository.findMatieresByEnseignantId(enseignantId);
    }
    
    public List<Matiere> getMatieresByUtilisateur(Utilisateur utilisateur) {
        if (utilisateur.getRole() == Role.ENSEIGNANT && utilisateur.getEnseignant() != null) {
            return utilisateur.getEnseignant().getMatieres();
        } else if (utilisateur.getRole() == Role.ADMINISTRATEUR) {
            return matiereRepository.findAll();
        } else {
            throw new IllegalStateException("Rôle utilisateur non pris en charge.");
        }
    }
    
    
}
