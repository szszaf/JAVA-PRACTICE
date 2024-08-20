package org.plscraper.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "matches")
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private LocalDate matchDate;
    private int attendance;

    public Match(LocalDate matchDate, int attendance) {
        this.matchDate = matchDate;
        this.attendance = attendance;
    }
}
