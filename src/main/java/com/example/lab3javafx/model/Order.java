package com.example.lab3javafx.model;

import com.example.lab3javafx.util.LocalDateAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class Order {
    private final StringProperty orderType;
    private final StringProperty orderVeriety;
    private final StringProperty currencyFull;
    private final StringProperty tiker;
    private final StringProperty count;
    private final StringProperty number;
    private final ObjectProperty<LocalDate> data;
    private final ObjectProperty<LocalDate> duration;


    public Order() {
        this(null, null);
    }

    public Order(String number, String tiker) {
        this.number = new SimpleStringProperty(number);
        this.tiker = new SimpleStringProperty(tiker);

        // Какие-то фиктивные начальные данные для удобства тестирования.
        this.orderType = new SimpleStringProperty("тип");
        this.orderVeriety = new SimpleStringProperty("вид");
        this.currencyFull = new SimpleStringProperty("валюта полное");
        this.count = new SimpleStringProperty("0");

        this.data = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.duration = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 3, 21));
    }

    public String getOrderType() {
        return orderType.get();
    }

    public StringProperty orderTypeProperty() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType.set(orderType);
    }

    public String getOrderVeriety() {
        return orderVeriety.get();
    }

    public StringProperty orderVerietyProperty() {
        return orderVeriety;
    }

    public void setOrderVeriety(String orderVeriety) {
        this.orderVeriety.set(orderVeriety);
    }

    public String getCurrencyFull() {
        return currencyFull.get();
    }

    public StringProperty currencyFullProperty() {
        return currencyFull;
    }

    public void setCurrencyFull(String currencyFull) {
        this.currencyFull.set(currencyFull);
    }

    public String getTiker() {
        return tiker.get();
    }

    public StringProperty tikerProperty() {
        return tiker;
    }

    public void setTiker(String tiker) {
        this.tiker.set(tiker);
    }
    public String getCount() {
        return count.get();
    }

    public StringProperty countProperty() {
        return count;
    }

    public void setCount(String count) {
        this.count.set(count);
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getData() {
        return data.get();
    }

    public ObjectProperty<LocalDate> dataProperty() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data.set(data);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDuration() {
        return duration.get();
    }

    public ObjectProperty<LocalDate> durationProperty() {
        return duration;
    }

    public void setDuration(LocalDate duration) {
        this.duration.set(duration);
    }
}
