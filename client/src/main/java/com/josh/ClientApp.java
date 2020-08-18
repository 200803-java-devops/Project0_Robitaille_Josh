package com.josh;

public class ClientApp {
    
    /**
     * Main method for the client side of the program
     * Creates a new client
     * Runs the startClient() method in the client class
     * RUN THIS AFTER RUNNING THE SERVER APP
     * Can be run multiple times to create more chat windows
     * @param args
     * @author Josh Robitaille
     */
    public static void main(String[] args) {
        Client client = new Client(9008);
        client.startClient();
    }
}