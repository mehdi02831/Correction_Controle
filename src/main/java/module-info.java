module com.example.correction_controle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.correction_controle to javafx.fxml;
    exports com.example.correction_controle;
}