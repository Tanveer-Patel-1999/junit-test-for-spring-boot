package com.example.onetoone.controller;

import com.example.onetoone.controller.PersonController;
import com.example.onetoone.model.Address;
import com.example.onetoone.model.Person;
import com.example.onetoone.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PersonControllerTest {

    private static final String PATH_CREATE_PERSON ="/persons";
    private static final String PATH_GET_PERSON_BY_ID = "/person/1";
    private static final String PATH_DELETE_PERSON_BY_ID = "/person/1";
    private static final String PATH_PUT_PERSON_BY_ID =  "/person/1";

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    private JacksonTester<Person> jsonPerson; // used for objects

    private JacksonTester<Address> jsonAddress;

    MockMvc mockMvc;


    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        ObjectMapper mapper = new ObjectMapper();
        JacksonTester.initFields(this,mapper);
    }

    @Test
    public void testCreatePerson() throws Exception {
        Address address = new Address();
        address.setAddress1("patel gali");
        address.setAddress2("khut area");
        address.setCity("Chikodi");
        address.setState("Karnataka");
        address.setPinCode(591226L);
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("Tanveer");
        person.setLastName("Patel");
        person.setAddress(address);

        when(personService.createPerson(Mockito.any(Person.class))).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson.write(person).getJson()))
                        .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(personService, times(1)).createPerson(person);
    }

    @Test
    public void testGetPerson() throws Exception {
        Person person = new Person();
        Address address = new Address();
        address.setAddress1("patel galli");
        address.setAddress2("khut area");
        address.setCity("Chikodi");
        address.setState("karnataka");
        address.setPinCode(591226L);
        person.setId(1L);
        person.setFirstName("Tanveer");
        person.setLastName("Patel");
        person.setAddress(address);

        when(personService.getPerson(anyLong())).thenReturn(new Person());
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_PERSON_BY_ID))
                .andExpect(status().isOk());
        Mockito.verify(personService, times(1)).getPerson(anyLong());
    }

    @Test
    public void testDeletePersonById() throws Exception {
        Person person = new Person();
        Address address = new Address();
        address.setAddress1("patel galli");
        address.setAddress2("khut area");
        address.setCity("Chikodi");
        address.setState("karnataka");
        address.setPinCode(591226L);
        person.setId(1L);
        person.setFirstName("Tanveer");
        person.setLastName("Patel");
        person.setAddress(address);

        //used for void
        doNothing().when(personService).deletePerson(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_DELETE_PERSON_BY_ID,anyLong()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        Mockito.verify(personService, times(1)).deletePerson(anyLong());

    }

    @Test
    public void testUpdate() throws Exception {
        Person person = new Person();
        Address address = new Address();
        address.setAddress1("patel galli");
        address.setAddress2("khut area");
        address.setCity("Chikodi");
        address.setState("karnataka");
        address.setPinCode(591226L);
        person.setId(1L);
        person.setFirstName("Tanveer");
        person.setLastName("Patel");
        person.setAddress(address);

        when(personService.updatePerson(1L,person)).thenReturn(person);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_PERSON_BY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson.write(person).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();
        Mockito.verify(personService, times(1)).updatePerson(1L,person);
        assertEquals(jsonPerson.write(person).getJson(), response.getContentAsString());
    }
}
