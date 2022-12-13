package com.example.onetoone.repository;
import com.example.onetoone.entity.AddressEntity1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository1 extends JpaRepository<AddressEntity1,Long> {
}
