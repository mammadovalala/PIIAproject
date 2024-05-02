module com.example.piiaproject {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.piiaproject to javafx.fxml;
    opens com.example.piiaproject.Model to javafx.fxml;
    exports com.example.piiaproject.View;
    opens com.example.piiaproject.Controller to javafx.fxml;
}