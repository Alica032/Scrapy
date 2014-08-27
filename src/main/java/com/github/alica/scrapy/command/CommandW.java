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
        int[] wordCount;
        for(Scrapy.Page page: scrapy.webpages){
            System.out.println(page.getUrl() + ':');
            wordCount = scrapy.wordCount(listWords, page.getWebpage());
            for (int i = 0; i < listWords.size(); i++)
            {
                    System.out.println(listWords.get(i) + ": " + wordCount[i]);
                }
                System.out.println();
            }
        }
}
