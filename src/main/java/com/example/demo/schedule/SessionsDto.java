package com.example.demo.schedule;

import com.example.demo.client.ClientDto;
import com.example.demo.doctor.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionsDto {
    private int id;
    private DoctorDto doctor;
    private LocalDate date;
    private LocalTime time;
    private ClientDto client;

}
