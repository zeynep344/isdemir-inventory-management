package com.example.isdemir;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // templates/403.html dosyasını döndürecek
    }
}
