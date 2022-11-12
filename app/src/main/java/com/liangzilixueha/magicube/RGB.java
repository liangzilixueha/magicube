package com.liangzilixueha.magicube;

public class RGB {
    public Integer Red;
    public Integer Green;
    public Integer Blue;

    public RGB(Integer red, Integer green, Integer blue) {
        Red = red;
        Green = green;
        Blue = blue;
    }

    public RGB() {
        Red = 0;
        Green = 0;
        Blue = 0;
    }

    public RGB(int pickedColor) {
        Red = (pickedColor >> 16) & 0xff;
        Green = (pickedColor >> 8) & 0xff;
        Blue = (pickedColor) & 0xff;
    }

    @Override
    public String toString() {
        return "RGB{" +
                "Red=" + Red +
                ", Green=" + Green +
                ", Blue=" + Blue +
                '}';
    }

    public int toInt() {
        return (Red << 16) + (Green << 8) + Blue;
    }
}
