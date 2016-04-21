package com.example.pic;

/**
 * Created by 7023 on 2016/4/1.
 */
public class PisEntity {
    private String img;
    private String title;
    private int id;

    public PisEntity() {
        super();

    }

    public PisEntity(String img, String title, int id) {
        super();
        this.img = img;
        this.title = title;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
