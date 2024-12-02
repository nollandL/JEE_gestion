package com.example.jee_gestion.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jee_gestion.Model.Role;
import com.example.jee_gestion.service.UtilisateurService;

@Controller
@RequestMapping("/compte")
public class CompteController {
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/creation")
    public String afficherFormulaireCreation() {
        return "CreerCompte"; // Nom du JSP
    }

    @PostMapping("/creation")
    public String creerCompte(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("role") Role role,
                              @RequestParam("nom") String nom,
                              @RequestParam("prenom") String prenom,
                              @RequestParam("contact") String contact,
                              Model model) {
        try {
            utilisateurService.creerCompte(username, password, role, nom, prenom, contact);
            model.addAttribute("message", "Compte créé avec succès !");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "CreerCompte"; // Remplacez par la vue correspondante
        }
    }

}
