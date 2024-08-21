package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "originals")
@NoArgsConstructor
@Getter
@Setter
public class Original {
    @Id
    @GeneratedValue
    private Long id;

    public Original(String title, int number1, int number2, int number3, int number4) {
        this.title = title;
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
    }

    private String title;
    private int number1;
    private int number2;
    private int number3;
    private int number4;
}
