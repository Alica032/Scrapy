package com.github.alica.scrapy.command;

import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.util.CommandWithParameter;

import java.util.ArrayList;

public class CommandW implements CommandWithParameter{
    private Scrapy scrapy;

    public CommandW(Scrapy scrapy){
        this.scrapy = scrapy;
    }

    @Override
    public void execute(ArrayList<String> listWords) {

        for(Scrapy.Page page: scrapy.webpages){
            System.out.println(page.getUrl() + ':');
            for (String word: listWords){
                    System.out.println(word + ": " + scrapy.wordCount(word, page.getWebpage()));
                }
                System.out.println();
            }
        }
}
