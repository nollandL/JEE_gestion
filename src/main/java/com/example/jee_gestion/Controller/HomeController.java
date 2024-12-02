package com.example.jee_gestion.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showLoginPage() {
        return "Login"; // Assurez-vous que "Login.jsp" est placé correctement
    }
}