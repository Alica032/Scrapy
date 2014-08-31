package com.github.alica.scrapy;
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
