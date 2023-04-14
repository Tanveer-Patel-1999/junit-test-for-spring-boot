package com.example.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Person extends PersonResponse {

    private String firstName;
    private String lastName;
    private Address address;

}
