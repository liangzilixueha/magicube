package com.liangzilixueha.magicube;

import com.google.gson.annotations.SerializedName;

public class Pixiv {
    public Integer Red;
    public Integer Green;
    public Integer Blue;

    public Pixiv(int red, int green, int blue) {
        Red = red;
        Green = green;
        Blue = blue;
    }

    @Override
    public String toString() {
        return "Pixiv{" +
                "Red=" + Red +
                ", Green=" + Green +
                ", Blue=" + Blue +
                '}';
    }
}
