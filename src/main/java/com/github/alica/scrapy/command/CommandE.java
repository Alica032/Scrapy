package com.github.alica.scrapy.command;
import com.github.alica.scrapy.Page;
import com.github.alica.scrapy.command.util.Command;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CommandE implements Command {
    private final String[] listWords;
    public CommandE(String[] listWords){
        this.listWords = listWords;
    }
    private boolean findWords(String sentence){
        StringTokenizer st = new StringTokenizer(sentence);
        while (st.hasMoreTokens()) {
            final String token = st.nextToken();
            for (String str: listWords)
                if(token.equals(str)) return true;
        }
        return false;
    }
    public void findSentenceContainWords(String page){
        String code_pattern = "\\s//:((//:~){0}|.|\\s)*//:~\\s";
        String string = page.replaceAll(code_pattern, "");
        Pattern p = Pattern.compile("(^|(?<=[.!?]\\s))(\\d+\\.\\s?)*[А-ЯA-Z][^!?]*?[.!?](?=\\s*(\\d+\\.\\s)*[А-ЯA-Z]|$)", Pattern.MULTILINE);
        Matcher m = p.matcher(string);
        while (m.find()) {
            if (findWords(m.group()))
                System.out.println(m.group());
        }
    }
    @Override
    public void execute(Page page) {
        findSentenceContainWords(page.getWebpage());
        System.out.println();
    }
}
