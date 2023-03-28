package com.example.client1.controller;

import com.example.client1.entity.AuthorEntity;
import com.example.client1.entity.BookEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.client1.controller.ApplicationController.*;

public class EditAuthorController {

    @FXML
    private TextField authorLastName;

    @FXML
    private TextField authorName;

    @FXML
    private TextField authorSurname;

    private Stage editAuthorStage;
    private AuthorEntity author;
    private int authorId;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.editAuthorStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setLabels(AuthorEntity authorIn, int authorId) {
        this.author = authorIn;
        this.authorId = authorId;

        authorName.setText(author.getName());
        authorLastName.setText(author.getLastname());
        authorSurname.setText(author.getSurname());
    }

    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            author.setName(authorName.getText());
            author.setSurname(authorSurname.getText());
            author.setLastname(authorLastName.getText());

            okClicked = true;
            editAuthorStage.close();
            authorsData.set(authorId, author);
            updateAuthor(author);
        }
    }

    @FXML
    private void handleCancel() {
        editAuthorStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (authorName.getText() == null || authorName.getText().length() == 0) errorMessage = "Не обнаружено имя автора!\n";
        if (authorLastName.getText() == null || authorLastName.getText().length() == 0) errorMessage = "Не обнаружено отчество автора!\n";
        if (authorSurname.getText() == null || authorSurname.getText().length() == 0) errorMessage = "Не обнаружена фамилия автора!\n";

        if (errorMessage.length() == 0) return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editAuthorStage);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, укажите корректные значения текстовых полей");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public static void updateAuthor(AuthorEntity author) throws IOException {
        http.post("http://localhost:2825/api/v1/author/update", gson.toJson(author).toString());
    }
}
