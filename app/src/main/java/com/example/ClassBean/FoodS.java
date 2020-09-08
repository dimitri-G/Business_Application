package com.example.ClassBean;

import com.example.businessapplication.R;

public class FoodS {
    String foodDetailName;
    String foodDetailPrice;
    String foodDetailType;
    String foodDetaolCon;
    String foodN;

    public FoodS(String a,String b,String c,String d,String e){
        foodDetailName=a;
        foodDetailPrice=b;
        foodDetailType=c;
        foodDetaolCon=d;
        foodN=e;
    }

    public String getFoodDetailName() {
        return foodDetailName;
    }

    public String getFoodDetailPrice() {
        return foodDetailPrice;
    }

    public String getFoodDetailType() {
        return foodDetailType;
    }

    public String getFoodDetaolCon() {
        return foodDetaolCon;
    }

    public String getFoodN() {
        return foodN;
    }
    public FoodS(){

    }
}
