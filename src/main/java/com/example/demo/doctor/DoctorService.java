package com.example.demo.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<DoctorDto> getDoctorsBySpecialty(String specialty) {
        List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
        return doctors.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }



    private DoctorDto mapToDto(Doctor doctor) {
        return DoctorDto.builder()
                .lastName(doctor.getLastName())
                .firstName(doctor.getFirstName())
                .middleName(doctor.getMiddleName())
                .specialty(doctor.getSpecialty())
                .build();
    }

}
