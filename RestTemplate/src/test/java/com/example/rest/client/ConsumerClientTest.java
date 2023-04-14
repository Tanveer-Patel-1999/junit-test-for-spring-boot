package com.example.rest.client;

import com.example.rest.exception.ConsumerClientException;
import com.example.rest.model.Address;
import com.example.rest.model.Person;
import com.example.rest.model.PersonResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ConsumerClientTest {

    private static final Long PERSON_ID = 1L;

//    we are get that url from Application.properties
    private static final String ONE_TO_ONE_REST_HOST = "http://one-to-one";
    Person person = new Person();
    @InjectMocks
    private ConsumerClient sut;
    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

//        that url came from ConsumerClient
        ReflectionTestUtils.setField(sut, "url", ONE_TO_ONE_REST_HOST);

        Address address = new Address();
        address.setAddress1("A");
        address.setAddress2("B");
        address.setPinCode(591227L);
        address.setState("KA");
        address.setCity("CKD");
        person.setId(PERSON_ID);
        person.setFirstName("Tanveer");
        person.setLastName("Patel");
        person.setAddress(address);
    }

    @Test
    public void createPersonRestTest() {
        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(1L);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),
                        Mockito.eq(HttpMethod.POST),
                        Mockito.any(HttpEntity.class),
                        Mockito.eq(PersonResponse.class)))
                .thenReturn(ResponseEntity.ok(personResponse));

        PersonResponse response = sut.createPerson(person);
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(Mockito.any(String.class),
                Mockito.eq(HttpMethod.POST),
                Mockito.any(HttpEntity.class),
                Mockito.eq(PersonResponse.class));
        assertEquals(1L, response.getId());
        assertEquals("Tanveer", person.getFirstName());
        assertEquals("Patel", person.getLastName());
        assertEquals(1L, person.getId());
        assertEquals("A", person.getAddress().getAddress1());
        assertEquals("B", person.getAddress().getAddress2());
    }

    @Test
    public void testCreateSite_badRequest() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {

            Mockito.when(restTemplate.exchange(Mockito.anyString(),
                            Mockito.eq(HttpMethod.POST),
                            Mockito.any(HttpEntity.class),
                            Mockito.eq(PersonResponse.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

            PersonResponse response = sut.createPerson(person);
            Mockito.verify(restTemplate, Mockito.times(1)).exchange(Mockito.any(String.class),
                    Mockito.eq(HttpMethod.POST),
                    Mockito.any(HttpEntity.class),
                    Mockito.eq(PersonResponse.class));
            assertNotNull(response);
        });
        Assertions.assertEquals("Person name is already exist", thrown.getMessage());
    }

    @Test
    public void testCreateSite_InternalServer() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {

            Mockito.when(restTemplate.exchange(Mockito.anyString(),
                            Mockito.eq(HttpMethod.POST),
                            Mockito.any(HttpEntity.class),
                            Mockito.eq(PersonResponse.class)))
                    .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            PersonResponse response = sut.createPerson(person);
            Mockito.verify(restTemplate, Mockito.times(1)).exchange(Mockito.any(String.class),
                    Mockito.eq(HttpMethod.POST),
                    Mockito.any(HttpEntity.class),
                    Mockito.eq(PersonResponse.class));
            assertNotNull(response);
        });
        Assertions.assertEquals("Error when trying to create person with personName = Tanveer", thrown.getMessage());
    }

    @Test
    public void testGetPersonRestByIdTest() {

        when(restTemplate.getForObject(anyString(), Mockito.eq(Person.class), Mockito.anyLong()))
                .thenReturn(new Person());
        sut.getPersonBYId(1L);
//      Mockito.verify(restTemplate, Mockito.times(1)).getForObject(anyString(), Mockito.eq(Person.class), Mockito.anyLong());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(anyString(), Mockito.eq(Person.class), argumentCaptor.capture());
        Long actualValue = argumentCaptor.getValue();
        assertEquals(1L, actualValue);
        System.out.println(actualValue);


        assertEquals("Tanveer", person.getFirstName());
        assertEquals("Patel", person.getLastName());
        assertEquals(1L, person.getId());
        assertEquals("A", person.getAddress().getAddress1());
        assertEquals("B", person.getAddress().getAddress2());
    }

    @Test
    public void ConsumerClientException_badRequest() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {
            when(restTemplate.getForObject(anyString(), Mockito.eq(Person.class), Mockito.anyLong()))
                    .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
            sut.getPersonBYId(1L);

            ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
            Mockito.verify(restTemplate, Mockito.times(1)).getForObject(anyString(),
                    Mockito.eq(Person.class), argumentCaptor.capture());
            Long id = argumentCaptor.getValue();
            assertEquals(1L,id);
        });
        Assertions.assertEquals("Error when trying to retrieve person for personId= 1", thrown.getMessage());

    }

    @Test
    public void ConsumerClientException_InternalServerError() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {
            when(restTemplate.getForObject(anyString(), Mockito.eq(Person.class), Mockito.anyLong()))
                    .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
            sut.getPersonBYId(1L);

            ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
            Mockito.verify(restTemplate, Mockito.times(1))
                    .getForObject(anyString(),
                            Mockito.eq(Person.class),
                            argumentCaptor.capture());
            Long id = argumentCaptor.getValue();
            assertEquals(1L,id);
        });
        Assertions.assertEquals("Error when trying to retrieve person for personId= 1", thrown.getMessage());
    }

    @Test
    public void updatePersonRestByIdTest() {
        Address address = new Address();
        address.setAddress1("A");
        address.setAddress2("B");
        address.setPinCode(591227L);
        address.setState("KA");
        address.setCity("CKD");
        person.setId(PERSON_ID);
        person.setFirstName("Tanveer");
        person.setLastName("Patel");
        person.setAddress(address);

        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.PUT), Mockito.any(HttpEntity.class), Mockito.eq(Person.class))).thenReturn((ResponseEntity.ok(new Person())));
        Person response = sut.updatePerson(person, 1L);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(argumentCaptor.capture(), Mockito.eq(HttpMethod.PUT), Mockito.any(HttpEntity.class), Mockito.eq(Person.class));
        String actualValue = argumentCaptor.getValue();
        assertEquals("http://one-to-one/person/1", actualValue);
        assertNotNull(response);


        assertEquals("Tanveer", person.getFirstName());
        assertEquals("Patel", person.getLastName());
        assertEquals(1L, person.getId());
        assertEquals("A", person.getAddress().getAddress1());
        assertEquals("B", person.getAddress().getAddress2());

    }


    @Test
    public void update_exceptionInternal() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {
            when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.PUT), Mockito.any(HttpEntity.class), Mockito.eq(Person.class)))
                    .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
            Person response = sut.updatePerson(person, 1L);

            ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
            Mockito.verify(restTemplate, Mockito.times(1)).exchange(argumentCaptor.capture(), Mockito.eq(HttpMethod.PUT), Mockito.any(HttpEntity.class), Mockito.eq(Person.class));
            String actualValue = argumentCaptor.getValue();
            assertEquals("http://one-to-one/person/1", actualValue);
            assertEquals(1L, response.getId());
        });
        Assertions.assertEquals("Error when trying to update person by personId = 1", thrown.getMessage());

    }

    @Test
    public void update_exceptionBadRequest() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {
            when(restTemplate.exchange(Mockito.anyString(),
                    Mockito.eq(HttpMethod.PUT),
                    Mockito.any(HttpEntity.class),
                    Mockito.eq(Person.class)))
                    .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
            Person response = sut.updatePerson(person, 1L);

            ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
            Mockito.verify(restTemplate, Mockito.times(1)).exchange(argumentCaptor.capture(), Mockito.eq(HttpMethod.PUT), Mockito.any(HttpEntity.class), Mockito.eq(Person.class));
            String actualValue = argumentCaptor.getValue();
            assertEquals("http://one-to-one/person/1", actualValue);
            assertEquals(1L, response.getId());
        });
        Assertions.assertEquals("Person id already exists.", thrown.getMessage());
    }

    @Test
    public void deletePersonRestByIDTest() {
        Mockito.doNothing().when(restTemplate).delete(anyString());
        sut.deleteById(1L);
//        verify(restTemplate, times(1)).delete(anyString());
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(restTemplate, times(1)).delete(argumentCaptor.capture());
        String actualValue = argumentCaptor.getValue();
        assertEquals("http://one-to-one/person/1", actualValue);

    }
    @Test
    public void deletePerson_clientError() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {
            doThrow(new HttpClientErrorException(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed"))
                    .when(restTemplate).delete(Mockito.anyString());
            sut.deleteById(1L);
            verify(restTemplate, Mockito.times(1)).delete(Mockito.anyString());
        });
        Assertions.assertEquals("Unable to delete Person for PersonId=1", thrown.getMessage());
    }

    @Test
    public void deletePerson_BadRequest() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {
            doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                    .when(restTemplate).delete(Mockito.anyString());
            sut.deleteById(1L);
            verify(restTemplate, Mockito.times(1)).delete(Mockito.anyString());
        });
        Assertions.assertEquals("Unable to delete Person for PersonId=1", thrown.getMessage());
    }

    @Test
    public void deletePerson_BadRequest1() {
        ConsumerClientException thrown = Assertions.assertThrows(ConsumerClientException.class, () -> {
            doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST))
                    .when(restTemplate).delete(Mockito.anyString());
            sut.deleteById(1L);
            verify(restTemplate, Mockito.times(1)).delete(Mockito.anyString());
        });
        Assertions.assertEquals("Unable to delete Person for PersonId=1", thrown.getMessage());
    }



}
