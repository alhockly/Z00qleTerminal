package com.example;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class Web {

    String content = null;
    URLConnection connection = null;

    public void GetPage(String url) {
        try {
            connection = new URL(url).openConnection();
            String redirect = connection.getHeaderField("Location");
            if (redirect != null){
                connection = new URL(redirect).openConnection();
            }
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            //System.out.println(content);

            PrintWriter out = new PrintWriter("last,web.getpage.html");
            out.println(content);
            out.close();



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testpage(String uri){
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL(uri);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

    }

    public List<result> search(String url,String term){

            // Create a new instance of the html unit driver
            // Notice that the remainder of the code relies on the interface,
            // not the implementation.
            WebDriver driver = new HtmlUnitDriver();


            driver.get(url);

        // Find the text input element by its name
            WebElement EmailElement = driver.findElement(By.name("q"));
            // Enter something to search for
            EmailElement.sendKeys(term);

            // Now submit the form. WebDriver will find the form for us from the element
            EmailElement.submit();

            // Check the title of the page
            System.out.println("Page title is: " + driver.getTitle());

            if(driver.getTitle().contains("\"\" - ")){
                return null;
            }


            String source = driver.getPageSource();

        /*
        PrintWriter out = null;
        try {
            out = new PrintWriter(driver.getTitle()+".html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.println(source);
            out.close();
        */
            WebElement resultTable = driver.findElement(By.className("table-torrents"));

            List<WebElement> allRows = resultTable.findElements(By.tagName("tr"));

            List<result> results= new ArrayList<>();
            int errcount=0;
            for (WebElement row : allRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if(cells.size()==6) {
                        List<WebElement>links= row.findElements(By.tagName("a"));
                        String maglink="";
                        for(WebElement link:links){
                            if(link.getAttribute("href").contains("magnet:?")){
                                maglink=link.getAttribute("href");
                            }
                        }
                    try {
                        int pos = Integer.parseInt(cells.get(0).getText().replace(".", ""));
                        String name = cells.get(1).getText().split("\n")[0];
                        String qual = cells.get(1).getText().split("\n")[1];
                        String sound="";
                        String video="";
                        Pattern p = Pattern.compile("\\w {3}\\w");
                        Matcher m = p.matcher(qual);


                        if(m.find()) {
                             sound = qual.split("   ")[0];
                             video = qual.split("   ")[1];
                        }
                        else{
                             sound="";
                             video="";
                        }
                        String size = cells.get(3).getText().replace(" ","");
                        String age = cells.get(4).getText();
                        String seed = cells.get(5).getText().split("\n")[0];
                        String leech = cells.get(5).getText().split("\n")[1];               ///throws error
                        results.add(new result(maglink,pos, name, sound, video, size, age, seed, leech));

                    }catch (Exception e){
                        errcount++;
                        //e.printStackTrace();
                    }
                }

            }
            System.out.println("errcount="+errcount);
        System.out.println("Got "+results.size()+" results");



            System.out.print(Main.ANSI_BLACK);
            for(result res: results){
                System.out.println(res.toString());
            }


            driver.quit();


        System.out.println("Enter a number to dl or press q to return to search");
        Main.context="results";

            return results;



    }


}
