package com.example.correction_controle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class SceneMM2Controler {
    @FXML
    private TextField PortID;
    @FXML
    private TextField HostID;
    @FXML
    private TextField senderNameID;
    @FXML
    private TextField messageID;
    @FXML
    private ListView<String> TestView;
    PrintWriter pw;

    @FXML
    protected void onConnect() {
        String host = HostID.getText();
        int port;
        try {
            port = Integer.parseInt(PortID.getText());
        } catch (NumberFormatException e) {
            displayErrorMessage("Invalid port number");
            return;
        }

        try {
            Socket socket = new Socket(host, port);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            pw = new PrintWriter(os, true);

            new Thread(() -> {
                while (true) {
                    try {
                        String response = br.readLine();
                        String[] messageParts = response.split("=>");
                        if (messageParts.length == 2) {
                            String sender = messageParts[0]; // Extract the sender information
                            String message = messageParts[1]; // Extract the message content
                            String formattedMessage = "Sender: " + sender + " - Message: " + message; // Create the formatted message
                            Platform.runLater(() -> {
                                TestView.getItems().add(formattedMessage);
                            });
                        } else {
                            Platform.runLater(() -> {
                                TestView.getItems().add(response);
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            displayErrorMessage("Failed to connect to the server");
        }
    }

    @FXML
    public void onSend() {
        String message = messageID.getText();
        pw.println(message);
    }


    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
