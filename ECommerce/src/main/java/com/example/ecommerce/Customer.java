package com.example.ecommerce;

public class Customer {
    private int id;
    private String name , userName , mobile;
    public Customer(int id, String name, String userName, String mobile) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobile() {
        return mobile;
    }
}
