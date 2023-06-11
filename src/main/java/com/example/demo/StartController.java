package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/index")
    public String showIndex() {
        return "index";
    }
}
