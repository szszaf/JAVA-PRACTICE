package com.example.entities;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int number1;

    public Secondary(String title, int number1, int number2, int number3, int number4, int sum, int product) {
        this.title = title;
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
        this.sum = sum;
        this.product = product;
    }

    private int number2;
    private int number3;
    private int number4;
    private int sum;
    private int product;
}
