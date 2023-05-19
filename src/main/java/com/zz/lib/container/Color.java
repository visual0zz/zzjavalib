package com.zz.lib.container;

import com.zz.lib.common.util.CheckUtil;

public class Color {
    int red,green,blue;
    private Color(int red,int green,int blue){
        red= Math.max(red, 0);
        red=Math.min(red,255);
        green= Math.max(green, 0);
        green=Math.min(green,255);
        blue= Math.max(blue, 0);
        blue=Math.min(blue,255);
        this.red=red;
        this.green=green;
        this.blue=blue;
    }
    public static Color ofRGB(int red,int green,int blue){
        return new Color(red,green,blue);
    }
    public static Color ofCMYK(float cyan,float magenta,float yellow,float black){
        CheckUtil.mustTrue("input must between 0 and 1"
                ,cyan>=0&&cyan<=1&&magenta>=0&&magenta<=1&&yellow>=0&&yellow<=1&&black>=0&&black<=1);
        return new Color((int) (255*(1-cyan)*(1-black)), (int) (255*(1-magenta)*(1-magenta)), (int) (255*(1-yellow)*(1-yellow)));
    }
    public static Color ofCMYK(int cyan,int magenta,int yellow,int black){
        CheckUtil.mustTrue("input must between 0 and 100"
                ,cyan>=0&&cyan<=100&&magenta>=0&&magenta<=100&&yellow>=0&&yellow<=100&&black>=0&&black<=100);
        return ofCMYK((float) (cyan/100.0), (float) (magenta/100.0), (float) (yellow/100.0), (float) (black/100.0));
    }
}
