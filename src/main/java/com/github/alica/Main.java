package com.github.alica;
import com.github.alica.scrapy.Scrapy;
import com.github.alica.scrapy.command.CommandC;
import com.github.alica.scrapy.command.CommandE;
import com.github.alica.scrapy.command.CommandW;
import com.github.alica.scrapy.command.util.Command;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    private static final String regexUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final Pattern patternUrl = Pattern.compile(regexUrl);
    public static void main(String[]args){
        if(args.length < 3){
            System.out.print("Uncorrect commands");
            return;
        }
        Scrapy scrapy;
        Matcher matcher = patternUrl.matcher(args[0]);
        String[] listWords = args[1].split(",");
        if (matcher.matches()) scrapy = Scrapy.readUrl(args[0]);
        else scrapy= Scrapy.readUrlsFile(new File(args[0]));
        boolean time = false;
        Command commandC = new CommandC();
        Command commandW = new CommandW(listWords);
        Command commandE = new CommandE(listWords);
        List<Command> commands = new ArrayList<Command>(3);
        for (int i = 2; i < args.length; i++)
        {
            if(args[i].equals( "-v")) time = true;
            else if(args[i].equals( "-c") && !commands.contains(commandC)) commands.add(commandC);
            else if(args[i].equals( "-w") && !commands.contains(commandW)) commands.add(commandW);
            else if(args[i].equals( "-e") && !commands.contains(commandE)) commands.add(commandE);
            else{
                System.out.print("Uncorrect commands");
                System.exit(1);
            }
        }
        scrapy.run(commands, time);
    }
}
