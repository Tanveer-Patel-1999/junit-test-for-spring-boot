package com.example.onetoone.model;
import lombok.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Person extends PersonResponse{

    private String firstName;
    private String lastName;
    private Address address;
}
