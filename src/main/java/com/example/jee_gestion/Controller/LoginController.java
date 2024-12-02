	package com.example.jee_gestion.Controller;
	
	import com.example.jee_gestion.Model.Utilisateur;
	import com.example.jee_gestion.Model.Role;
	import com.example.jee_gestion.repository.UtilisateurRepository;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.servlet.ModelAndView;
	
	import jakarta.servlet.http.HttpSession;
	
	@Controller
	@RequestMapping("/login")
	public class LoginController {
	
	    private final UtilisateurRepository utilisateurRepository;
	
	    @Autowired
	    public LoginController(UtilisateurRepository utilisateurRepository) {
	        this.utilisateurRepository = utilisateurRepository;
	    }
	
	    @GetMapping
	    public String showLoginPage() {
	        return "Login"; // Retourne la vue de connexion
	    }
	
	    @PostMapping
	    public ModelAndView processLogin(
	            @RequestParam("username") String username,
	            @RequestParam("password") String password,
	            HttpSession session,
	            Model model) {
	
	        Utilisateur utilisateur = utilisateurRepository.findByUsernameAndPassword(username, password);
	
	        if (utilisateur != null) {
	            session.setAttribute("userId", utilisateur.getId());
	            session.setAttribute("username", utilisateur.getUsername());
	            session.setAttribute("role", utilisateur.getRole());
	            session.setAttribute("userRole", utilisateur.getRole().toString());

	            if (utilisateur.getRole() == Role.ENSEIGNANT) {
	                session.setAttribute("specialites", utilisateur.getEnseignant().getMatieres());
	            } else {
	                session.setAttribute("specialites", null);
	            }
	
	            switch (utilisateur.getRole()) {
	                case ADMINISTRATEUR:
	                    return new ModelAndView("redirect:/admin/menu");
	                case ENSEIGNANT:
	                    return new ModelAndView("redirect:/enseignant/menu");
	                case ETUDIANT:
	                    return new ModelAndView("redirect:/etudiants/menu");
	                default:
	                    return new ModelAndView("Login");
	            }
	        } else {
	            model.addAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect");
	            return new ModelAndView("Login");
	        }
	    }
	    
	    @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate(); // Invalider la session
	        return "redirect:/login"; // Rediriger vers la page de connexion
	    }

	}
