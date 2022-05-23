package com.example.marowl.model;

public class User {
    String id;
    String email;
    String nickName;
    String phone;

    public User(String email, String nickName, String phone,String id) {
        this.id=id;
        this.email = email;
        this.nickName = nickName;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public String getNickName() {
        return nickName;
    }
    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }
}
