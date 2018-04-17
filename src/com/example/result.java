package com.example;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class result {
    String mag;
    int pos;
    String name;
    String sound;
    String video;
    String size;
    String age;
    String seed;
    String leech;
    public result(String mag, int pos,String name, String sound,String video,String size,String age,String seed, String leech){
        this.pos=pos;
        this.name=name;
        this.sound=sound;
        this.video=video;
        this.size=size;
        this.age=age;
        this.mag=mag;
        this.seed=seed;
        this.leech=leech;
        ////Remove char from seed and leech
    }

    @Override
    public String toString() {
        String color="";
        color+=Main.ANSI_RESET;
        color+=fixedpos()+". ";
        color+=Main.ANSI_PURPLE+fixedLengthString(name,80)+" ";
        color+=Main.ANSI_WHITE+fixedLengthString(size,6)+" ";

        color+=Main.ANSI_CYAN+rightalign(seed,3);
        color+=Main.ANSI_RED+rightalign(leech,3);


        return color;
    }

    public void open() throws URISyntaxException, IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(mag));
        }
        System.out.println("opening "+mag);
    }

    public String fixedpos(){
        if(pos<10){
            return " "+String.valueOf(pos);
        }
        return String.valueOf(pos);
    }


    public static String fixedLengthString(String string, int length) {
        while(string.length()<length) {
            string += " ";
        }




        return string.substring(0, Math.min(string.length(), length));
    }

    public String rightalign(String string,int length){
        while(string.length()<length) {
            string=" "+string;
        }
        return string;
    }
}