package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "secondaries")
@NoArgsConstructor
@Getter
@Setter
public class Secondary {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int sum;
    private int product;
}
