package com.samurayrus.customconverterui.updater;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class UpdaterUiController {
    @FXML
    public TextArea infoVersionTextArea;
    @FXML
    public Button updateButton;

    private final UpdaterService updaterService;
    public Label labelVersions;

    public UpdaterUiController() {
        updaterService = new UpdaterService();
    }

    @FXML
    public void initialize() {
//        if(updaterService.checkNeedToUpdate()) {
            UpdaterVersionInfo versionInfo = updaterService.loadNewVersionInfo();
            infoVersionTextArea.setText(versionInfo.toString());
//        }
        labelVersions.setText(updaterService.getCurrentVersion() + "=>" + "0.3");
    }

    @FXML
    public void onUpdateButtonClick(ActionEvent actionEvent) {
    }
}
