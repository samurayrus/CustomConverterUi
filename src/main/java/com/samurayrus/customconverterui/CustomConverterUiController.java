package com.samurayrus.customconverterui;

import com.samurayrus.customconverterui.converter.ConverterService;
import com.samurayrus.customconverterui.converter.ConverterSwitchEnum;
import com.samurayrus.customconverterui.updater.UpdaterUiController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

public class CustomConverterUiController {

    @FXML
    AnchorPane anchorPaneUi;
    @FXML
    public Button convertButton;

    @FXML
    public TextArea infoTextArea;

    @FXML
    private ListView<String> convertFromList;
    private final ObservableList<String> convertFromCellContent = FXCollections.observableArrayList();

    @FXML
    private ListView<String> convertToList;
    private final ObservableList<String> convertToCellContent = FXCollections.observableArrayList();

    private ConverterService converterService;

    private String filePathForConvert = "";

    public void setFilePathForConvert(String newPath) {
        filePathForConvert = newPath;
    }

    public CustomConverterUiController() {
        converterService = new ConverterService();
    }

    @FXML
    public void initialize() throws IOException {
        convertFromList.setItems(convertFromCellContent);
        convertToList.setItems(convertToCellContent);
        //----
        //FROM
        convertFromCellContent.add("JSON");
        convertFromCellContent.add("TEST");
        //TO
        convertToCellContent.add("CSV");
        convertToCellContent.add("TEST");
        //----

        convertFromList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                String selectedText = convertFromList.getSelectionModel().getSelectedItem();
                if (selectedText.equals("JSON")) {
                    //TODO: Здесь будут скрываться или раскрываться элементы в противоположном списке, чтобы пользователь понимал во что он может конвертировать
                }
            }
        });

        convertToList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                String selectedText = convertToList.getSelectionModel().getSelectedItem();
                if (selectedText.equals("CSV")) {
                    //TODO: Здесь будут скрываться или раскрываться элементы в противоположном списке, чтобы пользователь понимал во что он может конвертировать
                }
            }
        });

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("updater-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 435, 317);
        stage.setTitle("Updater");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onDragDropped(DragEvent dragEvent) throws IOException {
        if (dragEvent.getDragboard().hasFiles()) {
            setFilePathForConvert(dragEvent.getDragboard().getFiles().get(0).getPath());
            infoTextArea.setText("Set File: \n" + filePathForConvert);
        }
    }

    @FXML
    public void onDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            //TODO: доработать смену цвета или показывать пикчу
//            ColorInput colorInput = new ColorInput();
//            colorInput.setPaint(Color.STEELBLUE);
//            convertButton.setEffect(colorInput);
        } else {
            dragEvent.consume();
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        String convertFromString = convertFromList.getSelectionModel().getSelectedItem();
        String convertToString = convertToList.getSelectionModel().getSelectedItem();

        if (convertFromString == null || convertToString == null) {
            infoTextArea.setText("ConvertTo and ConvertFrom not choice!");
        }

        ConverterSwitchEnum convertFrom = ConverterSwitchEnum.valueOf(convertFromString);
        ConverterSwitchEnum convertTo = ConverterSwitchEnum.valueOf(convertToString);

        if (convertFrom.getConvertTo().equals(convertTo)) {
            if (convertFrom == ConverterSwitchEnum.TEST) {
                infoTextArea.setText("TEST=>TEST! Test complete!");
                return;
            }

            try {
                String newFileName = convertFromString + "_" + convertToString + "_" + LocalDateTime.now().getNano() + "." + convertToString.toLowerCase(Locale.ROOT);
                converterService.convertFromJsonToCSV(filePathForConvert, newFileName);
                infoTextArea.setText("Converted! New file has been created in the CustomConverterUi directory: " + newFileName);
            } catch (Exception e) {
                infoTextArea.setText("Error with convert this file : \n" + e.getMessage());
            }
        } else {
            infoTextArea.setText("Error! Wrong choice");
        }

    }

    @FXML
    public void onAboutButtonClick(ActionEvent actionEvent) {
        infoTextArea.setText(
                "Привет! Это приложение находится на стадии разработки." +
                        " Пока поддерживается конвертирование только из JSON в CSV по просьбам трудящихся." +
                        " В будущем будет добавлена функция автообновления. " +
                        "SamurayRus" +
                        "GitHub for updates: https://github.com/samurayrus/CustomConverterUi"
        );
    }
}