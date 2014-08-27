package com.github.alica.scrapy.command;
import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.util.CommandWithoutParameter;

public class CommandV implements CommandWithoutParameter{
    private Scrapy scrapy;

    public CommandV(Scrapy scrapy){
        this.scrapy = scrapy;
    }
    @Override
    public void execute() {
        System.out.print("Time spend on data scraping and data processing: " + scrapy.time());
    }
}
