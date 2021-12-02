package com.example.filecipher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class CipherDecipherController {

    @FXML
    private TextField cipherFileAddress;

    @FXML
    private PasswordField cipherPass;

    @FXML
    private PasswordField cipherPassConfirm;

    @FXML
    private TextField decipherFileAddress;

    @FXML
    private PasswordField decipherPass;

    @FXML
    private PasswordField decipherPassConfirmed;

    @FXML
    void cipherBtnPressed(ActionEvent event) {
        if (cipherPass.getText().equals(cipherPassConfirm.getText())) {
            // TODO()
        } else {
            showAlert();
        }
    }

    @FXML
    void decipherBtnPressed(ActionEvent event) {
        if (decipherPass.getText().equals(decipherPassConfirmed.getText())) {
            // TODO()
        } else {
            showAlert();
        }
    }

    @FXML
    void findCipherBtnPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo a cifrar");
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        cipherFileAddress.setText(file.getAbsolutePath());
    }

    @FXML
    void findDecipherBtnPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo a descifrar");
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        decipherFileAddress.setText(file.getAbsolutePath());
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.showAndWait();
        cleanFields();
    }

    private void cleanFields() {

        // Cifrar
        cipherFileAddress.setText("");
        cipherPass.setText("");
        cipherPassConfirm.setText("");

        // Descrifrar
        decipherFileAddress.setText("");
        decipherPass.setText("");
        decipherPassConfirmed.setText("");
    }

}
