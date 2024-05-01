module com.example.piiaproject {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.piiaproject;
    opens com.example.piiaproject to javafx.fxml;


}