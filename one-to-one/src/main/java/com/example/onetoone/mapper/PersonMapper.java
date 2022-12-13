//package com.example.onetoone.mapper;
//
//import com.example.onetoone.entity.AddressEntity;
//import com.example.onetoone.entity.AddressEntity1;
//import com.example.onetoone.entity.PersonEntity;
//import com.example.onetoone.entity.PersonEntity1;
//import com.example.onetoone.model.Person;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class PersonMapper {
//
//    //post---->request body
//    public static PersonEntity1 modelToEntity(Person person) {
//        PersonEntity1 personEntity = new PersonEntity1();
//        personEntity.setFirstName(person.getFirstName());
//        personEntity.setLastName(person.getLastName());
//        AddressEntity1 addressEntity = new AddressEntity1();
//        addressEntity.setAddress1(person.getAddress().getAddress1());
//        addressEntity.setAddress2(person.getAddress().getAddress2());
//        addressEntity.setState(person.getAddress().getState());
//        addressEntity.setPinCode(person.getAddress().getPinCode());
//        addressEntity.setCity(person.getAddress().getCity());
//        personEntity.setAddress(addressEntity);
//        return personEntity;
//    }
//
//    //get all person
//    public static List<Person> entityToModels(final List<PersonEntity> personEntities) {
//        //data is in list when u iterate
//        final ArrayList<Person> person = new ArrayList<>(personEntities.size());
//        person.addAll(personEntities.stream().map(PersonMapper::entityToModel).collect(Collectors.toList()));
//        return person;
//    }
//
//    //get by id
//    public static Person entityToModel(PersonEntity personEntities) {
//        Person person = new Person();
//        person.setFirstName(personEntities.getFirstName());
//        person.setLastName(personEntities.getLastName());
//        //if (personEntities.getAddress().getAddress1() != null)
//        person.getAddress().setAddress1(personEntities.getAddress().getAddress1());
//        //if (personEntities.getAddress().getAddress2() != null)
//        person.getAddress().setAddress2(personEntities.getAddress().getAddress2());
//        //if (personEntities.getAddress().getPinCode() != null)
//        person.getAddress().setPinCode(personEntities.getAddress().getPinCode());
//        //if (personEntities.getAddress().getCity() != null)
//        person.getAddress().setCity(personEntities.getAddress().getCity());
//        //if (personEntities.getAddress().getState() != null)
//        person.getAddress().setState(personEntities.getAddress().getState());
//        return person;
//    }
//
//    //put ----> id , request body --NOT USING MAPid
//    public static PersonEntity modelToEntity1(Person person, PersonEntity personEntity, AddressEntity addressEntity) {
//        personEntity.setFirstName(person.getFirstName());
//        personEntity.setLastName(person.getLastName());
//        addressEntity.setAddress1(person.getAddress().getAddress1());
//        addressEntity.setAddress2(person.getAddress().getAddress2());
//        addressEntity.setState(person.getAddress().getState());
//        addressEntity.setPinCode(person.getAddress().getPinCode());
//        addressEntity.setCity(person.getAddress().getCity());
//        personEntity.setAddress(addressEntity);
//        return personEntity;
//    }
//
//    //put ----> id , request body -- USING MAPid
//    public static PersonEntity1 modelToEntity11(Person person, PersonEntity1 personEntity, AddressEntity1 addressEntity) {
//        personEntity.setFirstName(person.getFirstName());
//        personEntity.setLastName(person.getLastName());
//        addressEntity.setAddress1(person.getAddress().getAddress1());
//        addressEntity.setAddress2(person.getAddress().getAddress2());
//        addressEntity.setState(person.getAddress().getState());
//        addressEntity.setPinCode(person.getAddress().getPinCode());
//        addressEntity.setCity(person.getAddress().getCity());
//        personEntity.setAddress(addressEntity);
//        return personEntity;
//    }
//}
//
