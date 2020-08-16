package com.josh;

import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Client {
    static JFrame welcomeWindow = new JFrame("start");
    static JFrame chatWindow = new JFrame("Chat");
    static JTextArea chatArea = new JTextArea(30, 40);
    static JTextField textField = new JTextField(40);
    static JLabel label = new JLabel("          ");
    static JButton sendButton = new JButton("Send");
    static JLabel nameLabel = new JLabel("          ");

    Socket socket;
    InetAddress ip;
    int port;
    static String username;
    static BufferedReader reader;
    static PrintWriter writer;

    public Client(int port) {
        try {
            this.ip = InetAddress.getByName("localhost");
            this.port = port;

        } catch (UnknownHostException e) {
            System.err.println("Client could not get host name");
        }
        setJFrameProperties();
    }

    public void setJFrameProperties() {
        chatWindow.setLayout(new FlowLayout());
        chatWindow.add(nameLabel);
        chatWindow.setTitle("Chat Application");
        chatWindow.add(new JScrollPane(chatArea));
        chatWindow.add(label);
        chatWindow.add(textField);
        chatWindow.add(sendButton);
        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatWindow.setSize(500, 625);
        chatWindow.setVisible(true);

        textField.setEditable(false);
        chatArea.setEditable(false);

        sendButton.addActionListener(new Listener());
        textField.addActionListener(new Listener());
    }

    public void startSocket() {
        try {
            socket = new Socket(ip, port);
            System.out.println("Client started");
        } catch (IOException e) {
            System.err.println("Could not create socket for client on port " + port);
        }
    }

    public void startClient() {
        try {
            startSocket();
            
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String message = reader.readLine();
                if (message.equals("NameRequired")) {
                    username = JOptionPane.showInputDialog(chatWindow, "Enter username", "Start",
                            JOptionPane.PLAIN_MESSAGE);
                    writer.println(username);
                } else if (message.equals("NameTaken")) {
                    username = JOptionPane.showInputDialog(chatWindow, "Enter another username", "Name Taken",
                            JOptionPane.WARNING_MESSAGE);
                    writer.println(username);
                } else if (message.startsWith("NameAccepted")) {
                    textField.setEditable(true);
                    String[] tokens = new String[2];
                    StringTokenizer tokenizer = new StringTokenizer(message, ":");
                    tokens[0] = tokenizer.nextToken();
                    tokens[1] = tokenizer.nextToken();
                    nameLabel.setText(tokens[1]);
                    username = tokens[1];
                } else {
                    chatArea.append(message + "\n");
                }
            }

        } catch (IOException e) {
            System.err.println("Something went wrong reading messages ");
        }

    }

}