package com.github.alica.scrapy.command;
import com.github.alica.scrapy.Page;
import com.github.alica.scrapy.command.util.Command;

import java.util.StringTokenizer;
public class CommandW implements Command {
    private String[] listWords;
    public CommandW(String[] listWords){
        this.listWords = listWords;
    }
    public int[] wordCount(String htmlText){
        int[] wordCount = new int[listWords.length];
        for (int i = 0; i < wordCount.length; i++) wordCount[i] = 0;
        StringTokenizer st = new StringTokenizer(htmlText);
        while (st.hasMoreTokens()) {
            final String token = st.nextToken();
            for (int i = 0; i < listWords.length; i++)
                if(token.equals(listWords[i]))
                    wordCount[i] += 1;
        }
        return wordCount;
    }
    @Override
    public void execute(Page page) {
        int[] wordCount = wordCount(page.getWebpage());
        for (int i = 0; i < listWords.length; i++)
            System.out.println(listWords[i] + ": " + wordCount[i]);
        System.out.println();
    }
}
