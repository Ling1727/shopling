package com.example.shopling.model;

/**
 * Created by hasee on 2019/4/25.
 */

public class ShoppingTrolley {
    private String user_id;
    private int commodity_number;
    private int number;
    private String state;
    private Commodity commodity;

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getCommodity_number() {
        return commodity_number;
    }

    public void setCommodity_number(int commodity_number) {
        this.commodity_number = commodity_number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ShoppingTrolley{" +
                "user_id='" + user_id + '\'' +
                ", commodity_number=" + commodity_number +
                ", nunber=" + number +
                ", state='" + state + '\'' +
                '}';
    }
}
