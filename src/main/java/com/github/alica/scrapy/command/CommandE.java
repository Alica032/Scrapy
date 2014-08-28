package com.github.alica.scrapy.command;
import com.github.alica.scrapy.Page;
import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.util.Command;
public class CommandE implements Command {
    private Scrapy scrapy;
    public CommandE(Scrapy scrapy){
        this.scrapy = scrapy;
    }
    @Override
    public void execute(Page page) {
        scrapy.findSentenceContainWords(page.getWebpage(), scrapy.listWords);
        System.out.println();
    }
}
