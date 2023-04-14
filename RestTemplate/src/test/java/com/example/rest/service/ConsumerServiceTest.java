package com.example.rest.service;

import com.example.rest.client.ConsumerClient;
import com.example.rest.model.Address;
import com.example.rest.model.Person;
import com.example.rest.model.PersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConsumerServiceTest {

    @InjectMocks
    private ConsumerService sut; //sut --> system under test

    @Mock
    private ConsumerClient consumerClient;

    Person person = new Person();

    Address address = new Address();

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);

        address.setAddress1("A");
        address.setAddress2("B");
        address.setState("KA");
        address.setCity("CKD");
        address.setPinCode(591126L);
        person.setId(1L);
        person.setFirstName("Tanveer");
        person.setLastName("Patel");
        person.setAddress(address);
    }

    @Test
    public void createPersonTest()
    {
        Mockito.when(consumerClient.createPerson(Mockito.any(Person.class))).thenReturn(person);
        PersonResponse personResponse = sut.createPerson(person);

        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        Mockito.verify(consumerClient,Mockito.times(1)).createPerson(argumentCaptor.capture());
        Person result = argumentCaptor.getValue();
        assertEquals(person, result);

        assertNotNull(personResponse);
        assertEquals("Tanveer",person.getFirstName());
        assertEquals("Patel",person.getLastName());
        assertEquals(1L,person.getId());
        assertEquals("A",address.getAddress1());
        assertEquals(591126L,person.getAddress().getPinCode());
    }

    @Test
    public void updatePersonTest()
    {
        Mockito.when(consumerClient.updatePerson(Mockito.any(Person.class),Mockito.anyLong())).thenReturn(person);
        Person personResponse = sut.updatePerson(person,1L);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        Mockito.verify(consumerClient, Mockito.times(1)).updatePerson(captor.capture(), argumentCaptor.capture());
        Long id = argumentCaptor.getValue();
        Person result = captor.getValue();
        assertEquals(1L, id);
        assertEquals(person,result);

        assertNotNull(personResponse);
        assertEquals("Tanveer",person.getFirstName());
        assertEquals("Patel",person.getLastName());
        assertEquals(1L,person.getId());
        assertEquals("A",address.getAddress1());
    }

    @Test
    public void getPersonByIdTest()
    {
        Mockito.when(consumerClient.getPersonBYId(Mockito.anyLong())).thenReturn(person);
        Person personResponse = sut.getPersonBYId(1L);



        assertNotNull(personResponse);
        assertEquals("Tanveer",person.getFirstName());
        assertEquals("Patel",person.getLastName());
        assertEquals(1L,person.getId());
        assertEquals("A",address.getAddress1());
    }

    @Test
    public void deletePersonByIdTest()
    {
        Mockito.doNothing().when(consumerClient).deleteById(Mockito.anyLong());
        sut.deleteById(1L);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(consumerClient, Mockito.times(1)).deleteById(argumentCaptor.capture());
        Long id = argumentCaptor.getValue();
        assertEquals(1L,id);

    }

}
