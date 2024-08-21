package com.example.db;


import com.example.entities.Original;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PerformanceTester {
    private static final String dbName = "PerformanceTestDB";
    private static final String dbUser = "postgres";
    private static final String dbPass = "password";
    private final DBConnection connection;
    private SessionFactory sessionFactory;

    public PerformanceTester() {
        this.connection =
                new DBConnection(this.dbName, this.dbUser, this.dbPass);
        this.connection.initConnection();

        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public boolean generateAndSave(int count) {
        Random rand = new Random();

        Session session = this.sessionFactory.openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            IntStream.range(0, count)
                    .mapToObj(i -> new Original(
                            generateRandomString(rand.nextInt(100, 200)),
                            rand.nextInt(100, 200),
                            rand.nextInt(100, 200),
                            rand.nextInt(100, 200),
                            rand.nextInt(100, 200)
                    ))
                    .forEach(session::save);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return true;
    }


    private String generateRandomString(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final Random random = new Random();

        return IntStream.range(0, length)
                        .mapToObj(i -> String.valueOf(characters.charAt(random.nextInt(characters.length()))))
                        .collect(Collectors.joining());
    }
}
