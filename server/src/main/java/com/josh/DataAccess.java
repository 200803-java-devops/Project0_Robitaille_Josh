package com.josh;

import java.sql.*;

public class DataAccess implements Runnable {
    static String url = "jdbc:postgresql://127.0.0.1:63099///chatdata";
    static String postgresUser = "postgres";
    static String password = "josh_database";

    String user, dialog;

    /**
     * Constructor
     * @param user
     * @param dialog
     * @author Josh Robitaille
     */
    public DataAccess(String user, String dialog){
        this.user = user;
        this.dialog = dialog;
    }

    /**
     * Start a connection to chatdata database
     * Runs sql query to insert data into the database
     * @author Josh Robitaille
     */
    public  void run() {
        try (Connection connection = DriverManager.getConnection(url, postgresUser, password);) {
            System.out.println("Starting prepared statement");
            PreparedStatement ps = connection.prepareStatement("insert into chatdataTable(username, dialog) values(? , ?)");
            System.out.println("adding values");
            ps.setString(1, user);
            ps.setString(2, dialog);
            System.out.println("executing query");
            ps.executeQuery();
            int rowsAffected = ps.executeUpdate();
            System.out.println("Closing db connection");
            connection.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}