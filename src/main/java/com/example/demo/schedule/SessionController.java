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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class SessionController {
    private final SessionsService sessionsService;
    private final DoctorRepository doctorRepository;
    private final ClientRepository clientRepository;
    LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
    private final List<LocalDate> dates = List.of(
            monday,
            monday.plus(1, ChronoUnit.DAYS),
            monday.plus(2, ChronoUnit.DAYS),
            monday.plus(3, ChronoUnit.DAYS),
            monday.plus(4, ChronoUnit.DAYS),
            monday.plus(5, ChronoUnit.DAYS),
            monday.plus(6, ChronoUnit.DAYS),
            monday.plus(7, ChronoUnit.DAYS),
            monday.plus(8, ChronoUnit.DAYS),
            monday.plus(9, ChronoUnit.DAYS),
            monday.plus(10, ChronoUnit.DAYS),
            monday.plus(11, ChronoUnit.DAYS),
            monday.plus(12, ChronoUnit.DAYS),
            monday.plus(13, ChronoUnit.DAYS)
    );

    private final List<LocalTime> SLOTS = List.of(
            LocalTime.parse("09:00"), LocalTime.parse("09:30"), LocalTime.parse("10:00"), LocalTime.parse("10:30"),
            LocalTime.parse("11:00"), LocalTime.parse("11:30"), LocalTime.parse("12:00"), LocalTime.parse("12:30"),
            LocalTime.parse("13:00"), LocalTime.parse("13:30"), LocalTime.parse("14:00"), LocalTime.parse("14:30"),
            LocalTime.parse("15:00"), LocalTime.parse("15:30")
    );


    @GetMapping("/schedule/{lastName}")
    public String getAllDoctor(Model model, @PathVariable("lastName") String lastName) {
        Doctor doctor = doctorRepository.findByLastName(lastName);
        DoctorDto doctorDto = DoctorDto.builder().lastName(lastName).build();
        List<SessionsDto> sessions = sessionsService.getAllSessionsByDoctor(doctorDto);
      var monday = LocalDate.now().with(DayOfWeek.MONDAY);
//      List<LocalDate> week = null;
//      week.add(monday);
//        for (int i = 1; i < 14; i++) {
//            var day = monday.plus(i, ChronoUnit.DAYS);
//            week.add(day);
//        }

      Object [][]schedule = new Object [SLOTS.size()][dates.size()];
        for (int i = 0; i < SLOTS.size(); i++) {
            for (int j = 0; j < dates.size(); j++) {
                boolean isBooked = false;
                for (SessionsDto session : sessions) {
                    if (session.getDate().equals(dates.get(j)) && session.getTime().equals(SLOTS.get(i))) {
                        isBooked = true;
                        break;
                    }
                }
                schedule[i][j] = Map.of(

                        "slot", SLOTS.get(i),
                        "booked", isBooked//i%2 == 0
                );
            }
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("schedule", schedule);
        model.addAttribute("dates", dates);

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
