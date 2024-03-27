package com.samurayrus.customconverterui.updater;

import java.time.LocalDateTime;

public class UpdaterService {
    private final static String urlForLoadUpdate = "";
    private final static String currentVersion = "0.2";

    public boolean checkNeedToUpdate() {
        if("0.2".equals(currentVersion))
            return false;
        else
            return true;
    }

    public UpdaterVersionInfo loadNewVersionInfo() {
        return new UpdaterVersionInfo("0.3", "Описание обновления", LocalDateTime.now());
    }

    public void update() {

    }

    void createScriptsForUpdate() {

    }

    void reRunAfterUpdate() {

    }

    public String getCurrentVersion(){
        return currentVersion;
    }
}
