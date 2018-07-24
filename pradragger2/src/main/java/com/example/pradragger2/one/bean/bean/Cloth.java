package com.example.pradragger2.one.bean.bean;

/**
 * Created by TanJieXi on 2018/7/24.
 */
public class Cloth {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "布料:" + color;
    }
}
