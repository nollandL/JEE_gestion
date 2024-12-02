package com.example.jee_gestion.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jee_gestion.Model.Cours;
import com.example.jee_gestion.Model.Matiere;
import com.example.jee_gestion.Model.Role;
import com.example.jee_gestion.Model.Utilisateur;
import com.example.jee_gestion.service.CoursService;
import com.example.jee_gestion.service.MatiereService;
import com.example.jee_gestion.service.UtilisateurService;
import com.example.jee_gestion.service.EnseignantService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/enseignant")
public class EnseignantController {
	@Autowired
    private CoursService coursService;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private UtilisateurService utilisateurService;
    
    @Autowired
    private EnseignantService enseignantService;

    // Page du menu
    @GetMapping("/menu")
    public String menu(Model model, HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }
    	
        // Redirection vers la page de menu
        return "MenuEnseignant";
    }
    
    @GetMapping("/details")
    public String afficherDetailsEnseignant(HttpSession session, Model model) {
        // Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }

        // Récupérer l'enseignant associé
        model.addAttribute("enseignant", utilisateur.getEnseignant());

        // Charger les cours associés à cet enseignant
        List<Cours> coursAssocies = coursService.getCoursParProfesseur(utilisateur.getEnseignant().getId());
        model.addAttribute("coursAssocies", coursAssocies);

        return "detailsEnseignant";
    }

    @GetMapping("/modifierContact")
    public String afficherFormulaireModifierContact(HttpSession session, Model model) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }

        // Ajouter l'enseignant au modèle
        model.addAttribute("enseignant", utilisateur.getEnseignant());

        return "modifierContact";
    }

    @PostMapping("/sauvegarderContact")
    public String sauvegarderContact(@RequestParam("contact") String contact, HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }

        // Mettre à jour le contact
        enseignantService.modifierContact(utilisateur.getEnseignant().getId(), contact);

        return "redirect:/enseignant/details";
    }



    // Liste des cours
    @GetMapping("/cours")
    public String listeCours(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }

        // Charger les cours associés à cet enseignant
        List<Cours> coursAssocies = coursService.getCoursParProfesseur(utilisateur.getEnseignant().getId());
        model.addAttribute("coursAssocies", coursAssocies);

        // Charger les cours sans professeur liés aux matières de cet enseignant
        List<Cours> coursSansProf = coursService.getCoursSansProfesseurByEnseignantMatiere(utilisateur.getEnseignant().getId());
        model.addAttribute("coursSansProf", coursSansProf);

        return "CoursListe";
    }

        
    @PostMapping("/affecterCours")
    public String affecterCours(@RequestParam("coursId") Long coursId, HttpSession session) {
    	
    	// Récupérer l'utilisateur connecté depuis la session
    	Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }

        // Affecter le professeur au cours
        coursService.affecterProfesseurAuCours(coursId, utilisateur.getEnseignant().getId());

        // Rediriger vers la page des cours
        return "redirect:/enseignant/cours";
    }

    @GetMapping("/ajouterCours")
    public String ajouterCoursForm(Model model, HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }

        model.addAttribute("role", utilisateur.getRole());

        List<Matiere> matieres = matiereService.getMatieresByEnseignant(utilisateur.getEnseignant().getId());
        model.addAttribute("matieres", matieres);
        model.addAttribute("enseignantId", utilisateur.getEnseignant().getId());

        return "ajouterCours";
    }


    @PostMapping("/ajouterCours")
    public String ajouterCoursEnseignant(@RequestParam("dateCours") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateCours,
                                         @RequestParam("matiereId") Long matiereId,
                                         HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }
        
        coursService.ajouterCours(dateCours, matiereId, utilisateur.getEnseignant().getId());
        return "redirect:/enseignant/cours";
    }

    
    @PostMapping("/supprimerCours")
    public String supprimerCours(@RequestParam("coursId") Long coursId, HttpSession session) {
        // Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT) {
            return "redirect:/";
        }

        // Supprimer l'affectation du professeur au cours
        coursService.supprimerAffectationProfesseurAuCours(coursId);

        // Rediriger vers la page des cours
        return "redirect:/enseignant/cours";
    }
    
    @PostMapping("/supprimerCoursComplet")
    public String supprimerCoursComplet(@RequestParam("coursId") Long coursId, HttpSession session) {
        // Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }

        // Supprimer complètement le cours
        coursService.supprimerCoursComplet(coursId);

        // Rediriger vers la page des cours
        return "redirect:/enseignant/cours";
    }
    
    @GetMapping("/modifierDateCours")
    public String afficherFormulaireModifierDate(@RequestParam("coursId") Long coursId, Model model, HttpSession session) {
        // Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }

        // Récupérer le cours à modifier
        Cours cours = coursService.getCoursById(coursId);
        model.addAttribute("cours", cours);

        return "modifierDateCours";
    }

    @PostMapping("/sauvegarderDateCours")
    public String sauvegarderNouvelleDate(@RequestParam("coursId") Long coursId,
                                          @RequestParam("nouvelleDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime nouvelleDate,
                                          HttpSession session) {
        // Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        // Mettre à jour la date du cours
        coursService.modifierDateCours(coursId, nouvelleDate);

        return "redirect:/enseignant/cours";
    }
}