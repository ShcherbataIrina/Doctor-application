package com.example.demo.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/menu")
    public String getAllDoctor(Model model, @RequestParam(value = "specialty", required = false) String specialty) {
        if (specialty != null && !specialty.isEmpty()) {
            model.addAttribute("doctors", doctorService.getDoctorsBySpecialty(specialty));
        } else {
            model.addAttribute("doctors", doctorService.getAllDoctors());
        }
        return "menu";

    }

    @PostMapping("/menu")
    public String getDoctorsBySpecialty(@RequestParam("specialty") String specialty, RedirectAttributes redirectAttributes) {
      redirectAttributes.addAttribute("specialty", specialty);
            return "redirect:/menu";
    }

}
