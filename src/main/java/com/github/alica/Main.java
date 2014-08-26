package com.github.alica;
import com.github.alica.Scrapy.Scrapy;

import java.io.File;
import java.util.ArrayList;

public class Main {
    private static boolean[] flags = { false,  false, false, false };
    private void parser(String s){
    }

    public void run(){
        //Scanner scanner = new Scanner(System.in);
        //String command = scanner.nextLine();
    }

    public static void main(String[]args){
        ArrayList<String> listwords = new ArrayList<String>();
        listwords.add("cat");
        listwords.add("dog");
        flags[1] = true;
        flags[0] = true;
        flags[2] = true;
        flags[3] = true;
        //Scrapy scrapy = new Scrapy("http://www.businessinsider.com/cat-and-dog-ownership-maps-2014-7", listwords, flags);
        File fileURL = new File("src/main/resources/list_urls.txt");
        new  Scrapy(fileURL, listwords, flags);

    }
}
