package com.josh;

import java.awt.event.*;

public class Listener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Client.writer.println(Client.textField.getText());
        Client.textField.setText("");

    }
}