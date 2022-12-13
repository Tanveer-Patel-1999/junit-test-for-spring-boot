package com.example.onetoone.controller;

import com.example.onetoone.model.Person;
import com.example.onetoone.model.PersonResponse;
import com.example.onetoone.service.PersonService1;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController1 {
    private final PersonService1 personService1;

    public PersonController1(PersonService1 personService1) {
        this.personService1 = personService1;
    }

    @PostMapping(value = "/persons1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> createPerson(@RequestBody Person person) {
        PersonResponse personResponse = personService1.createPerson(person);
        return new ResponseEntity<>(personResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/persons1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> gatAll() {
        List<Person> person = personService1.getAll();
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping(value = "/person1/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPerson(@PathVariable Long personId) {
        Person person = personService1.getPerson(personId);
        return ResponseEntity.ok(person);
    }

    @PutMapping(value = "/person1/{personId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        Person person1 = personService1.updatePerson(personId, person);
        return new ResponseEntity<Person>(person1, HttpStatus.OK);
    }

    @DeleteMapping(value = "/person1/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        personService1.deletePerson(personId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
