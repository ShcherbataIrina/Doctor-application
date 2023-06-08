package com.example.demo.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerClient(@RequestParam("lastName") String lastName,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("middleName") String middleName,
                                 @RequestParam("age") int age,
                                 @RequestParam("address") String address,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 Model model) {
        var newClient = ClientDto.builder()
                .lastName(lastName)
                .firstName(firstName)
                .middleName(middleName)
                .age(age)
                .address(address)
                .email(email)
                .password(password)
                .build();

        clientService.saveClient(newClient);
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginClient(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model) {
        ClientDto clientDto = clientService.findClientByEmailAndPassword(email, password);
        if (clientDto != null) {
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }
}
