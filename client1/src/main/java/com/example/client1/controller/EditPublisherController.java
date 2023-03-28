package com.example.client1.controller;

import com.example.client1.entity.PublisherEntity;
import com.example.client1.response.PublisherResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.client1.controller.ApplicationController.*;
import static com.example.client1.controller.ApplicationController.gson;

public class EditPublisherController {
    @FXML
    private TextField publisherName;

    @FXML
    private TextField publisherCity;

    private Stage editPublisherStage;
    private PublisherEntity publisher;
    private int publisherId;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.editPublisherStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setLabels(PublisherEntity publisherIn, int publisherId) {
        this.publisher = publisherIn;
        this.publisherId = publisherId;

        publisherName.setText(publisher.getPublisher());
        publisherCity.setText(publisher.getCity());
    }

    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            publisher.setPublisher(publisherName.getText());
            publisher.setCity(publisherCity.getText());

            okClicked = true;
            editPublisherStage.close();
            publishersData.set(publisherId, publisher);
            updatePublisher(publisher);
        }
    }

    @FXML
    private void handleCancel() {
        editPublisherStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (publisherName.getText() == null || publisherName.getText().length() == 0)
            errorMessage = "Не обнаружено наименование издательства!\n";
        if (publisherCity.getText() == null || publisherCity.getText().length() == 0)
            errorMessage = "Не обнаружен город!\n";

        if (errorMessage.length() == 0) return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editPublisherStage);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, укажите корректные значения текстовых полей");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public static void updatePublisher(PublisherEntity author) throws IOException {
        http.post("http://localhost:2825/api/v1/publisher/update", gson.toJson(author));
    }
}
