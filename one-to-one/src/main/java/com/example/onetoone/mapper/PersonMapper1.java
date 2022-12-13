package com.example.onetoone.mapper;
import com.example.onetoone.entity.PersonEntity1;
import com.example.onetoone.model.Person;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper1 {
    Person entityToModel(PersonEntity1 personEntity1);

    PersonEntity1 modelToEntity(Person person);

    List<Person> entityToModels(List<PersonEntity1> personEntities1);
}
