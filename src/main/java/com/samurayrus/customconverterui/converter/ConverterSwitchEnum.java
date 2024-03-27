package com.samurayrus.customconverterui.converter;

public enum ConverterSwitchEnum {
    JSON("CSV"), CSV("JSON"), TEST("TEST");

    private final String convertTo;

    ConverterSwitchEnum(String strConvertTo) {
        convertTo = strConvertTo;
    }

    public ConverterSwitchEnum getConvertTo(){
        return ConverterSwitchEnum.valueOf(convertTo);
    }
}
