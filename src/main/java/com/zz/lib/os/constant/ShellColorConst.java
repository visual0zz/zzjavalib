package com.zz.lib.os.constant;

/**
 * linux系统
 */
public interface ShellColorConst {
    String clear="\033[0m";
    String hightlight="\033[01m";
    String underline="\033[04m";
    String blinking="\033[05m";
    String reverse="\033[07m";
    String fading="\033[08m";
    String cls="\033[2Jm";
    String hide_cursor="\033[?25l";
    String show_cursor="\033[?25h";

    String black="\033[30m";
    String red="\033[31m";
    String green="\033[32m";
    String yellow="\033[33m";
    String blue="\033[34m";
    String purple="\033[35m";
    String sky ="\033[36m";
    String white="\033[37m";

    String black_background="\033[40m";
    String red_background="\033[41m";
    String green_background="\033[42m";
    String yellow_background="\033[43m";
    String blue_background="\033[44m";
    String purple_background="\033[45m";
    String deep_green_background="\033[46m";
    String white_background="\033[47m";

    static String getColorCode(String name){
        switch (name){
            case "red":return red+hightlight;
            case "black":return black+hightlight;
            case "green":return green+hightlight;
            case "yellow":return yellow+hightlight;
            case "blue":return blue+hightlight;
            case "purple":return purple+hightlight;
            case "white":return white+hightlight;
            case "sky":return sky +hightlight;
        }
        return "";
    }
}
