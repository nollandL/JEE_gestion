package com.example.jee_gestion.Controller;

import com.example.jee_gestion.Model.Cours;
import com.example.jee_gestion.Model.Enseignant;
import com.example.jee_gestion.Model.Etudiant;
import com.example.jee_gestion.Model.Inscription;
import com.example.jee_gestion.Model.Matiere;
import com.example.jee_gestion.Model.Note;
import com.example.jee_gestion.Model.Role;
import com.example.jee_gestion.Model.Utilisateur;
import com.example.jee_gestion.service.CoursService;
import com.example.jee_gestion.service.EnseignantService;
import com.example.jee_gestion.service.EtudiantService;
import com.example.jee_gestion.service.MatiereService;
import com.example.jee_gestion.service.NoteService;
import com.example.jee_gestion.service.UtilisateurService;
import com.example.jee_gestion.repository.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;
    private final NoteService noteService;
    private final MatiereService matiereService;
    private final UtilisateurService utilisateurService;
    private final CoursService coursService;
    private final EnseignantService enseignantService;

    @Autowired
    public EtudiantController(EtudiantService etudiantService, NoteService noteService, MatiereService matiereService, UtilisateurService utilisateurService, CoursService coursService, EnseignantService enseignantService) {
        this.etudiantService = etudiantService;
        this.noteService = noteService;
        this.matiereService = matiereService;
        this.utilisateurService = utilisateurService;
        this.coursService = coursService;
        this.enseignantService = enseignantService;
    }
    
    @GetMapping("/menu")
    public String afficherMenuEtudiant(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ETUDIANT) {
            return "redirect:/";
        }

        return "MenuEtudiant";
    }


    // Affichage de la liste des étudiants avec possibilité de recherche   
    @GetMapping
    public String getAllEtudiants(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "matiereId", required = false) Long matiereId,
                                  Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }

        List<Etudiant> etudiants;

        if (matiereId != null && (keyword != null && !keyword.isEmpty())) {
            // Recherche par mot-clé et matière
            List<Etudiant> etudiantsParMatiere = etudiantService.findEtudiantsByCoursOrNotesMatiere(matiereId);
            etudiants = etudiantsParMatiere.stream()
                        .filter(etudiant -> etudiant.getNom().contains(keyword) || etudiant.getPrenom().contains(keyword))
                        .toList();
        } else if (matiereId != null) {
            // Filtrer uniquement par matière
            etudiants = etudiantService.findEtudiantsByCoursOrNotesMatiere(matiereId);
        } else if (keyword != null && !keyword.isEmpty()) {
            // Rechercher uniquement par mot-clé
            etudiants = etudiantService.searchEtudiants(keyword);
        } else {
            // Récupérer tous les étudiants
            etudiants = etudiantService.getAllEtudiants();
        }

        List<Matiere> matieres = matiereService.getAllMatieres();
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("keyword", keyword);
        model.addAttribute("matiereId", matiereId);
        model.addAttribute("matieres", matieres);

        return "EtudiantsListe";
    }




    
    @GetMapping("/cours")
    public String afficherCours(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithDetails(userId);

        if (utilisateur.getRole() != Role.ETUDIANT) {
            return "redirect:/";
        }

        // Récupérer tous les cours
        List<Cours> tousLesCours = coursService.getTousLesCours();

        // Récupérer les cours auxquels l'étudiant est inscrit
        List<Cours> coursInscrits = etudiantService.getCoursInscrits(utilisateur.getEtudiant().getId());

        // Filtrer pour n'afficher que les cours non inscrits
        List<Cours> coursNonInscrits = tousLesCours.stream()
            .filter(cours -> !coursInscrits.contains(cours))
            .toList();

        model.addAttribute("coursNonInscrits", coursNonInscrits);
        model.addAttribute("coursInscrits", coursInscrits);

        return "CoursDisponibles";
    }

    @GetMapping("/modifierContact")
    public String afficherFormulaireModifierContact(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithDetails(userId);

        if (utilisateur.getRole() != Role.ETUDIANT) {
            return "redirect:/";
        }

        Etudiant etudiant = utilisateur.getEtudiant();
        model.addAttribute("etudiant", etudiant);

        return "modifierContactEtudiant"; // Assurez-vous que "modifierContact.jsp" existe
    }

    
    @PostMapping("/sauvegarderContact")
    public String sauvegarderContact(@RequestParam("contact") String contact, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithDetails(userId);

        if (utilisateur.getRole() != Role.ETUDIANT) {
            return "redirect:/";
        }

        Etudiant etudiant = utilisateur.getEtudiant();
        etudiant.setContact(contact);
        etudiantService.updateEtudiant(etudiant);

        return "redirect:/etudiants/details";
    }



    
    @PostMapping("/inscrire")
    public String inscrireCours(@RequestParam("coursId") Long coursId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithDetails(userId);

        if (utilisateur.getRole() != Role.ETUDIANT) {
            return "redirect:/";
        }

        etudiantService.inscrireCours(utilisateur.getEtudiant().getId(), coursId);
        return "redirect:/etudiants/cours";
    }
    
    
    @PostMapping("/desinscrire")
    public String desinscrireCours(@RequestParam("coursId") Long coursId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findByIdWithDetails(userId);

        if (utilisateur.getRole() != Role.ETUDIANT) {
            return "redirect:/";
        }

        etudiantService.desinscrireCours(utilisateur.getEtudiant().getId(), coursId);

        return "redirect:/etudiants/cours";
    }





    @GetMapping("/ajouterEtudiant")
    public String showAddEtudiantForm(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        model.addAttribute("etudiant", new Etudiant());
        return "ajouterEtudiant";
    }

    @PostMapping("/ajouterEtudiant")
    public String addEtudiant(@Validated @ModelAttribute("etudiant") Etudiant etudiant, BindingResult result, Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
    	
    	if (result.hasErrors()) {
            return "ajouterEtudiant";
        }
        etudiantService.saveEtudiant(etudiant);
        return "redirect:/etudiants";
    }

    @GetMapping("/modifierEtudiant")
    public String showUpdateEtudiantForm(@RequestParam("id") Long id, Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
    	
    	Etudiant etudiant = etudiantService.getEtudiantById(id);
        if (etudiant != null) {
            model.addAttribute("etudiant", etudiant);
            return "ajouterEtudiant";
        }
        return "redirect:/etudiants";
    }

    @PostMapping("/modifierEtudiant")
    public String updateEtudiant(@Validated @ModelAttribute("etudiant") Etudiant etudiant, BindingResult result, Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        if (result.hasErrors()) {
            return "ajouterEtudiant";
        }
        etudiantService.updateEtudiant(etudiant);
        return "redirect:/etudiants";
    }
    
    @GetMapping("/details")
    public String getDetailsEtudiant(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        // Récupérer l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.findByIdWithDetails(userId);

        // Vérifiez que l'utilisateur est bien un étudiant
        if (utilisateur.getRole() != Role.ETUDIANT) {
            return "redirect:/";
        }

        Etudiant etudiant = utilisateur.getEtudiant();
        model.addAttribute("etudiant", etudiant);

        // Récupérer les données des notes et moyennes
        Map<String, Object> notesData = noteService.getNotesAndAverages(etudiant.getId());
        model.addAttribute("notesData", notesData);

        // Calculer le nombre maximum de notes pour une présentation cohérente
        Integer maxNoteCount = noteService.getMaxNoteCountForEtudiant(etudiant.getId());
        model.addAttribute("maxNoteCount", maxNoteCount);

        // Charger les inscriptions (cours auxquels l'étudiant est inscrit)
        List<Inscription> inscriptions = etudiant.getInscriptions();
        model.addAttribute("inscriptions", inscriptions);

        return "detailsEtudiant";
    }



    @GetMapping("/detailsEtudiantEnseignant")
    public String getEtudiantDetails(@RequestParam("id") Long id, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        Etudiant etudiant = etudiantService.getEtudiantById(id);
        model.addAttribute("etudiant", etudiant);

        Map<String, Object> notesData = noteService.getNotesAndAverages(id);
        model.addAttribute("notesData", notesData);

        Integer maxNoteCount = noteService.getMaxNoteCountForEtudiant(id);
        model.addAttribute("maxNoteCount", maxNoteCount);

        List<Inscription> inscriptions = etudiant.getInscriptions();
        model.addAttribute("inscriptions", inscriptions);

        return "detailsEtudiantEnseignant";
    }

    @GetMapping("/supprimerNote")
    public String deleteLastNote(@RequestParam("etudiantId") Long etudiantId, @RequestParam("matiereId") Long matiereId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        noteService.deleteLastNoteForEtudiantAndMatiere(etudiantId, matiereId);
        return "redirect:/etudiants/detailsEtudiantEnseignant?id=" + etudiantId;
    }

    @GetMapping("/supprimerEtudiant")
    public String deleteEtudiant(@RequestParam("id") Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur.getRole() != Role.ENSEIGNANT && utilisateur.getRole() != Role.ADMINISTRATEUR) {
            return "redirect:/";
        }
        
        etudiantService.deleteEtudiant(id);
        return "redirect:/etudiants";
    }

    @GetMapping("/genererReleve")
    public ResponseEntity<String> genererReleveDeNotes(@RequestParam("id") Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }

        Etudiant etudiant = etudiantService.getEtudiantById(id);
        if (etudiant == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> notesData = noteService.getNotesAndAverages(id);

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><title>Relevé de Notes</title></head><body>");
        html.append("<h1>Relevé de Notes</h1>");
        html.append("<p><strong>Nom : </strong>").append(etudiant.getNom()).append("</p>");
        html.append("<p><strong>Prénom : </strong>").append(etudiant.getPrenom()).append("</p>");
        
        html.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
        html.append("<tr><th>Matière</th>");
        Integer maxNoteCount = noteService.getMaxNoteCountForEtudiant(id);
        for (int i = 1; i <= maxNoteCount; i++) {
            html.append("<th>Note ").append(i).append("</th>");
        }
        html.append("<th>Moyenne de l'Étudiant</th><th>Moyenne Générale</th></tr>");
        
        for (Map.Entry<String, Object> entry : notesData.entrySet()) {
            Map<String, Object> matiereData = (Map<String, Object>) entry.getValue();
            html.append("<tr>");
            html.append("<td>").append(matiereData.get("matiereNom")).append("</td>");
            
            List<Note> notes = (List<Note>) matiereData.get("notes");
            for (Note note : notes) {
                html.append("<td>").append(note.getNote()).append("</td>");
            }
            
            for (int i = notes.size(); i < maxNoteCount; i++) {
                html.append("<td></td>");
            }

            html.append("<td>").append(matiereData.get("moyenneEtudiant")).append("</td>");
            html.append("<td>").append(matiereData.get("moyenneGenerale")).append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        
        html.append("</body></html>");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=releve_notes.html")
                .contentType(MediaType.TEXT_HTML)
                .body(html.toString());
    }
}
