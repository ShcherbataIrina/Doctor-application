package com.example.demo.schedule;

import com.example.demo.client.Client;
import com.example.demo.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SessionsRepository extends JpaRepository<Sessions, String> {
    List<Sessions> findByDoctor(Doctor doctor);

    void deleteByDoctorAndClient(Doctor doctor, Client client);
    Sessions findByDoctorAndClientAndDateAndTime(Doctor doctor, Client client, LocalDate date, LocalTime time);
}
