package com.samurayrus.customconverterui.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConverterService {


    public void convertFromJsonToCSV(String fromPath, String toPath, boolean customSeparator) throws IOException {
        String escapedJson = String.join("", Files.readAllLines(Paths.get(fromPath), StandardCharsets.UTF_8));
        escapedJson = escapedJson.replace("\\u", "\\\\u");

        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        JsonNode jsonTree = mapper.readTree(escapedJson);

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        //Берем заголовки только у первого объекта, чтобы создать столбцы
        JsonNode firstObject = jsonTree.elements().next();

        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);

        char charSeparetor;
        if (customSeparator)
            charSeparetor = ';';
        else
            charSeparetor = ',';

        CsvSchema csvSchema = csvSchemaBuilder.build()
                .withColumnSeparator(charSeparetor)
                .withHeader();

        //Преобразуем Json в csv файл с использованием ранее заданных заголовков
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.configure(CsvGenerator.Feature.STRICT_CHECK_FOR_QUOTING, true);
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new OutputStreamWriter(new FileOutputStream(toPath), StandardCharsets.UTF_8), jsonTree);

// Тестировал экранирование
//        String escapedJson = StringEscapeUtils.escapeJson(String.join("", Files.readAllLines(Paths.get(fromPath), StandardCharsets.UTF_8)));
//        System.out.println("SADSD");
//        System.out.println(escapedJson);
//        String asd= String.join("", Files.readAllLines(Paths.get(fromPath), StandardCharsets.UTF_8));
//        System.out.println(asd);
//        asd = asd.replace("\\", "\\\\");
//        System.out.println(asd);
//        test(asd);
////        CSVWriter csvWriter = new CSVWriter(new FileWriter(fromPath));
////        csvWriter.writeAll(jsonTree.);
    }

    public String mapExcelFileToJson(final String excelFilePath, final String toPath) {
        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(excelFilePath)).withCSVParser(rfc4180Parser).withSkipLines(1).build()) {
            final List<String[]> r = reader.readAll();
            List<String> mappedResultForFirstColumn = new ArrayList<>();
            for (String[] array : r) {
                mappedResultForFirstColumn.add("\"" + array[0] + "\"");
            }
            return "[" + String.join(",", mappedResultForFirstColumn) + "]";
        } catch (Exception e) {
            return "Map CSV Exception: " + e.getMessage();
        }
    }
}
