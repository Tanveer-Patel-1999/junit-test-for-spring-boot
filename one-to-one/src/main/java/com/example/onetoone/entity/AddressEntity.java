package com.example.onetoone.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @SequenceGenerator(name = "address_id", sequenceName = "address_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id")

    @Column(name = "address_id")
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

    @OneToOne(mappedBy = "address")
    private PersonEntity person;
}
