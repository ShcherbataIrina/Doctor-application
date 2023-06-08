package com.example.demo.schedule;

import com.example.demo.client.Client;
import com.example.demo.client.ClientDto;
import com.example.demo.client.ClientRepository;
import com.example.demo.doctor.Doctor;
import com.example.demo.doctor.DoctorDto;
import com.example.demo.doctor.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class SessionController {
    private final SessionsService sessionsService;
    private final DoctorRepository doctorRepository;
    private final ClientRepository clientRepository;

    private final List<String> dates = List.of(
            "2023-06-19", "2023-06-20", "2023-06-21", "2023-06-22", "2023-06-23",
            "2023-06-26", "2023-06-27", "2023-06-28", "2023-06-29", "2023-06-30"
    );

    private final List<String> times = List.of(
            "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00",
            "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30"
    );

    @GetMapping("/schedule/{lastName}")
    public String getAllDoctor(Model model, @PathVariable("lastName") String lastName) {
        Doctor doctor = doctorRepository.findByLastName(lastName);
        DoctorDto doctorDto = DoctorDto.builder().lastName(lastName).build();
        List<SessionsDto> sessions = sessionsService.getAllSessionsByDoctor(doctorDto);
        model.addAttribute("doctor", doctor);
        model.addAttribute("sessions", sessions);
        model.addAttribute("dates", dates);
        model.addAttribute("times", times);

        return "schedule";

    }

    @GetMapping("/schedule/book-session")
    public String showBookSessionForm() {
        return "book-session";
    }

    @GetMapping("/cancel-session")
    public String showCancelSessionForm() {
        return "cancel-session";
    }

    @PostMapping("/schedule/book-session")
    public String bookSession(@RequestParam("email") String email,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("date") LocalDate date,
                              @RequestParam("time") LocalTime time,
                              @RequestParam("password") String password) {
        Client client = clientRepository.findByEmailAndPassword(email, password);
        if (client != null) {
            Doctor doctor = doctorRepository.findByLastName(lastName);

            SessionsDto sessionsDto = SessionsDto.builder()
                    .client(ClientDto.builder().id(client.getId()).email(email).password(password).build())
                    .doctor(DoctorDto.builder().id(doctor.getId()).lastName(lastName).build())
                    .time(time)
                    .date(date)
                    .build();

            sessionsService.saveSession(sessionsDto);
        }
        return "redirect:/menu";
    }

    @PostMapping("/cancel-session")
    public String cancelSession(@RequestParam("email") String email,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("date") LocalDate date,
                                @RequestParam("time") LocalTime time,
                                @RequestParam("password") String password) {

        Client client = clientRepository.findByEmailAndPassword(email, password);
        if (client != null) {
            Doctor doctor = doctorRepository.findByLastName(lastName);
            SessionsDto sessionsDto = SessionsDto.builder()
                    .client(ClientDto.builder().id(client.getId()).email(email).password(password).build())
                    .doctor(DoctorDto.builder().id(doctor.getId()).lastName(lastName).build())
                    .time(time)
                    .date(date)
                    .build();

            sessionsService.deleteSession(sessionsDto);
        }
        return "redirect:/menu";
    }
}
