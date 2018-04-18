package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static boolean Hasmatch() {
        return hasmatch;
    }

    public static void SetMatch(boolean val) {
        hasmatch = val;
    }

    public static boolean hasmatch=false;
    // Create a new instance of the html unit driver
    // Notice that the remainder of the code relies on the interface,
    // not the implementation.
    static WebDriver driver = new HtmlUnitDriver();

    static Scanner input = new Scanner(System.in);
    public static String context = "search";
    static List<String> commands = new ArrayList<>();

    public static result[] results;

    static String baseurl = "https://www.zooqle.com";


    public static void main(String[] args) {
        commands.add("search");
        System.out.println(ANSI_GREEN + "Zooqle Terminal\n");

        if(!IsInternetAvailable()){
            System.out.println(ANSI_RED+"NO INTERNET :(");
            return;
        }
        ///check if online hehe
        Web web = new Web();


        while (true) {
            System.out.print(ANSI_GREEN+"Zooqle: $ ");

            String command = input.nextLine();
            commandHandler(command, web);

        }
    }

    private static boolean IsInternetAvailable() {
        try {
            final URL url = new URL("http://www.zooqle.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    private static void commandHandler(String command,Web web) {

        if(command.equals("q")){
            context="search";
            return;
        }

        if(command.equals("")){
            return;
        }

        if(context.equals("search")){
            results =  web.search(baseurl,command,driver);
            context="results";

        }

        else {
            if (context.equals("results")) {
                if (command.equals("q")) {
                    context = "search";
                    return;
                }

                if(Hasmatch()) {
                    if (command.toLowerCase().equals("y")) {
                        System.out.println("going to " + results[0].Getlink());
                        context="onmatch";
                        Web.getmatchpage(results[0].Getlink(),driver);

                    }
                    if (command.toLowerCase().equals("n")) {
                        context = "results";
                    }
                }

                ///if its a num open the mag link
                try {
                    int num = Integer.parseInt(command);
                    results[num].open();
                    context = "search";
                } catch (Exception e) {
                    //e.printStackTrace();s

                }

            }

        }
    }

    public static String GetProfileName(String html){

        PrintWriter out = null;


        try {
            out = new PrintWriter("lastfile.html");
            out.println(html);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        String tags[] = html.split("<");
        int i=0;
        for(String tag:tags){
            if(tag.contains("f89xq")){
                System.out.println(tags[i]);
                return tag;
            }

            i++;
        }

        return "Uknown";
    }



    private static boolean checkValid(String command){
        String args[] = command.split(" ");

        for(String comm: commands){
            if(args[0].toLowerCase().equals(comm))
                return true;
        }
        return false;
    }
}

