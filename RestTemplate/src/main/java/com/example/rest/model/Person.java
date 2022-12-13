package com.example.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Person extends PersonResponse {

    private String firstName;
    private String lastName;
    private Address address;

}
