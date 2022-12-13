package com.example.onetoone.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Person1")
public class PersonEntity1 {

    @Id
    @SequenceGenerator(name = "id", sequenceName = "id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    @PrimaryKeyJoinColumn
    private AddressEntity1 address;


}
