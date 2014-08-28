package com.github.alica;
import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.*;
import com.github.alica.scrapy.command.util.CommandWithParameter;
import com.github.alica.scrapy.command.util.CommandWithoutParameter;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String regexUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final Pattern patternUrl = Pattern.compile(regexUrl);

    public static void main(String[]args){
        args = new String[5];
        args[0] = "src/main/resources/list_urls.txt";
        args[1] = "cat,dog";
        args[2] = "-c";
        args[3] = "-v";
        args[4] = "-e";

        if((args.length < 3)||(args.length > 6)){
            System.out.print("Uncorrect commands");
            return;
        }

        Scrapy scrapy;
        Matcher matcher = patternUrl.matcher(args[0]);

        if (matcher.matches()) scrapy = Scrapy.readUrl(args[0]);
        else scrapy= Scrapy.readUrlsFile(new File(args[0]));
        String[] listWords = args[1].split(",");
        Set commandSet = new HashSet<String>();
        commandSet.add("-c");
        commandSet.add("-w");
        commandSet.add("-v");
        commandSet.add("-e");

        boolean time = false;
        for (int i = 2; i < args.length; i++)
        {
            if (!commandSet.contains(args[i])){
                System.out.print("Uncorrect commands");
                return;
            }
            for (int j = i + 1; j < args.length; j++)
                if (args[i].equals(args[j])) {
                    System.out.print("Uncorrect commands");
                    return;
                }
            if(args[i] == "-v") time = true;
        }

        CommandWithoutParameter commandV = new CommandV(scrapy);
        CommandWithoutParameter commandC = new CommandC(scrapy);
        CommandWithParameter commandW = new CommandW(scrapy);
        CommandWithParameter commandE = new CommandE(scrapy);
        Switch s = new Switch(commandV, commandC, commandW, commandE);
        scrapy.run();
        for (int i = 2; i < args.length; i++)
            switch(args[i].charAt(1)){
                case 'c': s.c();
                    break;
                case 'w': s.w(listWords);
                    break;
                case 'e': s.e(listWords);
            }
        if(time) s.v();
    }
}
