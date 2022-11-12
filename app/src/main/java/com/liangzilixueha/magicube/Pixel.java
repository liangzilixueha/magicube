package com.liangzilixueha.magicube;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Pixel {
    private static final String TAG = "csw";
    int Red;
    int Green;
    int Blue;
    String Color = "";

    public Pixel(int red, int green, int blue) {
        Red = red;
        Green = green;
        Blue = blue;
    }

    /**
     * 将RGB值转换为文字的方法 即用作显示，也用作魔方还原数据
     * <p>
     * 需要注意的是 由于光线 魔方型号 等等因素干扰
     * 这里面的数字你可以自己进行调整
     */

    private void makeColor(Pixel p) {
        if (p.Red > 120) {
            if (p.Green > 160) {
                if (p.Blue > 150) {
                    this.Color = "白";
                } else {
                    this.Color = "黄";
                }
            } else if (p.Green > 50) {
                this.Color = "橙";
            } else {
                this.Color = "红";
            }
        } else {
            if (p.Green > p.Blue) {
                this.Color = "绿";
            } else {
                this.Color = "蓝";
            }
        }
    }

    public String getColor() {
        return Color;
    }

    public void setColor(SharedPreferences sp) {
        RGB red = new RGB(sp.getInt(FILE.RED, Red));
        RGB green = new RGB(sp.getInt(FILE.GREEN, Green));
        RGB blue = new RGB(sp.getInt(FILE.BLUE, Blue));
        RGB yellow = new RGB(sp.getInt(FILE.YELLOW, 0));
        RGB orange = new RGB(sp.getInt(FILE.ORANGE, 0));
        RGB white = new RGB(sp.getInt(FILE.WHITE, 0));
        List<RGB> list = new ArrayList<>();
        String[] color = {"红", "绿", "蓝", "黄", "橙", "白"};
        list.add(red);
        list.add(green);
        list.add(blue);
        list.add(yellow);
        list.add(orange);
        list.add(white);
        RGB rgb = new RGB(Red, Green, Blue);
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (距离(rgb, list.get(i)) < 距离(rgb, list.get(index))) {
                index = i;
            }
        }
        this.Color = color[index];
    }

    private double 距离(RGB d1, RGB d2) {
        double x1 = Math.pow(d1.Red - d2.Red, 2);
        double x2 = Math.pow(d1.Green - d2.Green, 2);
        double x3 = Math.pow(d1.Blue - d2.Blue, 2);
        return Math.sqrt(x1 + x2 + x3);
    }

    @Override
    public String toString() {
        return "R:" + Red +
                ",G:" + Green +
                ",B:" + Blue + "  ";
    }

}
