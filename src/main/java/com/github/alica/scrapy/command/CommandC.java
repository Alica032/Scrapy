package com.github.alica.scrapy.command;
import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.util.CommandWithoutParameter;

public class CommandC implements CommandWithoutParameter{
    private Scrapy scrapy;

    public CommandC(Scrapy scrapy){
        this.scrapy = scrapy;
    }
    @Override
    public void execute() {
        for(Scrapy.Page page: scrapy.webpages){
            System.out.println(page.getUrl() + ':');
            System.out.println("number of characterisics " + scrapy.characterCount(page.getWebpage()));
            System.out.println();
        }
    }
}
