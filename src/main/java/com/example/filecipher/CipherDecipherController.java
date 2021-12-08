package com.example.filecipher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

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
    
    /**

     * This method starts the encryption of a file. Verify that the passwords are considered and that they are not null.
     
     * If the verification is unsuccessful, it displays an alarm.

     * @param event press button "Cifrar" on the screen.

     */
    @FXML
    void cipherBtnPressed(ActionEvent event) {
        if ((cipherPass.getText().equals(cipherPassConfirm.getText()) && !cipherPass.getText().equals(""))) {
            File toEncrypt = new File(cipherFileAddress.getText());
            encryptDecrypt(toEncrypt, cipherPass.getText(), Cipher.ENCRYPT_MODE);
        } else {
            showAlert();
        }
    }

    /**

     * This method starts the decryption of a file. Verify that the passwords are considered and that they are not null

     * If the verification is unsuccessful, it displays an alarm.

     * @param event press button "Decifrar" on the screen.

     */
    @FXML
    void decipherBtnPressed(ActionEvent event) {
        if ((decipherPass.getText().equals(decipherPassConfirmed.getText()) && !decipherPass.getText().equals(""))) {
            File toDecrypt = new File(decipherFileAddress.getText());
            encryptDecrypt(toDecrypt, decipherPass.getText(), Cipher.DECRYPT_MODE);
        } else {
            showAlert();
        }
    }

    /**

     * This method opens the device's file browser to find a file to manipulate.

     * @param event press button "encontrar" on the screen

     */
    @FXML
    void findCipherBtnPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo a cifrar");
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        cipherFileAddress.setText(file.getAbsolutePath());
    }

    /**

     * This method opens the device's file browser to find a file to manipulate.

     * @param event press button "encontrar" on the screen

     */
    @FXML
    void findDecipherBtnPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoja el archivo a descifrar");
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        decipherFileAddress.setText(file.getAbsolutePath());
    }

  
    /**

     * This method displays a pop-up window when any exception of the program is thrown.

     */
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.showAndWait();
        cleanFields();
    }

    /**

     * This method clears the fields of the view.

     */
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

    /**

     * This method is the file encryptor and decrypter, where according to its the way in which it creates a file encrypted.txt or decrypted.txt.

     * @param toEncDec It is the file to be encrypted or encrypted, depending on the mode.
     
     * @param password It is the password that the user chooses to carry out the encryption.
     
     * @param type is the mode that the ENCRYPT_MODE or DECRYPT_MODE method is in.

     */
    private void encryptDecrypt(File toEncDec, String password, int type) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(toEncDec));
            BufferedOutputStream out;
            File toOut = null;
            if (type == Cipher.ENCRYPT_MODE){
                toOut = new File("encrypted.txt");
                calculateWriteSHA1(toOut, toEncDec);
                out = new BufferedOutputStream(new FileOutputStream(toOut, true));
            }else{
                toOut = new File("decrypted.txt");
                out = new BufferedOutputStream(new FileOutputStream(toOut));
                in.readNBytes(20);
            }
            SecretKeyFactory kf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] salt = new byte[20];
            byte[] iv = new byte[16];
            KeySpec ks = new PBEKeySpec(password.toCharArray(), salt, 1000, 128);
            SecretKey key = kf.generateSecret(ks);
            byte[] aesKey = key.getEncoded();
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            cipher.init(type, keySpec, ivspec);
            byte[] inputBuffer = new byte[1];
            int bytesRead = in.read(inputBuffer);
            while (bytesRead > 0) {
                out.write(cipher.update(inputBuffer, 0, bytesRead));
                bytesRead = in.read(inputBuffer);
            }
            out.write(cipher.doFinal());
            in.close();
            Runtime.getRuntime().exec("explorer.exe /select," + toOut.getAbsolutePath());
            out.close();
            if (type == Cipher.DECRYPT_MODE) {
                boolean result = calculateCompareSHA1(toEncDec, toOut);
                if(!result) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Los hashes no corresponden.");
                    alert.showAndWait();
                    cleanFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación");
                    alert.setHeaderText("Confirmación");
                    alert.setContentText("Los hashes corresponden.");
                    alert.showAndWait();
                    cleanFields();
                }
            }
            cleanFields();
        }catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No se encontró el archivo.");
            alert.showAndWait();
            cleanFields();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                InvalidKeyException | IOException | BadPaddingException | IllegalBlockSizeException |
                InvalidKeySpecException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            cleanFields();
            e.printStackTrace();
        }
    }

    /**

     * This method receives a file to calculate its hash and write it to a new file.

     * @param toOut is the blank encrypted.txt file to pass through the SHA-1 hash.
     
     * @param input is the original file to calculate the hash.

     */
    private void calculateWriteSHA1 (File toOut, File input) throws NoSuchAlgorithmException, IOException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(input));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(toOut));
        sha1.update(in.readAllBytes());
        out.write(sha1.digest());
        in.close();
        out.close();
    }

    /**

     * This method so that the hashed key of the encrypted file and the supplied key match.

     * @param encrypted It is the encrypted file.
     
     * @param decrypted It is the key to compare.

     */
    private boolean calculateCompareSHA1 (File encrypted, File decrypted) throws NoSuchAlgorithmException, IOException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(encrypted));
        BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(decrypted));
        byte[] original = in.readNBytes(20);
        sha1.update(in2.readAllBytes());
        in.close();
        in2.close();
        byte[] result = sha1.digest();
        boolean equals = true;
        for (int i = 0; i < result.length; i++) {
            if(result[i] != original[i]) {
                equals = false;
            }
        }
        return equals;
    }

}
