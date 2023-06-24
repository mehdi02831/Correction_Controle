package com.example.correction_controle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
public class SceneMM1Controler {
    @FXML
    private TextField usernameID;
    @FXML
    private PasswordField passwordID;

    @FXML
    protected void onLoginMM() throws IOException {
        if (usernameID.getText().equals("admin") && passwordID.getText().equals("admin")) {
// savoir le stage
            Stage s = (Stage) usernameID.getScene().getWindow();
// Récuperer le fichier fxel de la 2 eme scene
            FXMLLoader fx = new FXMLLoader(MainApp.class.getResource("SceneMM2.fxml"));
            Scene sc2 = new Scene(fx.load());
// attacher  scene au stage
            s.setScene(sc2);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentification Error");
            alert.setHeaderText("Username or password are not validated !");
            alert.setContentText("Retry by changing the authentification information!");
            alert.show();
        }
    }

}