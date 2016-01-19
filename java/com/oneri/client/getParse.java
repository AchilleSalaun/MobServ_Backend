package com.oneri.client;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Main
{
    public static void main(String args[])
    {
    	
    	//POUR LES FILMS
    	String user_input = "nemo";

    	String contentType = "Movie";
    	
    	if(contentType.equals("Movie")){
	    	String imdb_url_content = getFirstURLIMDBSearchResult(user_input);
	        String title = getIMDBTitleName(imdb_url_content);
	        String creator = getIMDBMovieDirector(imdb_url_content);
	        String small_description = getIMDBSmallDescription(imdb_url_content);
	        String image_url = getIMDBImageURL(imdb_url_content);
	        String amazon_movie_commercial_link = getAmazonMovieCommercialLink(title);
    		System.out.println("Title : " + title);
            System.out.println("Director (Creator) : " + creator);
            System.out.println("Description : " + small_description);
            System.out.println("Image URL : " + image_url);
            System.out.println("Amazon Commercial Link : " + amazon_movie_commercial_link);
    	}
    	if(contentType.equals("Book")){
	        String iblist_url_content = getFirstURLIblistSearchResult(user_input);
	        String title = getIblistTitleName(iblist_url_content);
	        String creator = getIblistAuthor(iblist_url_content);
	        String description = getIblistDescription(iblist_url_content);
	        String image_url = getIblistImageURL(iblist_url_content);
	        System.out.println(iblist_url_content);
	        System.out.println(title);
	        System.out.println(creator);
	        System.out.println(description);
	        System.out.println(image_url);
    	}
        

        
        
    }
    
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
    
    public static String getFirstURLIMDBSearchResult(String title){
        String title_url_encoded = URLEncoder.encode(title);
        String url = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + title_url_encoded + "&s=all";
        String first_url_imdb_search_result = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=main]").select("table[class=findList]").select("tr[class=findResult odd]").get(0).select("td[class=result_text]").select("a").get(0); // selectionne le contenu wikipedia pour l'app
            first_url_imdb_search_result = "http://www.imdb.com/" + link.attr("href");
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getFirstURLIMDBSearchResult";
        }
        return first_url_imdb_search_result;
    }

    public static String getIMDBMovieDirector(String url){
        String imdb_movie_director = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("td[id=overview-top]").select("div[itemprop=director]").select("span[itemprop=name]").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_movie_director = link.text();
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBMovieDirector";
        }
        return imdb_movie_director;
    }

    public static String getIMDBSmallDescription(String url){
        String imdb_small_description = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("p[itemprop=description]").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_small_description = link.text();
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBSmallDescription";
        }
        return imdb_small_description;
    }

    public static String getIMDBTitleName(String url){
        String imdb_title_name = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("h1[class=header]").select("span[itemprop=name]").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_title_name = link.text();
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBTitleName";
        }
        return imdb_title_name;
    }

    public static String getIMDBImageURL(String url){
        String imdb_image_url = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("td[id=img_primary]").select("div[class=image]").select("a[href]").select("img").get(0); // selectionne le contenu wikipedia pour l'app
            imdb_image_url = link.attr("src");
        }catch(IOException e){
            return "IOException in Jsoup.connect(url).get() getIMDBImageURL";
        }
        return imdb_image_url;
    }
    
    
    //Ne Fonctionne pas
    public static String getAmazonMovieCommercialLink(String search_query){
    	
    	String url_encoded_search_query = URLEncoder.encode(search_query.toLowerCase());
    	String amazon_commercial_link = "";
    	//String url = "http://www.amazon.co.uk/s/ref=nb_sb_noss_2?url=search-alias%3Ddvd&field-keywords=" + url_encoded_search_query + "&sprefix=" + url_encoded_search_query + "%2Caps%2CNaN)";
    	//String url = "http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Ddvd&field-keywords=reservoir+dogs&rh=n%3A283926%2Ck%3Areservoir+dogs";
    	//String url = "http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Ddvd&field-keywords=reservoir+dogs&rh=n%3A283926%2Ck%3Areservoir+dogs";
    	String url = "http://www.amazon.co.uk/s/ref=nb_sb_ss_c_0_4?url=search-alias%3Ddvd&field-keywords=" + 
    	url_encoded_search_query + "&sprefix=" + url_encoded_search_query + "%2Caps%2C170";
    	Document doc;
    	System.out.println(url_encoded_search_query);
    	try{
    		doc = Jsoup.connect(url).timeout(100000).get();
    		
    		//Element link = doc.select("div[id=main]").select("div[id=rightContainerATF]").select("li[id=result_0]").select("div[class=a-row a-spacing-mini]").select("a[class=a-link-normal s-access-detail-page a-text-normal]").get(0);

    		Element link = doc.select("div[id=main]").select("div[id=rightContainerATF]").select("li[id=result_0]").
    				select("div[class=a-row a-spacing-mini]").select("div[class=a-row a-spacing-none]").get(0).
    				select("a[href]").get(0);
    
    		amazon_commercial_link = link.attr("href");
    	}catch(IOException e){
    		//return "IOException Jsoup.connect(url).get(0) getAmazonMovieCommercialLink " + url_encoded_search_query;
    		return e.getMessage();
    	}
    	
    	return amazon_commercial_link;
    }
}