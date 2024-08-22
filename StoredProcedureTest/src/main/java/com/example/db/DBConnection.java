package com.example.db;


import com.example.entities.Original;
import com.example.entities.Secondary;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DBConnection {
    private Connection conn = null;
    private String username;
    private String password;
    private String dbName;
    public static final String dbServerAddress = "jdbc:postgresql://localhost:5432/";
    private final SessionFactory sessionFactory;

    public DBConnection(String dbName, String username, String password) {
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public boolean initConnection() {
        try {
            this.conn = DriverManager.
                    getConnection(this.dbServerAddress + this.dbName,
                            this.username, this.password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean queryExecution(String query) {
        try(Statement stmt = this.conn.createStatement()) {
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    public boolean deleteFromOriginals() {
        String query = "delete from originals";
        return this.queryExecution(query);
    }

    public boolean deleteFromSecondaries() {
        String query = "delete from secondaries";
        return this.queryExecution(query);
    }

    public boolean  copySumProductStoredProcedure() {
        String query = "call copy_sum_product()";
        return this.queryExecution(query);
    }

    public boolean copySumProductProgrammatically() {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            List<Original> entities =
                    session.createQuery("from Original", Original.class).getResultList();

            entities.stream().map(original -> new Secondary(
                    original.getTitle(),
                    original.getNumber1(),
                    original.getNumber2(),
                    original.getNumber3(),
                    original.getNumber4(),
                    original.getNumber1() + original.getNumber2() +
                            original.getNumber3() + original.getNumber4(),
                    original.getNumber1() * original.getNumber2() *
                            original.getNumber3() * original.getNumber4()
            )).forEach(session::save);

            transaction.commit();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
            return false;
        }
    }


    private String generateRandomString(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final Random random = new Random();

        return IntStream.range(0, length)
                .mapToObj(i -> String.valueOf(characters.charAt(random.nextInt(characters.length()))))
                .collect(Collectors.joining());
    }
}
