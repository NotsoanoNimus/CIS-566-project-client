module xyz.xmit.silverclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;

    opens xyz.xmit.silverclient to javafx.fxml;
    exports xyz.xmit.silverclient;
    exports xyz.xmit.silverclient.api;
    exports xyz.xmit.silverclient.api.request;
    exports xyz.xmit.silverclient.models;
    exports xyz.xmit.silverclient.ui.controllers;
    exports xyz.xmit.silverclient.ui.statemachine;
    opens xyz.xmit.silverclient.ui.controllers to javafx.fxml;
    opens xyz.xmit.silverclient.api to com.fasterxml.jackson.databind;
    exports xyz.xmit.silverclient.utilities;
    opens xyz.xmit.silverclient.utilities to javafx.fxml;
}