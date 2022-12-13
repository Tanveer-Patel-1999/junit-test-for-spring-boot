package com.example.onetoone.service;

import com.example.onetoone.entity.AddressEntity;
import com.example.onetoone.entity.PersonEntity;
import com.example.onetoone.mapper.PersonMapperInter;
import com.example.onetoone.mapper.PersonMapperInterImpl;
import com.example.onetoone.model.Person;
import com.example.onetoone.model.PersonResponse;
import com.example.onetoone.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @InjectMocks
    @Autowired
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonMapperInter personMapperInter;

    @Mock
    private Mapper mapper;


    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        PersonMapperInterImpl personMapperInter1 = new PersonMapperInterImpl();
        personService = new PersonService(personRepository,personMapperInter1);

    }

    @Test
    public void createPersonTest() throws Exception
    {
        Person person = new Person();
        PersonEntity personEntity = new PersonEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress1("A");
        addressEntity.setAddress2("B");
        addressEntity.setState("KA");
        addressEntity.setCity("CKD");
        addressEntity.setPinCode(591226L);
        personEntity.setId(1L);
        personEntity.setFirstName("tanveer");
        personEntity.setLastName("patel");
        personEntity.setAddress(addressEntity);

        Mockito.when(personMapperInter.modelToEntity(any(Person.class))).thenReturn(personEntity);
        Mockito.when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity);
        PersonResponse personResponse = personService.createPerson(person);

//        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
//        verify(personRepository,times(1)).c(argumentCaptor.capture());
//        Person actualValue = argumentCaptor.getValue();
//        assertEquals(person,actualValue);


        personService.createPerson(person);
        assertEquals("tanveer",personEntity.getFirstName());
        assertEquals("patel", personEntity.getLastName());
        assertEquals(1l, personEntity.getId());
        assertEquals("A",addressEntity.getAddress1());
        assertEquals("B",addressEntity.getAddress2());
        assertEquals("KA",addressEntity.getState());
        assertEquals("CKD", addressEntity.getCity());
        assertEquals(591226,addressEntity.getPinCode());

    }

    @Test
    public void getPersonByIdTest()
    {
        Person person = new Person();
        PersonEntity personEntity = new PersonEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress1("A");
        addressEntity.setAddress2("B");
        addressEntity.setState("KA");
        addressEntity.setCity("CKD");
        addressEntity.setPinCode(591226L);
        personEntity.setId(1L);
        personEntity.setFirstName("tanveer");
        personEntity.setLastName("patel");
        personEntity.setAddress(addressEntity);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.when(personRepository.findById(anyLong())).thenReturn(Optional.of(new PersonEntity()));
        Mockito.when(personMapperInter.entityToModel(personEntity)).thenReturn(person);
        Person personResponse = personService.getPerson(anyLong());
        assertNotNull(personResponse);

        assertEquals("tanveer",personEntity.getFirstName());
        assertEquals("patel", personEntity.getLastName());
        assertEquals(1l, personEntity.getId());
        assertEquals("A",addressEntity.getAddress1());
        assertEquals("B",addressEntity.getAddress2());
        assertEquals("KA",addressEntity.getState());
        assertEquals("CKD", addressEntity.getCity());
        assertEquals(591226,addressEntity.getPinCode());
    }

    @Test
    public void getAllPersonTest()
    {
        Person person = new Person();
        PersonEntity personEntity = new PersonEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress1("A");
        addressEntity.setAddress2("B");
        addressEntity.setState("KA");
        addressEntity.setCity("CKD");
        addressEntity.setPinCode(591226L);
        personEntity.setId(1L);
        personEntity.setFirstName("tanveer");
        personEntity.setLastName("patel");
        personEntity.setAddress(addressEntity);

        Mockito.when(personMapperInter.entityToModel(personEntity)).thenReturn(person);
        Mockito.when(personRepository.findAll()).thenReturn(Arrays.asList(personEntity));
        List<Person> personList = personService.getAll();
        assertNotNull(personList);

        assertEquals("tanveer",personEntity.getFirstName());
        assertEquals("patel", personEntity.getLastName());
        assertEquals(1l, personEntity.getId());
        assertEquals("A",addressEntity.getAddress1());
        assertEquals("B",addressEntity.getAddress2());
        assertEquals("KA",addressEntity.getState());
        assertEquals("CKD", addressEntity.getCity());
        assertEquals(591226,addressEntity.getPinCode());

    }


    @Test
    public void personDeletedByIdTest()
    {
        PersonEntity personEntity = new PersonEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress1("A");
        addressEntity.setAddress2("B");
        addressEntity.setState("KA");
        addressEntity.setCity("CKD");
        addressEntity.setPinCode(591226L);
        personEntity.setId(1L);
        personEntity.setFirstName("tanveer");
        personEntity.setLastName("patel");
        personEntity.setAddress(addressEntity);

       when(personRepository.findById(anyLong())).thenReturn(Optional.of(personEntity));
       Person person = personService.getPerson(1L);
       assertNotNull(person);

       assertEquals(1L,person.getId());


    }

    @Test
    public void UpdatePersonById()
    {
        Person person = new Person();
        PersonEntity personEntity = new PersonEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress1("A");
        addressEntity.setAddress2("B");
        addressEntity.setState("KA");
        addressEntity.setCity("CKD");
        addressEntity.setPinCode(591226L);
        personEntity.setId(1L);
        personEntity.setFirstName("tanveer");
        personEntity.setLastName("patel");
        personEntity.setAddress(addressEntity);

        when(personRepository.findById(1L)).thenReturn(Optional.of(new PersonEntity()));
        when(personMapperInter.modelToEntity(person)).thenReturn(personEntity);
        when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity);
        when(personMapperInter.modelToEntity(person)).thenReturn(personEntity);

//        when(personService.updatePerson(1L,person)).thenReturn(person);
//        Person person1 = personService.updatePerson(1L,person);
//        assertNotNull(person1);




    }

}
