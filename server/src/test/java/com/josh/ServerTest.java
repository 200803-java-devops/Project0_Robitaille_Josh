package com.josh;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class ServerTest {

    @Test
    public void testAddingUsersToServer() {        
        try {
            String usernameTest = new String("username");
            Thread handlerTest = new Thread(new ChatHandler(new Socket("localhost", 8989)));
            Server.users.put(handlerTest, usernameTest);

            String usernameActual = Server.users.get(handlerTest);
            String usernameExpected = "username";

            assertEquals(usernameExpected, usernameActual);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddingWritersToServer(){
        try {
            Socket socket = new Socket("localhost", 8989);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Server.writers.add(writer);

            PrintWriter writerActual = Server.writers.get(0);
            PrintWriter writerExpected = writer;

            assertEquals(writerExpected, writerActual);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
