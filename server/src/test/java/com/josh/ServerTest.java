package com.josh;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class ServerTest {
    @Test
    public void testServerConnect() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chatdata",
                "postgres", "josh_database")) {

            // test if connection exists
            if (connection != null) {
                assertTrue(true);
            } else {
                assertFalse(true);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
    }
}
