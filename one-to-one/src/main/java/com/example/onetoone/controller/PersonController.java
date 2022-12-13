package com.example.onetoone.controller;

import com.example.onetoone.model.Person;
import com.example.onetoone.model.PersonResponse;
import com.example.onetoone.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> createPerson(@RequestBody Person person) {
        PersonResponse personResponse = personService.createPerson(person);
        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }


    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> gatAll() {
        List<Person> personEntities = personService.getAll();
        return new ResponseEntity<>(personEntities, HttpStatus.OK);
    }

    @GetMapping(value = "/person/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPerson(@PathVariable Long personId) {
        Person person = personService.getPerson(personId);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping(value = "/person/{personId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        Person person1 = personService.updatePerson(personId, person);
        return new ResponseEntity<>(person1, HttpStatus.OK);
    }

    @DeleteMapping(value = "/person/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
