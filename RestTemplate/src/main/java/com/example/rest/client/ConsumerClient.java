package com.example.rest.client;

import com.example.rest.exception.ConsumerClientException;
import com.example.rest.model.Person;
import com.example.rest.model.PersonResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Component
@Log4j2
public class ConsumerClient {

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private final RestTemplate restTemplate;
    @Value("${test.url}")
    private String url;

    public ConsumerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public PersonResponse createPerson(Person person) {

        try {

            ResponseEntity<PersonResponse> personReResponseEntity;
            HttpEntity<Person> http = new HttpEntity<>(person);
            personReResponseEntity = restTemplate.exchange(url + "/persons", HttpMethod.POST, http, PersonResponse.class);
            return personReResponseEntity.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            log.warn("Error while creating Person ", exception);
            if (BAD_REQUEST.equals(exception.getStatusCode())) {
                throw new ConsumerClientException("Person name is already exist", exception);
            }
            throw new ConsumerClientException(String.format("Error when trying to create person with personName = %s", person.getFirstName()), exception);
        }
    }

    public Person getPersonBYId(Long id) {

        try {
            return restTemplate.getForObject(url + "/person/{id}", Person.class, id);

        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            log.info("Error when trying to retrieve person for personId={}", id);
            throw new ConsumerClientException(
                    "Error when trying to retrieve person for personId= " + id);
        }

    }

    public void deleteById(Long id) {
        try {
            restTemplate.delete(url + "/person/" + id);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.warn("Unable to delete Person for PersonId={}", id);
            throw new ConsumerClientException("Unable to delete Person for PersonId=" + id);
        }

    }

    public Person updatePerson(Person person, Long id) {
        try {
            HttpEntity<Person> http = new HttpEntity<>(person);
            ResponseEntity<Person> personResponseEntity = restTemplate.exchange(url + "/person/" + id, HttpMethod.PUT, http, Person.class);
            return personResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            log.warn("Error while updating Person", exception);
            if (BAD_REQUEST.equals(exception.getStatusCode())) {
                throw new ConsumerClientException("Person id already exists.", exception);
            }
            throw new ConsumerClientException(
                    String.format("Error when trying to update person by personId = %s",
                            person.getId()),
                    exception);
        }
    }
}

