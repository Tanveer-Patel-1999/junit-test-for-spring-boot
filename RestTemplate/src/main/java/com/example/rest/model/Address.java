package com.example.rest.model;

import lombok.Data;

@Data

public class Address {
    private String address1;
    private String address2;
    private String city;
    private String state;
    private Long pinCode;
}
