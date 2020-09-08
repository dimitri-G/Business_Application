package com.example.ClassBean;

public class Food {
    private String foodId;
    private String foodName;
    private float foodPrice;
    private int imageId;
    private String foodType;
    private String foodConstruct;

    public Food(){}

    public Food(String _foodID,String _foodName, float _foodPrice,int _imageId){
        this.foodName = _foodName;
        this.foodId=_foodID;
        this.foodPrice = _foodPrice;
        this.imageId = _imageId;

    }
    public int getImageId() {
        return imageId;
    }

    public String getFoodConstruct() {
        return foodConstruct;
    }
    public String getFoodType() {
        return foodType;
    }

    public void setFoodConstruct(String foodConstruct) {
        this.foodConstruct = foodConstruct;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }
public String getFoodId(){
        return foodId;
}
    public String getFoodName() {
        return foodName;
    }

    public  void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }
}
