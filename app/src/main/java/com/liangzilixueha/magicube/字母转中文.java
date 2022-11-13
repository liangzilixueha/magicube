package com.liangzilixueha.magicube;

//让公式变成中文，记住，始终让你的正面朝向你
public class 字母转中文 {
    /*
    * 反转——传入整个字符串，然后返回字符串
    示例：传入   R U R' U' R' F R2 U' R' U' R U R' F'
         返回   F R U' R' U  R U  R2 F' R  U R U' R'
     */
    public String 反转(String str) {
        String[] split = str.split(" ");
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("2")) continue;
            if (split[i].contains("'"))
                split[i] = split[i].replace("'", "");
            else
                split[i] = split[i] + "'";
        }
        //反转
        for (int i = 0; i < split.length / 2; i++) {
            String temp = split[i];
            split[i] = split[split.length - i - 1];
            split[split.length - i - 1] = temp;
        }
        StringBuilder stringBuilder = new StringBuilder();
        //拼接
        for (String s : split) {
            stringBuilder.append(s).append("   ");
        }
        return stringBuilder.toString();
    }

    /*
     * 只能解析一个字符
     * 例如： 传入 L
     *       返回 左↓
     */
    public String 变换规则(String s) {
        //F
        s = s.replace("F", "前");
        //U
        s = s.replace("U", "上");
        if (s.contains("上"))
            if (s.contains("'")) {
                s = s.replace("'", "→撇");
            } else {
                s = s.concat("←撇");
            }
        s = s.replace("D", "下");
        if (s.contains("下"))
            if (s.contains("'")) {
                s = s.replace("'", "←撇");
            } else {
                s = s.concat("→撇");


            }
        s = s.replace("B", "后");
        if (s.contains("后"))
            if (s.contains("'")) {
                s = s.replace("'", "顺");
            } else {
                s = s.concat("逆");
            }
        s = s.replace("L", "左");
        if (s.contains("左"))
            if (s.contains("'")) {
                s = s.replace("'", "↑撇");
            } else {
                s = s.concat("↓撇");
            }
        s = s.replace("R", "右");
        if (s.contains("右"))
            if (s.contains("'")) {
                s = s.replace("'", "↓撇");
            } else {
                s = s.concat("↑撇");
            }
        s = s.replace("2", "2次");
        if (s.contains("2"))
            s = s.substring(0, 3);
        return s;
    }

    /*
     * 传入整个字符串，然后返回字符串
     * 例如：传入   R U R' U' R' F R2 U' R' U' R U R' F'
     *     返回   右↑撇 上←撇 右↓撇 上→撇 右↓撇 前 右2次 上→撇 右↓撇 上→撇 右↑撇 上←撇 右↓撇 前'
     * 前由于是正面，所以不变换 ’是逆时针的意思
     */

    public String 全部变换(String str) {
        String[] split = str.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : split) {
            stringBuilder.append(变换规则(s)).append("   ");
        }
        return stringBuilder.toString();
    }
}
