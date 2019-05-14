package com.example.shopling.model;

import org.litepal.crud.LitePalSupport;

/**
 * Created by hasee on 2019/4/22.
 */

public class Picture extends LitePalSupport{
    private int commodity_number;
    private  String path;

    public int getCommodity_number() {
        return commodity_number;
    }

    public void setCommodity_number(int commodity_number) {
        this.commodity_number = commodity_number;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
