package com.github.alica.scrapy.command;

import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.util.CommandWithParameter;

public class CommandE implements CommandWithParameter{
    private Scrapy scrapy;

    public CommandE(Scrapy scrapy){
        this.scrapy = scrapy;
    }

    @Override
    public void execute(String[] listWords) {
        for(Scrapy.Page page: scrapy.webpages){
            System.out.println(page.getUrl() + ':');
            scrapy.findSentenceContainWords(page.getWebpage(), listWords);
            System.out.println();
        }
    }
}
