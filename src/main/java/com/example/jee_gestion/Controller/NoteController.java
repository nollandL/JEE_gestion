package com.example.jee_gestion.Controller;

import com.example.jee_gestion.Model.*;
import com.example.jee_gestion.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UtilisateurService utilisateurService;
    private final EtudiantService etudiantService;
    private final MatiereService matiereService;

    @Autowired
    public NoteController(NoteService noteService, UtilisateurService utilisateurService, EtudiantService etudiantService, MatiereService matiereService) {
        this.noteService = noteService;
        this.utilisateurService = utilisateurService;
        this.etudiantService = etudiantService;
        this.matiereService = matiereService;
    }

    @GetMapping("/ajouterNote")
    public String showAddNoteForm(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Utilisateur utilisateur = utilisateurService.findById(userId);

        if (utilisateur == null) {
            throw new IllegalStateException("Utilisateur introuvable dans la session.");
        }

        if (utilisateur.getRole() == Role.ENSEIGNANT) {
            // Si l'utilisateur est un enseignant, afficher uniquement ses matières
            List<Matiere> matieres = utilisateur.getEnseignant().getMatieres();
            model.addAttribute("matieres", matieres);
        } else if (utilisateur.getRole() == Role.ADMINISTRATEUR) {
            // Si l'utilisateur est un administrateur, afficher toutes les matières
            List<Matiere> matieres = matiereService.getAllMatieres();
            model.addAttribute("matieres", matieres);
        } else {
            throw new IllegalStateException("Rôle utilisateur non pris en charge.");
        }

        // Ajouter les informations nécessaires pour le formulaire
        model.addAttribute("note", new Note());
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());

        return "ajouterNote"; // Vue correspondante
    }

    @PostMapping("/ajouterNote")
    public String saisirNote(
            @RequestParam("etudiantId") Long etudiantId,
            @RequestParam("matiereId") Long matiereId,
            @RequestParam("note") Float note,
            HttpSession session) {
        
        // Récupération de l'utilisateur connecté
        Long userId = (Long) session.getAttribute("userId");
        Utilisateur utilisateur = utilisateurService.findById(userId);
        
        if (utilisateur == null) {
            throw new IllegalStateException("Utilisateur non trouvé.");
        }

        // Vérifiez si l'utilisateur est un enseignant
        if (utilisateur.getRole() == Role.ENSEIGNANT) {
            Enseignant enseignant = utilisateur.getEnseignant();
            if (enseignant == null) {
                throw new IllegalStateException("L'utilisateur est un enseignant mais aucune entité Enseignant n'est associée.");
            }
            // Logique spécifique à l'enseignant (par exemple, vérifier la matière enseignée)
            if (!enseignant.getMatieres().stream().anyMatch(m -> m.getId().equals(matiereId))) {
                throw new IllegalArgumentException("Vous ne pouvez pas noter pour cette matière.");
            }
        } else if (utilisateur.getRole() == Role.ADMINISTRATEUR) {
            // Logique pour les administrateurs
            // Les administrateurs peuvent noter pour n'importe quelle matière
        } else {
            throw new IllegalArgumentException("Vous n'avez pas l'autorisation d'ajouter une note.");
        }

        // Ajout de la note
        noteService.ajouterNotePourEtudiant(etudiantId, matiereId, note);

        return "redirect:/etudiants/detailsEtudiantEnseignant?id=" + etudiantId;
    }

}
