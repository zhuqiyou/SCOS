package es.source.code.activity.Bean;

import java.io.Serializable;

public class Food implements Serializable {
    private String FoodName;
    private double FoodPrice;
    private int img;
    private int number;

    public Food(String FoodName,double FoodPrice){
        this.FoodName = FoodName;
        this.FoodPrice = FoodPrice;
        img = 0;
    }
    public Food(int img,String FoodName,double FoodPrice,int number){
        this.FoodName = FoodName;
        this.FoodPrice = FoodPrice;
        this.img=img;
        this.number = number;
    }
//    public Food(String FoodName,double FoodPrice,int number){
//        this.FoodName = FoodName;
//        this.FoodPrice = FoodPrice;
//        this.number=number;
//
//    }
    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public double getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        FoodPrice = foodPrice;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}