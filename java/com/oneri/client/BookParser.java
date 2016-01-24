package com.oneri.client;

import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BookParser {
	
	public static String getFirstURLIblistSearchResult(String title){
        String title_url_encoded = URLEncoder.encode(title);
        String url = "http://www.iblist.com/search/search.php?item=" + title_url_encoded + "&submit=Search";
        String first_url_iblist_search_result = "";
        Document doc;
        try{
        	doc = Jsoup.connect(url).get();
        	Element link = doc.select("tbody").select("li").get(0).select("a").get(0);
        	first_url_iblist_search_result = link.attr("href");
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getFirstURLIblistSearchResult title_url_encoded : " + 
            		title_url_encoded + " url : " + url;
        }
        return first_url_iblist_search_result;
    }
    
    public static String getIblistTitleName(String url){
    	Document doc;
    	String iblist_title_name = "";
    	try{
    		doc = Jsoup.connect(url).get();
    		Element link = doc.select("div[id=main]").select("div[class=boxbody]").get(0).select("td[class=content").get(0).
    				select("td[class=content").get(0).select("table[class=main]").select("tbody").select("tbody").select("tr").
    				select("td").select("a").get(0);
    		iblist_title_name = link.text();
    	}catch(IOException e){
    		return "IOException in Jsoup.connect(url).get() getIblistTitleName url : " + url;
    	}
    	return iblist_title_name;
    }
    
    public static String getIblistAuthor(String url){
    	Document doc;
    	String iblist_author = "";
    	try{
    		doc = Jsoup.connect(url).get();
    		Element link = doc.select("div[id=main]").select("div[class=boxbody]").get(0).select("td[class=content").get(0).
    				select("td[class=content").get(0).select("table[class=main]").select("tbody").select("tbody").select("tr").
    				select("td").select("a").get(1);
    		iblist_author = link.text();
    	}catch(IOException e){
    		return "IOException in Jsoup.connect(url).get() getIblistAuthor url : " + url;
    	}
    	return iblist_author;
    }
    
    public static String getIblistDescription(String url){
    	Document doc;
    	String iblist_description = "";
    	try{
    		doc = Jsoup.connect(url).get();
    		Element link = doc.select("div[id=main]").select("div[class=boxbody]").get(0).select("td[class=content").
    				select("div[class=indent]").get(0);
    		iblist_description = link.text();
    	}catch(IOException e){
    		return "IOException in Jsoup.connect(url).get() getIblistDescription url : " + url; 
    	}
    	return iblist_description;
    }
    
    public static String getIblistImageURL(String url){
    	Document doc;
    	String iblist_image_url = "";
    	String iblist_base_url = "http://iblist.com/";
    	try{
    		doc = Jsoup.connect(url).get();
    		Element link = doc.select("div[id=main]").select("div[class=boxbody]").get(1).select("tbody").
    				select("img[src]").get(0);
    		//Element link = doc.select("div[id=main]").select("div[class=boxbody]").get(1).select("tbody").
    			//	select("a[target=_new").get(0);
    		iblist_image_url = iblist_base_url + link.attr("src");
    		//iblist_image_url = iblist_base_url + link.attr("href");

    	}catch(IOException e){
    		return "IOException in Jsoup.connect(url).get() getIblistImageURL url : " + url; 
    	}
    	return iblist_image_url;
    }
    
}