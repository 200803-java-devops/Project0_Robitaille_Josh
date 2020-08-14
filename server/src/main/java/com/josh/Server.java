package com.josh;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    ServerSocket serverSocket;
    Socket clientSocket;
    static HashMap<Thread, String> users = new HashMap<Thread, String>();
    static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            System.err.println("Server could not be set up through port " + port);
        }
    }

    public void startServer() {
        try {
            System.out.println("Waiting for connections . . .");
            while (true) {
                this.clientSocket = serverSocket.accept();
                System.out.println("Client was accepted!");

                Thread handler = new Thread(new ChatHandler(this.clientSocket));
                handler.start();
            }
        } catch (IOException e) {
            System.err.println("Client was not accepted");
        }
    }

}