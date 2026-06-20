package com.example.isdemir;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login-page";
        }
        String username = auth.getName();
        String roles = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        model.addAttribute("username", username);
        model.addAttribute("roles", roles);
        return "dashboard"; // templates/dashboard.html
    }
}
