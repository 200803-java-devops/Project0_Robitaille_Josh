package com.josh;

import java.net.*;
import java.sql.Connection;
import java.io.*;
import java.util.HashMap;

public class Server {
    static ServerSocket serverSocket;
    Socket clientSocket;
    Connection connection;
    static HashMap<String, PrintWriter> users = new HashMap<String, PrintWriter>();

    /**
     * Constructor
     * Runtime runs if the server is shutdown with Crl C or the like and shuts down the server correctly
     * @param port
     * @author Josh Robitaille
     */
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);

            Thread shutdownThread = new Thread(new Runnable(){
                @Override
                public void run() {
                    System.err.println("Shutting down server");
                    shutdownServer();
                }                    
            });

            Runtime.getRuntime().addShutdownHook(new Thread(shutdownThread));

        } catch (IOException e) {
            System.err.println("Server could not be set up through port " + port);
        }
    }

    /**
     * Method that is called in ServerApp 
     * Listens for connections to clients through port 9008
     * When accepted it starts a new Clienthandler
     * @author Josh Robitaille
     */
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

    public void shutdownServer() {
        try {
            serverSocket.close();

        } catch (IOException e) {
            System.err.println("Could not shut down server");
        }
    }
}