package com.example.lab3javafx.controllers;

import com.example.lab3javafx.model.Order;
import com.example.lab3javafx.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderEditDialogController {

    @FXML
    private TextField orderTypeField;
    @FXML
    private TextField orderVerietyField;
    @FXML
    private TextField currencyField;
    @FXML
    private TextField tikerField;
    @FXML
    private TextField countField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField dataField;
    @FXML
    private TextField durationField;


    private Stage dialogStage;
    private Order order;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setOrder(Order order) {
        this.order = order;

        orderTypeField.setText(order.getOrderType());
        orderVerietyField.setText(order.getOrderVeriety());
        currencyField.setText(order.getCurrencyFull());
        tikerField.setText(order.getTiker());
        countField.setText(order.getCount());
        numberField.setText(order.getNumber());

        dataField.setText(DateUtil.format(order.getData()));
        dataField.setPromptText("dd.mm.yyyy");

        durationField.setText(DateUtil.format(order.getDuration()));
        durationField.setPromptText("dd.mm.yyyy");
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            order.setOrderType(orderTypeField.getText());
            order.setOrderVeriety(orderVerietyField.getText());
            order.setCurrencyFull(currencyField.getText());
            order.setTiker(tikerField.getText());
            order.setCount(countField.getText());
            order.setNumber(numberField.getText());
            order.setData(DateUtil.parse(dataField.getText()));
            order.setDuration(DateUtil.parse(durationField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (orderTypeField.getText() == null || orderTypeField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        }
        if (orderVerietyField.getText() == null || orderVerietyField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        }
        if (currencyField.getText() == null || currencyField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        }

        if (tikerField.getText() == null || tikerField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        }

        if (countField.getText() == null || countField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        }

        if (numberField.getText() == null || numberField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        }

        if (dataField.getText() == null || dataField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        } else {
            if (!DateUtil.validDate(dataField.getText())) {
                errorMessage += "No valid. Use the format dd.mm.yyyy!\n";
            }
        }

        if (durationField.getText() == null || durationField.getText().length() == 0) {
            errorMessage += "No valid!\n";
        } else {
            if (!DateUtil.validDate(durationField.getText())) {
                errorMessage += "No valid. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
