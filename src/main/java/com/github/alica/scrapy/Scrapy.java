package com.github.alica.scrapy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
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
        System.exit(1);
        return null;
    }

    private String textParser(StringBuffer buffer){
        Document doc = Jsoup.parse(buffer.toString());
        return doc.body().text();
    }

    public final void run(){
        t0 = System.currentTimeMillis();
        for (String url: listUrls)
            webpages.add(downloadWebPage(url));
    }

    public long time(){
        return System.currentTimeMillis() - t0;
    }

    public int[] wordCount(String[] listWords, String htmlText){
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

    private boolean findWords(String sentence, String[] listWords){
        StringTokenizer st = new StringTokenizer(sentence);
        while (st.hasMoreTokens()) {
            final String token = st.nextToken();
            for (String str: listWords)
                if(token.equals(str)) return true;
        }
        return false;
    }

    public void findSentenceContainWords(String page, String[] listWords){
        String code_pattern = "\\s//:((//:~){0}|.|\\s)*//:~\\s";
        String string = page.replaceAll(code_pattern, "");
        System.out.println(string);
        Pattern p = Pattern.compile("(^|(?<=[.!?]\\s))(\\d+\\.\\s?)*[А-ЯA-Z][^!?]*?[.!?](?=\\s*(\\d+\\.\\s)*[А-ЯA-Z]|$)", Pattern.MULTILINE);
        Matcher m = p.matcher(string);
        while (m.find()) {
            if (findWords(m.group(), listWords))
                System.out.println(m.group());
        }
    }

    public int characterCount(String page){
        return page.length();
    }
}
