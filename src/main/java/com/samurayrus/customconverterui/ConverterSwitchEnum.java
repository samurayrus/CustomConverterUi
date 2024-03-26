package com.samurayrus.customconverterui;

public enum ConverterSwitchEnum {
    JSON("CSV"), CSV("JSON");

    private final String convertTo;

    ConverterSwitchEnum(String strConvertTo) {
        convertTo = strConvertTo;
    }

    public ConverterSwitchEnum getConvertTo(){
        return ConverterSwitchEnum.valueOf(convertTo);
    }
}
