package com.example.rest.controller;

import com.example.rest.model.Address;
import com.example.rest.model.Person;
import com.example.rest.model.PersonResponse;
import com.example.rest.service.ConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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

class ConsumerControllerTest {

    private static final String PATH_CREATE_PERSON = "/persons";
    private static final String PATH_GET_PERSON_BY_ID = "/person/1";
    private static final String PATH_DELETE_PERSON_BY_ID = "/person/1";
    private static final String PATH_PUT_PERSON_BY_ID = "/person/1";
    MockMvc mockMvc;
    JacksonTester<Person> jsonPerson;
    @InjectMocks
    private ConsumerController consumerController;
    @Mock
    private ConsumerService consumerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consumerController).build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JacksonTester.initFields(this, mapper);


    }

    @Test
     void testCreatePerson() throws Exception {
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

        when(consumerService.createPerson(any(Person.class))).thenReturn(any(PersonResponse.class));

        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson.write(person).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("Tanveer", person.getFirstName());
        assertEquals("Patel", person.getLastName());
        assertEquals(1L, person.getId());
        assertEquals("patel gali", address.getAddress1());
    }

    @Test
    void testGetPersonById() throws Exception {
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

        when(consumerService.getPersonBYId(anyLong())).thenReturn(any(Person.class));
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_PERSON_BY_ID))
                .andExpect(status().isOk());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(consumerService, times(1)).getPersonBYId(argumentCaptor.capture());
        Long id = argumentCaptor.getValue();
        assertEquals(1L, id);

        assertEquals("Tanveer", person.getFirstName());
        assertEquals("Patel", person.getLastName());
        assertEquals(1L, person.getId());
        assertEquals("patel galli", address.getAddress1());
    }

    @Test
    void testDeletePersonById() throws Exception {
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

        doNothing().when(consumerService).deleteById(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_DELETE_PERSON_BY_ID, anyLong()))
                .andExpect(status()
                        .isOk()).andReturn().getResponse();


        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(consumerService, times(1)).deleteById(argumentCaptor.capture());
        Long id = argumentCaptor.getValue();
        assertEquals(1L, id);

        assertEquals("Tanveer", person.getFirstName());
        assertEquals("Patel", person.getLastName());
        assertEquals(1L, person.getId());
        assertEquals("patel galli", address.getAddress1());

    }

    @Test
    void testUpdate() throws Exception {
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

        when(consumerService.updatePerson(any(Person.class), anyLong())).thenReturn(person);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_PERSON_BY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson.write(person).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);

        Mockito.verify(consumerService, times(1)).updatePerson(captor.capture(), argumentCaptor.capture());
        Long id = argumentCaptor.getValue();
        Person result = captor.getValue();
        assertEquals(1L, id);

//        Mockito.verify(consumerService, times(1)).updatePerson(person,1L);
        assertEquals(jsonPerson.write(person).getJson(), response.getContentAsString());

    }


}
