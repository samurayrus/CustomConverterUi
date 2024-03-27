package com.samurayrus.customconverterui.updater;

import java.time.LocalDateTime;

public class UpdaterVersionInfo {
    private String version;
    private String title;
    private LocalDateTime updateDateTime;

    public UpdaterVersionInfo(String version, String title, LocalDateTime updateDateTime) {
        this.version = version;
        this.title = title;
        this.updateDateTime = updateDateTime;
    }

    public String getVersion() {
        return version;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    @Override
    public String toString() {
        return "New version available!" +
                "Version: " + version + "\n" +
                "About: " + title + "\n" +
                "Update release: " + updateDateTime
                ;
    }
}
