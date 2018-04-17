package com.example;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    
    static Scanner input = new Scanner(System.in);
    public static String context = "search";
    static List<String> commands = new ArrayList<>();

    public static List<result> results = new ArrayList<>();

    static String baseurl = "https://www.zooqle.com";


    public static void main(String[] args) {
        commands.add("search");
        System.out.println(ANSI_GREEN + "Zooqle Terminal\n");


        ///check if online hehe
        Web web = new Web();


        while (true) {
            System.out.print(ANSI_GREEN+"Zooqle: $ ");

            String command = input.nextLine();
            commandHandler(command, web);

        }
    }



    private static void commandHandler(String command,Web web) {
        if(command.equals("")){
            return;
        }

        if(context.equals("search")){
            results =  web.search(baseurl,command);
            context="results";

        }

        else {
            if (context.equals("results")) {
                if (command.equals("q")) {
                    context = "search";
                    return;
                }

                ///if its a num open the mag link
                try {
                    int num = Integer.parseInt(command);
                    results.get(num-1).open();
                    context = "search";
                } catch (Exception e) {
                    //e.printStackTrace();s
                    System.out.println("enter q to go back to search");
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

