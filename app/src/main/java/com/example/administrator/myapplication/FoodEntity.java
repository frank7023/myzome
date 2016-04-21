package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2016/3/17 0017.
 */
public class FoodEntity {
    private String description;//简介
    private String keywords;//关键字
    private String img;//照片

    public FoodEntity() {
    }

    public FoodEntity(String description, String keywords, String img) {
        this.description = description;
        this.keywords = keywords;
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
