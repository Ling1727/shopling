package com.example.shopling.model;

import org.litepal.crud.LitePalSupport;

/**
 * Created by hasee on 2019/4/22.
 */

public class Comment extends LitePalSupport{
    private String user_id;
    private int commodity_number;
    private String comment;
    private String type;
    private int number;
    private String picture;
    private String time;
    private String touxiang;
    private int like;

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user_id='" + user_id + '\'' +
                ", commodity_number=" + commodity_number +
                ", comment='" + comment + '\'' +
                ", type='" + type + '\'' +
                ", number=" + number +
                ", picture='" + picture + '\'' +
                ", time='" + time + '\'' +
                ", touxiang='" + touxiang + '\'' +
                ", like=" + like +
                '}';
    }
}
