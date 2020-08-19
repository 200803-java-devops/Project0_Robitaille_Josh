package com.josh;

import java.sql.*;

public class DataAccess implements Runnable {
    String user, dialog;

    /**
     * Constructor
     * 
     * @param user
     * @param dialog
     * @author Josh Robitaille
     */
    public DataAccess(String user, String dialog) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not find driver with Class.forName()");
        }
        this.user = user;
        this.dialog = dialog;
    }

    /**
     * Start a connection to chatdata database Runs sql query to insert data into
     * the database
     * 
     * @author Josh Robitaille
     */
    public void run() {
        System.out.println("trying connection for db");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chatdata",
                "postgres", "josh_database")) {
            System.out.println("Starting prepared statement");
            PreparedStatement ps = connection.prepareStatement("insert into chatHistory values(? , ?)");
            System.out.println("adding values");
            ps.setString(1, user);
            ps.setString(2, dialog);
            System.out.println("executing query");
            ps.executeUpdate();
            System.out.println("Closing db connection");
            connection.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}