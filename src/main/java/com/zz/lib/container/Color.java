package com.zz.lib.container;

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
        cyan=Math.max(cyan,0);
        cyan=Math.min(cyan,1);
        magenta=Math.max(magenta,0);
        magenta=Math.min(magenta,1);
        yellow=Math.max(yellow,0);
        yellow=Math.min(yellow,1);
        black=Math.max(black,0);
        black=Math.min(black,1);
        return new Color((int) (255*(1-cyan)*(1-black)), (int) (255*(1-magenta)*(1-magenta)), (int) (255*(1-yellow)*(1-yellow)));
    }
    public static Color ofCMYK(int cyan,int magenta,int yellow,int black){
        return ofCMYK((float) (cyan/100.0), (float) (magenta/100.0), (float) (yellow/100.0), (float) (black/100.0));
    }
}
