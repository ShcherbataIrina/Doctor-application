package com.example.demo.schedule;

import com.example.demo.client.Client;
import com.example.demo.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "sessions")
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
