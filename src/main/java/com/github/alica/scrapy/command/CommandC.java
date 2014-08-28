package com.github.alica.scrapy.command;
import com.github.alica.scrapy.Page;
import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.util.Command;
public class CommandC implements Command{
    private Scrapy scrapy;
    public CommandC(Scrapy scrapy){
        this.scrapy = scrapy;
    }
    @Override
    public void execute(Page page) {
        System.out.println("number of characterisics " + scrapy.characterCount(page.getWebpage()));
        System.out.println();
    }
}