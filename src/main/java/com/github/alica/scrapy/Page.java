package com.github.alica.scrapy;
public class Page{
    final String url;
    final String webpage;
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