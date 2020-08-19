package com.josh;

import java.net.*;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.*;

public class ChatHandler implements Runnable {
    Socket socket;
    BufferedReader dataIn;
    PrintWriter dataOut;
    String username;

    FileWriter fw;
    BufferedWriter bw;
    PrintWriter pw;

    /**
     * Constructor 
     * Creates a filewriter, bufferedwriter, and printwriter to add chat history to a csv file
     * @param socket
     * @author Josh Robitaille
     */
    public ChatHandler(Socket socket) {
        this.socket = socket;

        try {
            fw = new FileWriter("C:/Users/joshr/vs_code_workspace/chatapp/ChatLog.csv", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw, true);
        } catch (IOException e) {
            System.err.println("Could not set up logging writers");
        }
    }

    /**
     * Checks for a username, sends out a string to the client depending on the username input
     * Checks if the username is taken already
     * Then looks for messages from the user and sends it out to other users or a single user if dm'ed
     * @author Josh Robitaille
     */
    @Override
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

                if (!Server.users.containsKey(username)) {
                    Server.users.put(username, dataOut);
                    break;
                } else {
                    usernameTaken = true;
                }
            }
            dataOut.println("NameAccepted:" + username);

            while (true) {

                String message = dataIn.readLine();
                if (message == null) {
                    return;
                }

                if (message.contains("@")) {
                    String msg, receiver;
                    StringTokenizer messageTokens = new StringTokenizer(message, "@");
                    msg = messageTokens.nextToken();
                    receiver = messageTokens.nextToken();

                    for (HashMap.Entry<String, PrintWriter> map : Server.users.entrySet()) {
                        if (map.getKey().equals(receiver)) {
                            map.getValue().println("@" + username + ": " + msg);
                        } else {
                            continue;
                        }
                    }

                } else {
                    for (HashMap.Entry<String, PrintWriter> map : Server.users.entrySet()) {
                        map.getValue().println(username + ": " + message);
                    }
                }
                pw.println(username + ": " + message);
                // System.out.println("Starting db thread");
                Thread addData = new Thread(new DataAccess(username, message));
                addData.start();
            }

        } catch (Exception e) {
            System.err.println("Could not run the chat handler");
        }
    }
}
