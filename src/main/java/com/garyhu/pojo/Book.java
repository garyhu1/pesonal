package com.garyhu.pojo;

import java.io.Serializable;

/**
 * @author : Administrator
 * @decripetion :
 * @since : 2018/10/30
 **/
public class Book implements Serializable{

    private static final long serialVersionID = 16298393457L;

    private String name;
    private int id;
    private String author;
    private float price;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
