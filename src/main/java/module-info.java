module com.samurayrus.customconverterui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires jackson.databind;
    requires jackson.dataformat.csv;
//    requires com.fasterxml.jackson.core;
//    requires com.fasterxml.jackson.databind;
//    requires jackson.databind;
//    exports com.fasterxml.jackson;

    opens com.samurayrus.customconverterui to javafx.fxml;
    exports com.samurayrus.customconverterui;
}