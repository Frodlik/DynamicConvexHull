module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
}