package com.example.jee_gestion.Controller;

import com.example.jee_gestion.Model.Cours;
import com.example.jee_gestion.Model.Enseignant;
import com.example.jee_gestion.Model.Matiere;
import com.example.jee_gestion.Model.Role;
import com.example.jee_gestion.Model.Utilisateur;
import com.example.jee_gestion.service.CoursService;
import com.example.jee_gestion.service.EnseignantService;
import com.example.jee_gestion.service.MatiereService;
import com.example.jee_gestion.service.UtilisateurService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private UtilisateurService utilisateurService;
    
    @Autowired
    private CoursService coursService;

    @GetMapping("/menu")
    public String afficherMenuAdmin(HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        return "MenuAdmin"; // Vue spécifique pour le menu admin
    }

    @GetMapping("/enseignants")
    public String listerEnseignants(Model model, HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        List<Enseignant> enseignants = enseignantService.getAllEnseignants();
        model.addAttribute("enseignants", enseignants);
        return "ListeEnseignants"; // Assurez-vous que cette vue existe
    }
    
    @GetMapping("/detailsEnseignant")
    public String afficherDetailsEnseignant(@RequestParam("id") Long enseignantId, Model model, HttpSession session) {
        // Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }

        // Récupérer l'enseignant par ID
        Enseignant enseignant = enseignantService.getEnseignantById(enseignantId);
        model.addAttribute("enseignant", enseignant);

        // Récupérer les cours associés à cet enseignant
        List<Cours> coursAssocies = coursService.getCoursParProfesseur(enseignantId);
        model.addAttribute("coursAssocies", coursAssocies);

        return "detailsEnseignantAdmin"; // Vue JSP à créer
    }

    
    @GetMapping("/ajouterEnseignant")
    public String afficherFormulaireAjoutEnseignant(Model model, HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        model.addAttribute("enseignant", new Enseignant());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        return "ajouterEnseignant";
    }

    @PostMapping("/ajouterEnseignant")
    public String ajouterEnseignant(@ModelAttribute Enseignant enseignant,
                                    @RequestParam(value = "matiereIds", required = false) Long[] matiereIds, HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        enseignantService.ajouterEnseignantAvecMatieres(enseignant, matiereIds);
        return "redirect:/admin/enseignants";
    }

    @GetMapping("/cours")
    public String gererCours(Model model, HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        List<Cours> coursSansProf = coursService.getCoursSansProfesseur();
        List<Cours> coursAssocies = coursService.getTousLesCours();
        model.addAttribute("coursSansProf", coursSansProf);
        model.addAttribute("coursAssocies", coursAssocies);
        return "CoursListeAdmin";
    }

    @GetMapping("/ajouterCours")
    public String ajouterCoursForm(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }

        model.addAttribute("role", utilisateur.getRole());

        List<Matiere> matieres = matiereService.getAllMatieres();
        model.addAttribute("matieres", matieres);
        
        return "ajouterCours";
    }


    @PostMapping("/ajouterCours")
    public String ajouterCoursAdmin(@RequestParam("dateCours") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateCours,
                                    @RequestParam("matiereId") Long matiereId, HttpSession session) {
    	// Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        coursService.ajouterCoursSansProfesseur(dateCours, matiereId);
        return "redirect:/admin/cours";
    }
    
    @GetMapping("/modifierDateCours")
    public String afficherFormulaireModifierDate(@RequestParam("coursId") Long coursId, Model model, HttpSession session) {
        // Récupérer l'utilisateur connecté depuis la session
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithSpecialites(userId);

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }

        // Récupérer le cours à modifier
        Cours cours = coursService.getCoursById(coursId);
        model.addAttribute("cours", cours);

        return "modifierDateCoursAdmin";
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

        if (utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        // Mettre à jour la date du cours
        coursService.modifierDateCours(coursId, nouvelleDate);

        return "redirect:/admin/cours";
    }

}
