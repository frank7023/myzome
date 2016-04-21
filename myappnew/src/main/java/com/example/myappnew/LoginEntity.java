package com.example.myappnew;

import cn.bmob.v3.BmobObject;

/**
 * Created by 7023 on 2016/4/1.
 */
public class LoginEntity extends BmobObject {
    private String name;
    private String passwd;

    public LoginEntity(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public LoginEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
