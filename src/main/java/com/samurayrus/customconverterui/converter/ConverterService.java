package com.samurayrus.customconverterui.converter;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

public class ConverterService {


    public void convertFromJsonToCSV(String fromPath, String toPath) throws IOException {
        JsonNode jsonTree = new ObjectMapper().readTree(new File(fromPath));

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        //Берем заголовки только у первого объекта, чтобы создать столбцы
        JsonNode firstObject = jsonTree.elements().next();

        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        //Преобразуем Json в csv файл с использованием ранее заданных заголовков
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File(toPath), jsonTree);
    }
}
