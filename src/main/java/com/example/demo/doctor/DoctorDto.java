package com.example.demo.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private Long id;
    private String lastName;
    private  String firstName;
    private String middleName;
    private String specialty;

}
