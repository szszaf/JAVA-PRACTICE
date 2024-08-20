package org.plscraper.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teamsmatches")
public class TeamMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Match match;

}
