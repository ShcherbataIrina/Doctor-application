package com.example.demo.schedule;

import com.example.demo.client.Client;
import com.example.demo.client.ClientDto;
import com.example.demo.client.ClientRepository;
import com.example.demo.doctor.Doctor;
import com.example.demo.doctor.DoctorDto;
import com.example.demo.doctor.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionsService {

    private final SessionsRepository sessionsRepository;
    private final DoctorRepository doctorRepository;
    private final ClientRepository clientRepository;

    public void saveSession(SessionsDto sessionsDto) {
        Doctor doctor = doctorRepository.findByLastName(sessionsDto.getDoctor().getLastName());
        Client client = clientRepository.findByEmailAndPassword(sessionsDto.getClient().getEmail(), sessionsDto.getClient().getPassword());

        Sessions session = mapToEntity(sessionsDto, doctor, client);
        sessionsRepository.save(session);
    }

    public List<SessionsDto> getAllSessionsByDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findByLastName(doctorDto.getLastName());
        List<Sessions> sessionsList = sessionsRepository.findByDoctor(doctor);
        return sessionsList.stream().map(this::mapToDto).toList();
    }

    public void deleteSession(SessionsDto sessionsDto) {
        Doctor doctor = doctorRepository.findByLastName(sessionsDto.getDoctor().getLastName());
        Client client = clientRepository.findByEmailAndPassword(sessionsDto.getClient().getEmail(), sessionsDto.getClient().getPassword());

        Sessions session = sessionsRepository.findByDoctorAndClientAndDateAndTime(doctor, client, sessionsDto.getDate(), sessionsDto.getTime());
        if (session != null) {
            sessionsRepository.delete(session);
        }
    }

    private Sessions mapToEntity(SessionsDto sessionsDto, Doctor doctor, Client client) {
        return Sessions.builder()
                .id(sessionsDto.getId())
                .doctor(doctor)
                .client(client)
                .date(sessionsDto.getDate())
                .time(sessionsDto.getTime())
                .build();
    }

    private SessionsDto mapToDto(Sessions session) {
        return SessionsDto.builder()
                .id(session.getId())
                .doctor(mapToDoctorDto(session.getDoctor()))
                .date(session.getDate())
                .time(session.getTime())
                .client(mapToClientDto(session.getClient()))
                .build();
    }

    private DoctorDto mapToDoctorDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .lastName(doctor.getLastName())
                .firstName(doctor.getFirstName())
                .middleName(doctor.getMiddleName())
                .specialty(doctor.getSpecialty())
                .build();
    }

    private ClientDto mapToClientDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .lastName(client.getLastName())
                .firstName(client.getFirstName())
                .middleName(client.getMiddleName())
                .age(client.getAge())
                .address(client.getAddress())
                .email(client.getEmail())
                .password(client.getPassword())
                .build();
    }

}
