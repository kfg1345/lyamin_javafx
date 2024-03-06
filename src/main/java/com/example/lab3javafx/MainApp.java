package com.example.lab3javafx;


import com.example.lab3javafx.controllers.OrderEditDialogController;
import com.example.lab3javafx.controllers.OrderOverviewController;
import com.example.lab3javafx.controllers.RootLayoutController;
import com.example.lab3javafx.model.Order;
import com.example.lab3javafx.model.OrderListWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Order> orderData = FXCollections.observableArrayList();

    public MainApp() {
        orderData.add(new Order("235564", "TCSG"));
        orderData.add(new Order("658343", "FIVE"));
        orderData.add(new Order("123532", "GAZP"));
        orderData.add(new Order("768527", "LKOH"));
        orderData.add(new Order("679454", "CHMF"));
        orderData.add(new Order("345261", "YNDX"));
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("OrdersApp");

        this.primaryStage.getIcons().add(new Image("file:src/main/resources/images/pentosquareicon.png"));
        initRootLayout();

        showOrderOverview();
    }
    public ObservableList<Order> getOrderData() {
        return orderData;
    }

    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getOrderFilePath();
        if (file != null) {
            loadOrderDataFromFile(file);
        }
    }

    public void showOrderOverview() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrdersOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            OrderOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showOrderEditDialog(Order order) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Order");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            OrderEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOrder(order);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getOrderFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setOrderFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("OrdersApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("OrdersApp");
        }
    }

    public void loadOrderDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(OrderListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            OrderListWrapper wrapper = (OrderListWrapper) um.unmarshal(file);

            orderData.clear();
            orderData.addAll(wrapper.getOrders());

            setOrderFilePath(file);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось сохранить данные");
            alert.setContentText("Не удалось сохранить данные в файл:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveOrderDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(OrderListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            OrderListWrapper wrapper = new OrderListWrapper();
            wrapper.setOrders(orderData);

            m.marshal(wrapper, file);

            setOrderFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось сохранить данные");
            alert.setContentText("Не удалось сохранить данные в файл:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
