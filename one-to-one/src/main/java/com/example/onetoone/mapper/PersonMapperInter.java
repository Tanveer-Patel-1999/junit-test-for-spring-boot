package com.example.onetoone.mapper;
import com.example.onetoone.entity.PersonEntity;
import com.example.onetoone.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapperInter {

    PersonEntity modelToEntity(Person person);

    Person entityToModel(PersonEntity personEntity);

    List<Person> entityToModels(List<PersonEntity> personEntities);

}
