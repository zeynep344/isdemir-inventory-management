package com.example.isdemir;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TypeSelectController {

    @GetMapping("/type-select")
    public String showTypeSelectPage() {
        return "type-select"; // ✅ bu html'in adıyla birebir aynı olmalı
    }
}
