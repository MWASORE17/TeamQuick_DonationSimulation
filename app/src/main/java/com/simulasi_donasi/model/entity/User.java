package com.simulasi_donasi.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private int dompet;
    private String password;

    public static int _id = 1;

    public static ArrayList<User> users = new ArrayList<>();

    public User() {}

    public User(String name, String email, String password,int dompet) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dompet = dompet;
        this.id = _id;
        _id++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDompet() {
        return dompet;
    }

    public void setDompet(int dompet) {
        this.dompet = dompet;
    }
}