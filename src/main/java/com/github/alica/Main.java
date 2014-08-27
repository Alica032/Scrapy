package com.github.alica;

import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.Switch;
import com.github.alica.scrapy.command.CommandV;
import com.github.alica.scrapy.command.CommandW;
import com.github.alica.scrapy.command.util.CommandWithParameter;
import com.github.alica.scrapy.command.util.CommandWithoutParameter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by root on 27.08.14.
 */
public class Main {
    public static void main(String[]args){
        ArrayList<String> listWords = new ArrayList<String>();
        listWords.add("dog");
        listWords.add("cat");
        Scrapy scrapy = Scrapy.readUrlsFile(new File("src/main/resources/list_urls.txt"));
        CommandWithoutParameter commandV = new CommandV(scrapy);
        CommandWithParameter commandW = new CommandW(scrapy);
        Switch s = new Switch(commandV, commandW);
        scrapy.run();
        s.w(listWords);
        s.v();
    }
}
