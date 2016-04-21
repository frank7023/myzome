package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2016/3/17 0017.
 */
public class ClassifyEntity {
    private int id;
    private String name;

    public ClassifyEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClassifyEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
