package com.josh;

public class ClientApp {
    
    public static void main(String[] args) {
        Client client = new Client(9008);
        client.startClient();
    }
}