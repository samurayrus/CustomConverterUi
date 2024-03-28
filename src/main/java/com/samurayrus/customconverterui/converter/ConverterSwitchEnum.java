package com.samurayrus.customconverterui.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ConverterSwitchEnum {
    JSON(Arrays.asList("CSV", "CSV_EXCEL"), "json"),
    CSV(Arrays.asList("JSON"), "csv"),
    TEST(Arrays.asList("TEST"), "test"),
    CSV_EXCEL(Arrays.asList(""), "csv");

    private final List<String> convertTo;
    private final String extension;

    ConverterSwitchEnum(List<String> strConvertTo, String extension) {
        convertTo = new ArrayList<>(strConvertTo);
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public List<ConverterSwitchEnum> getConvertTo(){
        return convertTo.stream().map(ConverterSwitchEnum::valueOf).collect(Collectors.toList());
    }

    public boolean canConvertTo(ConverterSwitchEnum convertFrom){
        return getConvertTo().contains(convertFrom);
    }
}
