package es.source.code.activity.Bean;

import java.io.Serializable;

public class OrderFood implements Serializable {

    Food food;
    int data;

    public OrderFood(Food food, int data) {
        this.food = food;
        this.data = data;
    }

    public OrderFood(Food food) {
        this.food = food;
        this.data = 1;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}