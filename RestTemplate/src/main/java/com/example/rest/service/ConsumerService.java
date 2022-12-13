package com.example.rest.service;

import com.example.rest.client.ConsumerClient;
import com.example.rest.model.Person;
import com.example.rest.model.PersonResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ConsumerService {


    private final ConsumerClient consumerClient;

    @Autowired
    public ConsumerService(ConsumerClient consumerClient) {
        this.consumerClient = consumerClient;
    }


    public PersonResponse createPerson(Person person) {
        log.info("Creating new Person= {}", person.getFirstName());
        return consumerClient.createPerson(person);
    }

    public Person updatePerson(Person person, Long id) {
        log.info("Updating existing person = {}",  person.getFirstName());
        return consumerClient.updatePerson(person, id);
    }

    public void deleteById(Long id) {
        log.info("Delete person using the id ={}",id);
        consumerClient.deleteById(id);

    }

    public Person getPersonBYId(Long id) {
        log.info("Fetching person using the id ={}",id);
        return consumerClient.getPersonBYId(id);
    }
}
