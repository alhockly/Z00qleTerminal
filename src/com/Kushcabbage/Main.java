package com.Kushcabbage;

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
            if(context.equals("search")) {
                System.out.print(ANSI_GREEN + "Zooqle: $ ");
            }
            if(context.equals("results")){
                System.out.print(ANSI_GREEN + "Results: $ ");
            }
            if(context.equals("onmatch")){
                System.out.print(ANSI_GREEN + "Match: $ ");
            }


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



        if(context.equals("onmatch")){
            try {
                int num = Integer.parseInt(command);

                results[num].open();
                context = "search";


            } catch (Exception e) {
                //e.printStackTrace();
            }


        }



        if(command.equals("q")){
            context="search";
            return;
        }

        if(command.equals("")){
            return;
        }

        if(context.equals("search")){
            results =  web.search(baseurl,command,driver);


        }

        else {
            if (context.equals("results")) {
                if (command.equals("q")) {
                    context = "search";
                    return;
                }

                if(Hasmatch()) {
                    if (command.toLowerCase().equals("movie")) {
                        System.out.println("going to " + results[0].Getlink());
                        context="onmatch";
                        Web.getmatchpage(results[0].Getlink(),driver,"movie");

                    }
                    if (command.toLowerCase().equals("tv")) {
                        System.out.println("going to " + results[1].Getlink());
                        context="onmatch";
                        Web.getmatchpage(results[1].Getlink(),driver,"tv");
                    }




                }

                ///if its a num open the mag link
                try {
                    int num = Integer.parseInt(command);
                    results[num+1].open();
                    context = "search";
                } catch (Exception e) {
                    //e.printStackTrace();s

                }

            }

        }
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

