package org.example.invoicesrestservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String zipCode;
    private String street;
    private String buildingNumber;
    private String localNumber;

    @ManyToMany
    private List<Payer> payerList;

}
