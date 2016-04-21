package com.example.collect;

/**
 * Created by 7023 on 2016/4/9.
 */
public class CollectEntity {
    private String name;
    private String description;
    private String img;
    private String keywords;
    private String message;

    public CollectEntity() {
    }

    public CollectEntity(String name, String description, String img, String keywords, String message) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.keywords = keywords;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
