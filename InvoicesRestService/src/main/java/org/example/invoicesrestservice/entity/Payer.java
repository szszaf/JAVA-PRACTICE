package org.example.invoicesrestservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Payer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pesel;
    private String nip;
    private String vatUe;

    @OneToOne
    private Individual individual;

    @OneToOne Company company;

    @ManyToMany
    private List<Address> addressList;

}
