package com.josh;

public class ServerApp {
    /**
     * Main method for the Server side of the program
     * Creates a new server on port 9008
     * Runs the startServer() method in the server class
     * 
     * RUN THIS BEFORE YOU RUN A CLIENT
     */
    public static void main(String[] args) {
        Server server = new Server(9008);
        System.out.println("Starting server . . . ");
        server.startServer();
    }
}