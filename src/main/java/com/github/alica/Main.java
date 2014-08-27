package com.github.alica;

import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.*;
import com.github.alica.scrapy.command.util.CommandWithParameter;
import com.github.alica.scrapy.command.util.CommandWithoutParameter;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[]args){
        ArrayList<String> listWords = new ArrayList<String>();
        listWords.add("dog");
        listWords.add("cat");
        Scrapy scrapy = Scrapy.readUrlsFile(new File("src/main/resources/list_urls.txt"));
        CommandWithoutParameter commandV = new CommandV(scrapy);
        CommandWithoutParameter commandC = new CommandC(scrapy);
        CommandWithParameter commandW = new CommandW(scrapy);
        CommandWithParameter commandE = new CommandE(scrapy);
        Switch s = new Switch(commandV, commandC, commandW, commandE);
        scrapy.run();
        s.c();
        s.w(listWords);
        s.e(listWords);
        s.v();
    }
}
