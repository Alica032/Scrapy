package com.github.alica.scrapy.command;
import com.github.alica.scrapy.Page;
import com.github.alica.scrapy.command.util.Command;
public class CommandC implements Command{
    public int characterCount(String page){
        return page.length();
    }
    @Override
    public void execute(Page page) {
        System.out.println("number of characterisics " + characterCount(page.getWebpage()));
        System.out.println();
    }
}