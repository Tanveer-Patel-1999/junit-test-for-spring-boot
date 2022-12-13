package com.example.onetoone.repository;
import com.example.onetoone.entity.PersonEntity1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository1 extends JpaRepository<PersonEntity1,Long> {
}
