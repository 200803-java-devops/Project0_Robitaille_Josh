package com.josh;

import java.net.*;
import java.sql.Connection;
import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ServerSocket serverSocket;
    Socket clientSocket;
    static ExecutorService threadpool;
    Connection connection;
    static HashMap<String, PrintWriter> users = new HashMap<String, PrintWriter>();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            threadpool = Executors.newFixedThreadPool(15);

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
            threadpool.shutdown();

        } catch (IOException e) {
            System.err.println("Could not shut down server");
        }
    }
}