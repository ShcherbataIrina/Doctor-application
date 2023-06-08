package com.example.demo.client;

import com.example.demo.doctor.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public void saveClient(ClientDto clientDto) {
        Client client = mapToEntity(clientDto);
        clientRepository.save(client);
    }

    public ClientDto findClientByEmailAndPassword(String email, String password) {
        Client client = clientRepository.findByEmailAndPassword(email, password);
        return mapToDto(client);
    }

    private Client mapToEntity(ClientDto clientDto) {
        return Client.builder()
                .id(clientDto.getId())
                .lastName(clientDto.getLastName())
                .firstName(clientDto.getFirstName())
                .middleName(clientDto.getMiddleName())
                .age(clientDto.getAge())
                .address(clientDto.getAddress())
                .email(clientDto.getEmail())
                .password(clientDto.getPassword())
                .build();

    }

    private ClientDto mapToDto(Client client) {
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
