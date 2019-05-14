package com.example.shopling.model;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by hasee on 2019/4/25.
 */

public class Commodity {
    private int number;
    private double price;
    private String intro;
    private String comment;
    private String store;
    private Picture picture;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "number=" + number +
                ", price=" + price +
                ", intro='" + intro + '\'' +
                ", comment='" + comment + '\'' +
                ", store='" + store + '\'' +
                ", picture=" + picture +
                '}';
    }
}
