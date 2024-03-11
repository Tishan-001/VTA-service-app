package com.vta.app.enums;

public enum GlobalConfigs {
    EMAIL_HOST("EMAIL_HOST", "smtp.gmail.com"),
    EMAIL_USERNAME("EMAIL_USERNAME", "virtualtravelassistance@gmail.com"),
    EMAIL_PASSWORD("EMAIL_PASSWORD", "zywi momo vvvr kwty"),
    EMAIL_PORT("EMAIL_PORT", "587"),
    ;

    private String configName;
    private String defaultValue;

    GlobalConfigs(String configName) {
        this.configName = configName;
        this.defaultValue = "";
    }

    GlobalConfigs(String configName, String defaultValue) {
        this(configName);
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}
