module com.example.satellitethreadpool {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens com.example.satellitethreadpool to javafx.fxml;
    exports com.example.satellitethreadpool;
}