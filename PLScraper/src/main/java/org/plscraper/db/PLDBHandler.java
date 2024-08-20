package org.plscraper.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.plscraper.entity.Team;

import java.util.Set;
import java.util.stream.Collectors;

public class PLDBHandler {
    private final String dbName = "PremierLeagueDB";
    private final String dbUser = "postgres";
    private final String dbPass = "password";

    private SessionFactory sessionFactory;
    private DBConnection connection = null;

    public PLDBHandler() {
//        this.connection =
//                new DBConnection(this.dbName, this.dbUser, this.dbPass);
//        this.connection.initConnection();

        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Boolean insertTeams2(Set<String> teamNames) {
        Set<Team> teams = teamNames.stream()
                .map(teamName -> new Team(teamName))
                .collect(Collectors.toSet());

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            for(Team team : teams) {
                session.save(team);
            }

            transaction.commit();
        }

        return true;
    }

    public Boolean insertTeams(Set<String> teams) {
        String teamsValuesQuery = teams.stream()
                        .map(teamName -> teamName.replace("'", " "))
                        .map(teamName -> " ('" + teamName + "'),")
                        .collect(Collectors.joining());

        teamsValuesQuery = teamsValuesQuery.substring(0, teamsValuesQuery.length() - 1);

        String query = "insert into teams (name) values" + teamsValuesQuery;

        this.connection.queryExecution(query);

        return true;
    }
}
