package com.example.myappnew;

/**
 * Created by Administrator on 2016/3/19 0019.
 */
public class ClassifyEntity  {//分类实体，包装的功能
    private int id;
    private String name;

    public ClassifyEntity() {

    }

    public ClassifyEntity(int id, String name) {
        this.id = id;
        this.name = name;
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
