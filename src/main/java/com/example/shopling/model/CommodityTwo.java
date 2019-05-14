package com.example.shopling.model;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * Created by hasee on 2019/4/21.
 */

public class CommodityTwo extends LitePalSupport{
    private int number;
    private double price;
    private String intro;
    private List<Comment> comment;
    private String store;
    private List<Picture> picture;

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

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public List<Picture> getPicture() {

        picture= LitePal.where("commodity_number=?",number+"").find(Picture.class);
        return picture;
    }

    public void setPicture(List<Picture> picture) {
        this.picture = picture;
    }
}
