package com.example.onetoone.service;
import com.example.onetoone.entity.PersonEntity;
import com.example.onetoone.exception.PersonNotFoundException;
import com.example.onetoone.mapper.PersonMapperInter;
import com.example.onetoone.model.Person;
import com.example.onetoone.model.PersonResponse;
import com.example.onetoone.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapperInter personMapperInter;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonMapperInter personMapperInter) {
        this.personRepository = personRepository;
        this.personMapperInter = personMapperInter;
    }

    @CacheEvict(value = "persons", allEntries = true)
    public PersonResponse createPerson(Person person) {
        PersonEntity personEntity = personMapperInter.modelToEntity(person);
        personRepository.save(personEntity);
        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(personEntity.getId());
        log.info("user name {} {} ", personEntity.getFirstName(), personEntity.getLastName() + " created successful");
        return personResponse;
    }

    @Cacheable(cacheNames = "persons")
    public List<Person> getAll() {
        List<Person> person = personMapperInter.entityToModels(personRepository.findAll());
        log.info("getting all person.....");
        return person;
    }

    @Cacheable(cacheNames = "persons", key = "#personId")
    public Person getPerson(Long personId) {
        PersonEntity personEntity = personRepository.findById(personId).orElseThrow(()-> new PersonNotFoundException("Person not found:"+personId));
        Person person = personMapperInter.entityToModel(personEntity);
        System.out.println(person);
        log.info("User name {} {} ", person.getFirstName(), person.getLastName() + " retrieved");
        return person;
    }

    @CachePut(cacheNames = "persons", key = "#personId")
    public Person updatePerson(Long personId, Person person) {
        PersonEntity personEntity1 = personRepository.findById(personId).orElseThrow(()-> new PersonNotFoundException("Person not found:"+personId));
        PersonEntity personEntity = personMapperInter.modelToEntity(person);
        personEntity.getAddress().setId(personEntity1.getAddress().getId());
        personEntity.setId(personId);
        personRepository.save(personEntity);
        log.info("person with id {} ", personId + " updated successfully");
        return personMapperInter.entityToModel(personEntity);
    }

    @Cacheable(cacheNames = "persons", key = "#personId")
    public void deletePerson(Long personId) {
        PersonEntity personEntity = personRepository.findById(personId).orElseThrow(()-> new PersonNotFoundException("Person not found:"+personId));
        personRepository.deleteById(personId);
        log.info("person id {} ", personId + " deleted successfully");
    }

}



