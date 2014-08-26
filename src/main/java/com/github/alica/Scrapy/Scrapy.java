package com.github.alica.Scrapy;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scrapy {
    private class Page{
        String url;
        String webpage;
        Page(String url, String webpage){
            this.url = url;
            this.webpage = webpage;
        }

    }
    private ArrayList<Page> webpage = new ArrayList<Page>();
    private ArrayList<String> words;
    private InputStream is = null;
    private final String symbols = " ,./?;:!-`()\'\"1234567890";
    private final Pattern regex = Pattern.compile("<script.*?</script>|\\<.*?\\>", Pattern.DOTALL);
    private int position, wordcount;
    private String str;
    private Pattern pattern;
    private long t0 = System.currentTimeMillis();
    private boolean[] flags;

    public Scrapy(File file, ArrayList<String> words, boolean[] flags){
        this.flags = flags;
        this.words = words;
        fileReader(file);
        commands();
    }

    public Scrapy(String url, ArrayList<String> words, boolean[] flags){
        this.flags = flags;
        this.words = words;
        webPage(url);
        commands();
    }

    private void fileReader(File file){

        try {
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) webPage(line);
        } catch (IOException e) {
            System.out.print("Error: it can't to read file");
            System.exit(1);
        }

    }

    private void commands(){
        pattern();
        for(Page page: webpage){
            System.out.println(page.url + ':');
            if(flags[1]){
                for (String word: words){
                    System.out.println(word + ": " + wordCount(word, page.webpage));
                }
                System.out.println();
            }
            if(flags[2]) {
                System.out.println("number of characterisics " + characterCount(page.webpage));
                System.out.println();
            }
            if(flags[3]) sentence(page.webpage);
        }
        if(flags[0]) System.out.print("Time spend on data scraping and data processing:" + time());
    }

    private long time(){
        return System.currentTimeMillis() - t0;
    }

    private String textParser(StringBuffer buffer){
        Matcher match = regex.matcher(buffer.toString());
        String result = match.replaceAll("");
        return result.replaceAll("[\\s]{2,}", " ");
    }

    private void webPage(String url) {
        URL u;
        DataInputStream dis;
        String s;
        try {
            u = new URL(url);
            is = u.openStream();
            dis = new DataInputStream(new BufferedInputStream(is));
            StringBuffer sb = new StringBuffer();
            while ((s = dis.readLine()) != null) {
                sb.append(s);
            }
            webpage.add(new Page(url, textParser(sb)));

        } catch (MalformedURLException mue) {
            System.out.println("Incorrect URL");
            System.exit(1);
        } catch (IOException ioe) {
            System.exit(1);
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                System.exit(1);
            }
        }
    }

    private int wordCount(String word, String page){
        wordcount = 0;
        position = 0;
        while (page.indexOf(word, position) != -1){
            str = "";
            position = (page.toLowerCase()).indexOf(word.toLowerCase(), position);
            if (position > 0) str += page.charAt(position - 1);
            if (position + word.length() < page.length()) str += page.charAt(position + word.length());
            if(str.length() == 1 && symbols.contains(str)) wordcount++;
            if(str.length() == 2 && symbols.contains("" + str.charAt(0))&& symbols.contains("" + str.charAt(1))) wordcount++;
            position += word.length();
        }
        return wordcount;
    }

    private int characterCount(String page){
        return page.length();
    }

    private void pattern(){
        String str = "([A-Z][^.?!]*?)?(?<!\\w)(?i)(";
        for (String word: words){
            str = str + word + '|';
        }
        str = str.substring(0, str.length()-1) + ")(?!\\w)[^.?!]*?[.?!]{1,2}\"?";
        pattern = Pattern.compile(str);
    }

    private void sentence(String page){
        Matcher m = pattern.matcher(page);
        while (m.find()) {
            System.out.println(m.group());
        }
        System.out.println();
    }
}