package com.example.rest.controller;

import com.example.rest.model.Person;
import com.example.rest.model.PersonResponse;
import com.example.rest.service.ConsumerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }


    @GetMapping(path = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPersonBYId(@PathVariable Long id) {
        Person person = consumerService.getPersonBYId(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping(path = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> createPerson(@RequestBody Person person) {
        PersonResponse personRe = consumerService.createPerson(person);
        return new ResponseEntity<>(personRe, HttpStatus.OK);
    }

    @PutMapping(value = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Person personResponse = consumerService.updatePerson(person, id);
        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }

    @DeleteMapping(path = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        consumerService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}

