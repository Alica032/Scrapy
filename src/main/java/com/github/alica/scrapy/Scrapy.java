package com.github.alica.scrapy;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scrapy {
    public class Page{
        String url;
        String webpage;
        Page(String url, String webpage){
            this.url = url;
            this.webpage = webpage;
        }
       public String getUrl(){
           return url;
       }
       public String getWebpage(){
           return webpage;
       }

    }
    private ArrayList<String> listUrls;
    private final String symbols = " ,./?;:!-`()\'\"1234567890";
    private final Pattern regex = Pattern.compile("<script.*?</script>|<p.*?</p>|<.*?>", Pattern.DOTALL);
    private long t0;
    public ArrayList<Page> webpages;

    private Scrapy(ArrayList<String> listUrls){
        webpages = new ArrayList<Page>();
        this.listUrls = listUrls;
    }

    private Scrapy(String url){
        webpages = new ArrayList<Page>(1);
        listUrls = new ArrayList<String>(1);
        listUrls.add(url);
    }

    public static Scrapy readUrlsFile(File file){
        try {
            ArrayList<String> listUrls = new ArrayList<String>();
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) listUrls.add(line);
            return new Scrapy(listUrls);
        } catch (IOException e) {
            System.out.print("Error: it can't to read file");
            System.exit(1);
        }
        return null;
    }

    public static Scrapy readUrl(String url){

        return new Scrapy(url);
    }

    private Page downloadWebPage(String url) {
        URL u;
        DataInputStream dis;
        String s;
        InputStream is = null;
        try {
            u = new URL(url);
            is = u.openStream();
            dis = new DataInputStream(new BufferedInputStream(is));
            StringBuffer sb = new StringBuffer();
            while ((s = dis.readLine()) != null) {
                sb.append(s);
            }
            return new Page(url, textParser(sb));

        } catch (MalformedURLException mue) {
            System.out.println("Incorrect URL");
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Incorrect URL or no connect");
            System.exit(1);
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                System.exit(1);
            }
        }
        return null;
    }

    private String textParser(StringBuffer buffer){
        Matcher match = regex.matcher(buffer.toString());
        String result = match.replaceAll("");
        return result.replaceAll("[\\s]{2,}", " ");
    }

    public final void run(){
        t0 = System.currentTimeMillis();
        for (String url: listUrls)
            webpages.add(downloadWebPage(url));
    }

    public long time(){
        return System.currentTimeMillis() - t0;
    }

    public int wordCount(String word, String page){
        String str;
        int wordcount = 0;
        int position = 0;
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

/*
    public Pattern pattern(ArrayList<String> words){
        String str = "([A-Z][^.?!]*?)?(?<!\\w)(?i)(";
        for (String word: words){
            str = str + word + '|';
        }
        str = str.substring(0, str.length()-1) + ")(?!\\w)[^.?!]*?[.?!]{1,2}\"?";
        return Pattern.compile(str);
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



    private int wordCount(String word, String page){
        String str;
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
*/
}