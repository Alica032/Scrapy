package com.github.alica.scrapy;
import com.github.alica.scrapy.command.util.Command;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class Scrapy {
    private final ArrayList<String> listUrls;
    private long t0;
    private Scrapy(ArrayList<String> listUrls){
        this.listUrls = listUrls;
    }
    private Scrapy(String url){
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
    public final void run(List<Command> listCommand, boolean time){
        for (String url: listUrls){
            System.out.println(url + ':');
            t0 = System.currentTimeMillis();
            Page page = downloadWebPage(url);
            if (time) System.out.println("Time spend on data scraping: " + time());
            t0 = System.currentTimeMillis();
            for (Command command: listCommand)
                command.execute(page);
            if (time) System.out.println("Time spend on data processing: " + time());
        }
    }
    public long time(){
        return System.currentTimeMillis() - t0;
    }
}