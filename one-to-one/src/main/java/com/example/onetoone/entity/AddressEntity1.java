package com.example.onetoone.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address1")
public class AddressEntity1 {

    @Id
    private Long id;

    @Column(name = "address_one")
    private String address1;

    @Column(name = "address_two")
    private String address2;

    @Column(name = "address_city")
    private String city;

    @Column(name = "address_state")
    private String state;

    @Column(name = "pin_code")
    private Long pinCode;

    @MapsId // it is used PK of Person1 and Address1
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private PersonEntity1 person;

}
