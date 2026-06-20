package com.example.isdemir;

import com.example.isdemir.service.UserService;
import com.example.isdemir.user.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Root -> login sayfasına yönlendir
    @GetMapping("/")
    public String root() {
        return "redirect:/login-page";
    }

    // Giriş sayfasını render eder
    @GetMapping("/login-page")
    public String loginPage() {
        return "login-page"; // templates/login-page.html
    }

    // Hızlı sağlık kontrolü: http://localhost:8080/healthz
    @ResponseBody
    @GetMapping("/healthz")
    public String healthz() {
        return "OK";
    }

    // (Opsiyonel) Kayıt sayfası GET
    @GetMapping("/register")
    public String registerForm(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new RegisterForm());
        }
        return "register"; // templates/register.html varsa
    }

    // (Opsiyonel) Kayıt gönderimi POST
    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("form") RegisterForm form,
                                 BindingResult br,
                                 RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "register";
        }
        try {
            userService.register(form);
            ra.addFlashAttribute("ok", "Kayıt tamamlandı. Giriş yapabilirsiniz.");
            return "redirect:/login-page";
        } catch (IllegalArgumentException e) {
            br.reject("mismatch", e.getMessage());
            return "register";
        } catch (IllegalStateException e) {
            br.rejectValue("username", "duplicate", e.getMessage());
            return "register";
        }
    }
}
