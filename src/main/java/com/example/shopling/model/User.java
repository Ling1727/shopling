package com.example.shopling.model;

import org.litepal.crud.LitePalSupport;

/**
 * Created by hasee on 2019/4/20.
 */

public class User extends LitePalSupport{
    private String nameId;
    private int cell_phone_number;
    private String password;
    private String name;
    private int vip;
    private String gender;
    private String birthday;
    private String head_portrait;
    private double money;
    private String date_of_foundation;

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public int getCell_phone_number() {
        return cell_phone_number;
    }

    public void setCell_phone_number(int cell_phone_number) {
        this.cell_phone_number = cell_phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDate_of_foundation() {
        return date_of_foundation;
    }

    public void setDate_of_foundation(String date_of_foundation) {
        this.date_of_foundation = date_of_foundation;
    }

    @Override
    public String toString() {
        return "User{" +
                "nameId='" + nameId + '\'' +
                ", cell_phone_number=" + cell_phone_number +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", vip=" + vip +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", head_portrait='" + head_portrait + '\'' +
                ", money=" + money +
                ", date_of_foundation='" + date_of_foundation + '\'' +
                '}';
    }
}
