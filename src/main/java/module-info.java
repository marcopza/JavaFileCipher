module com.example.filecipher {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.filecipher to javafx.fxml;
    exports com.example.filecipher;
}