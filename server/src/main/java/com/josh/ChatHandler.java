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
    Thread userThread;

    FileWriter fw;
    BufferedWriter bw;
    PrintWriter pw;

    public ChatHandler(Socket socket) {
        this.socket = socket;

        try {
            fw = new FileWriter("C:/Users/joshr/vs_code_workspace/chatapp/LogTest.csv", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw, true);
        } catch (IOException e) {
            System.err.println("Could not set up logging writers");
        }
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
                            map.getValue().println(username + ": " + message);
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

            }

        } catch (Exception e) {
            System.err.println("Could not run the chat handler");
        }
    }
}
