package com.example.lab3javafx.controllers;

import com.example.lab3javafx.MainApp;
import com.example.lab3javafx.model.Order;
import com.example.lab3javafx.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrderOverviewController {
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, String> numberColumn;
    @FXML
    private TableColumn<Order, String> tikerColumn;

    @FXML
    private Label orderTypeLabel;
    @FXML
    private Label orderVerietyLabel;
    @FXML
    private Label currencyFullLabel;
    @FXML
    private Label tikerLabel;
    @FXML
    private Label countLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label dataLabel;
    @FXML
    private Label durationLabel;

    // Ссылка на главное приложение.
    private MainApp mainApp;

    public OrderOverviewController() {
    }

    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами.
        numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        tikerColumn.setCellValueFactory(cellData -> cellData.getValue().tikerProperty());

        showOrderDetails(null);

        orderTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        orderTable.setItems(mainApp.getOrderData());
    }

    private void showOrderDetails(Order order) {
        if (order != null) {
            orderTypeLabel.setText(order.getOrderType());
            orderVerietyLabel.setText(order.getOrderVeriety());
            currencyFullLabel.setText(order.getCurrencyFull());
            tikerLabel.setText(order.getTiker());
            countLabel.setText(order.getCount());
            numberLabel.setText(order.getNumber());
            dataLabel.setText(DateUtil.format(order.getData()));
            durationLabel.setText(DateUtil.format(order.getDuration()));
        } else {
            orderTypeLabel.setText("---");
            orderVerietyLabel.setText("---");
            currencyFullLabel.setText("---");
            tikerLabel.setText("---");
            countLabel.setText("---");
            numberLabel.setText("---");
            dataLabel.setText("---");
            durationLabel.setText("---");
        }
    }

    @FXML
    private void handledDeleteOrder() {
        int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            orderTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано!");
            alert.setHeaderText("Не выбран объект");
            alert.setContentText("Пожалуйста, выберите что-нибудь.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewOrder() {
        Order tempOrder = new Order();
        boolean okClicked = mainApp.showOrderEditDialog(tempOrder);
        if (okClicked) {
            mainApp.getOrderData().add(tempOrder);
        }
    }

    @FXML
    private void handleEditOrder() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            boolean okClicked = mainApp.showOrderEditDialog(selectedOrder);
            if (okClicked) {
                showOrderDetails(selectedOrder);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Заказ не выбран");
            alert.setContentText("Пожалуйста, выберите заказ в таблице.");

            alert.showAndWait();
        }
    }
}
