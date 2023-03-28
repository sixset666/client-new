package com.example.client1.controller;

import java.io.IOException;
import java.util.Objects;

import com.example.client1.entity.AuthorEntity;
import com.example.client1.entity.BookEntity;
import com.example.client1.entity.PublisherEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.example.client1.controller.ApplicationController.*;

public class EditBookController {
    @FXML
    private ComboBox<AuthorEntity> authorList;

    @FXML
    private TextField bookChapter;

    @FXML
    private TextField bookName;

    @FXML
    private ComboBox<PublisherEntity> publisherList;

    @FXML
    private TextField bookYear;

    private Stage editBookStage;
    private BookEntity book;
    private int bookId;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.editBookStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void initialize() {
        authorList.setItems(FXCollections.observableArrayList(authorsData));
        authorList.setValue(authorsData.get(0));
        publisherList.setItems(FXCollections.observableArrayList(publishersData));
        publisherList.setValue(publishersData.get(0));
    }

    public void setLabels(BookEntity bookIn, int book_id) {
        this.book = bookIn;
        this.bookId = book_id;

        bookName.setText(book.getTitle());
        bookYear.setText(book.getYear() + "");
        bookChapter.setText(book.getKind());
    }

    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            book.setTitle(bookName.getText());
            book.setAuthor(authorList.getValue());
            book.setPublisher(publisherList.getValue());
            book.setYear(Integer.parseInt(bookYear.getText()));
            book.setKind(bookChapter.getText());

            okClicked = true;
            editBookStage.close();
            booksData.set(bookId, book);
            updateBook(book);
        }
    }

    @FXML
    private void handleCancel() {
        editBookStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (bookChapter.getText() == null || bookChapter.getText().length() == 0) errorMessage = "Не обнаружен жанр книги!\n";
        if (bookName.getText() == null || bookName.getText().length() == 0) errorMessage = "Не обнаружено название книги!\n";
        if (bookYear == null || bookYear.getText().length() == 0 || Objects.equals(bookYear.getText(), "0")) errorMessage = "Не обнаружен год выпуска книги!\n";
        else {
            try {
                Integer.parseInt(bookYear.getText());
            } catch (NumberFormatException e) {
                errorMessage = "Некорректное значение года выпуска книги (должно быть целочисленное)\n";
            }
        }

        if (errorMessage.length() == 0) return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editBookStage);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, укажите корректные значения текстовых полей");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public static void updateBook(BookEntity book) throws IOException {
        http.put(api + "update", gson.toJson(book).toString());
    }
}