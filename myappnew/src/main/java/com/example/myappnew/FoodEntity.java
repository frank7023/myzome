package com.example.myappnew;

/**
 * Created by Administrator on 2016/3/19 0019.
 */
public class FoodEntity {
    private String name;
    private int id;
    private String description;//简介
    private String keywords;//关键字
    private String img;//图片
    public FoodEntity() {
    }

    public FoodEntity(String name, int id, String description, String keywords, String img) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.keywords = keywords;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
