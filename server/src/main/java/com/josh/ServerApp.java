package com.josh;

public class ServerApp {
    public static void main(String[] args) {
        Server server = new Server(9008);
        System.out.println("Starting server . . . ");
        server.startServer();
    }
}