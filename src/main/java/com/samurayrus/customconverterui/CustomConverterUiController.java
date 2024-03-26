package com.samurayrus.customconverterui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class CustomConverterUiController extends Parent {
    @FXML
    public ListView<String> convertFromList;
    public final ObservableList<String> convertFromCellContent = FXCollections.observableArrayList();

    @FXML
    public ListView<String> convertToList;
    public final ObservableList<String> convertToCellContent = FXCollections.observableArrayList();

    @FXML
    public Label welcomeText;


    @FXML
    public void initialize() {
        convertFromList.setItems(convertFromCellContent);
        convertToList.setItems(convertToCellContent);
        //----
        //FROM
        convertFromCellContent.add("JSON");
        convertFromCellContent.add("JSON1");
        //TO
        convertToCellContent.add("CSV");
        convertToCellContent.add("JSON");
        //----


        convertFromList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                String selectedText = convertFromList.getSelectionModel().getSelectedItem();
                if (selectedText.equals("json")) {
                    //
                }
            }
        });

        convertToList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                String selectedText = convertToList.getSelectionModel().getSelectedItem();
                if (selectedText.equals("csv")) {
                    //
                }
            }
        });
    }

    @FXML
    protected void onHelloButtonClick() {
        String s1 = convertFromList.getSelectionModel().getSelectedItem();
        String s2 = convertToList.getSelectionModel().getSelectedItem();
        ConverterSwitchEnum.valueOf("JSON");
        ConverterSwitchEnum convertFrom = ConverterSwitchEnum.valueOf(convertFromList.getSelectionModel().getSelectedItem());
        ConverterSwitchEnum convertTo = ConverterSwitchEnum.valueOf(convertToList.getSelectionModel().getSelectedItem());

        if(convertFrom.getConvertTo().equals(convertTo)){
            welcomeText.setText("Converted!");
        } else {
            welcomeText.setText("Error! Wrong choose");
        }

    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}