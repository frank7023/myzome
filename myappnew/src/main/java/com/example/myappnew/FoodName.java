package com.example.myappnew;

import cn.bmob.v3.BmobObject;

/**
 * Created by 7023 on 2016/4/5.
 */
public class FoodName extends BmobObject {
    private String foodName;
    private String userName;
    private String foodUri;
    private Integer count;

    public FoodName() {
    }

    public FoodName(String foodName, String userName, String foodUri, Integer count) {
        this.foodName = foodName;
        this.userName = userName;
        this.foodUri = foodUri;
        this.count = count;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getFoodUri() {
        return foodUri;
    }

    public void setFoodUri(String foodUri) {
        this.foodUri = foodUri;
    }
}
