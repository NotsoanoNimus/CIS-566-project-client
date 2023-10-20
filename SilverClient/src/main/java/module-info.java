module xyz.xmit.silverclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens xyz.xmit.silverclient to javafx.fxml;
    exports xyz.xmit.silverclient;
    exports xyz.xmit.silverclient.controllers;
    opens xyz.xmit.silverclient.controllers to javafx.fxml;
}