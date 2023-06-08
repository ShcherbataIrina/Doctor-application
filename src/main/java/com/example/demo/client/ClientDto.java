package com.example.demo.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private int age;
    private String address;
    private String email;
    private String password;
}
