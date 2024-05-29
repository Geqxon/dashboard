module org.example.dachboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;


    opens org.example.dachboard to javafx.fxml;
    exports org.example.dachboard;
}