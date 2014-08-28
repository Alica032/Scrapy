package com.github.alica.scrapy.command;
import com.github.alica.scrapy.Page;
import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.util.Command;
public class CommandW implements Command {
    private Scrapy scrapy;
    public CommandW(Scrapy scrapy){
        this.scrapy = scrapy;
    }
    @Override
    public void execute(Page page) {
        int[] wordCount = scrapy.wordCount(scrapy.listWords, page.getWebpage());
        for (int i = 0; i < scrapy.listWords.length; i++)
            System.out.println(scrapy.listWords[i] + ": " + wordCount[i]);
        System.out.println();
    }
}
