package com.josh;

import java.net.*;
import java.io.*;

public class ChatHandler implements Runnable {
    Socket socket;
    BufferedReader dataIn;
    PrintWriter dataOut;
    String username;
    Thread userThread;

    public ChatHandler(Socket socket) {
        this.socket = socket;
    }

    public void setUserThread(Thread thread) {
        this.userThread = thread;
    }

    public void run() {
        try {
            dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataOut = new PrintWriter(socket.getOutputStream(), true);

            boolean usernameTaken = false;

            while (true) {
                if (usernameTaken) {
                    dataOut.println("NameTaken");
                } else {
                    dataOut.println("NameRequired");
                }
                username = dataIn.readLine();

                if (!Server.users.containsValue(username)) {
                    Server.users.put(userThread, username);
                    break;
                } else {
                    usernameTaken = true;
                }
            }
            dataOut.println("NameAccepted:" + username);
            Server.writers.add(dataOut);

            while (true) {

                String message = dataIn.readLine();
                if (message == null) {
                    return;
                }

                for (PrintWriter writer : Server.writers) {
                    writer.println(username + ": " + message);
                }
            }

        } catch (Exception e) {
            System.err.println("Could not run the chat handler");
        }
    }
}
