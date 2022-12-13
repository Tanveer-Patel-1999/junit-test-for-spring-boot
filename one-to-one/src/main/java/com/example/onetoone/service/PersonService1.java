package com.example.onetoone.service;

import com.example.onetoone.entity.PersonEntity1;
import com.example.onetoone.mapper.PersonMapper1;
import com.example.onetoone.model.Person;
import com.example.onetoone.model.PersonResponse;
import com.example.onetoone.repository.PersonRepository1;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PersonService1 {
    private final PersonRepository1 personRepository1;
    private final PersonMapper1 personMapper1;

    public PersonService1(PersonRepository1 personRepository1, PersonMapper1 personMapper1) {
        this.personRepository1 = personRepository1;
        this.personMapper1 = personMapper1;
    }

    public PersonResponse createPerson(Person person) {
        PersonEntity1 personEntity1 = personMapper1.modelToEntity(person);
        personEntity1.getAddress().setPerson(personEntity1);
        personRepository1.save(personEntity1);
        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(personEntity1.getId());
        log.info("Person Created Successfully!!");
        return personResponse;
    }
    /*
     @CacheEvict(value = "persons", allEntries = true)
    public PersonResponse createPerson(Person person) {
        PersonEntity personEntity = personMapperInter.modelToEntity(person);
        personRepository.save(personEntity);

        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(personEntity.getId());
        log.info("user name {} {} ", personEntity.getFirstName(), personEntity.getLastName() + " created successful");
        return personResponse;
    }
     */

    public List<Person> getAll() {
        List<Person> addressList = personMapper1.entityToModels(personRepository1.findAll());
        log.info("getting all person.....");
        return addressList;
    }

    public Person getPerson(Long personId) {
        PersonEntity1 personEntity1 = personRepository1.findById(personId).orElseThrow();
        Person person = personMapper1.entityToModel(personEntity1);
        log.info("User name {} {} ", person.getFirstName(), person.getLastName() + " retrieved");
        return person;
    }

    public Person updatePerson(Long personId, Person person) {
        PersonEntity1 personEntity1 = personRepository1.findById(personId).orElseThrow();
        PersonEntity1 personEntity = personMapper1.modelToEntity(person);
        personEntity.getAddress().setId(personEntity1.getAddress().getId());
        personEntity.setId(personId);
        personRepository1.save(personEntity);
        log.info("person with id {} ", personId + " updated successfully");
        person = personMapper1.entityToModel(personEntity1);
        return person;
    }

    public void deletePerson(Long personId) {
        personRepository1.deleteById(personId);
        log.info("person id {} ", personId + " deleted successfully");
    }
}
