package com.oneri.client;

import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MovieParser {
	
	public static String getFirstURLIMDBSearchResult(String title){
        String title_url_encoded = URLEncoder.encode(title);
        //String url = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + title_url_encoded + "&s=all";
        String url = "http://www.imdb.com/find?q=" + title_url_encoded + "&s=tt&ttype=ft&ref_=fn_ft";
        String first_url_imdb_search_result = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Elements links_tmp = doc.select("div[id=main]").select("table[class=findList]").select("tr[class=findResult odd]");
            if(links_tmp.size()!=0){
            	Elements links = links_tmp.get(0).
                		select("td[class=result_text]").select("a");
            	if(links.size()!=0){
            		Element link = links.get(0);
            		 first_url_imdb_search_result = "http://www.imdb.com/" + link.attr("href");
            	}
            	else{
            		first_url_imdb_search_result = "";
            	}
            }
            else{
            	first_url_imdb_search_result = "";
            }	
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
            Elements links = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("td[id=overview-top]").
            		select("div[itemprop=director]").select("span[itemprop=name]");
            if(links.size()!=0){
            	Element link = links.get(0);
            	imdb_movie_director = link.text();
            }
            else{
            	imdb_movie_director = "";
            }
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
            Elements links = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("p[itemprop=description]"); 
            if(links.size()!=0){
            	Element link = links.get(0);
            	imdb_small_description = link.text();
            }
            else{
            	imdb_small_description = "";
            }
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
            Element link = doc.select("div[id=pagecontent]").select("div[id=main_top]").select("h1[class=header]").select("span[itemprop=name]").get(0);
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
    		doc = Jsoup.connect(url).timeout(200000).get();
    		
    		//Element link = doc.select("div[id=main]").select("div[id=rightContainerATF]").select("li[id=result_0]").select("div[class=a-row a-spacing-mini]").select("a[class=a-link-normal s-access-detail-page a-text-normal]").get(0);

    		Elements links_tmp = doc.select("div[id=main]").select("div[id=rightContainerATF]").select("li[id=result_0]").
    				select("div[class=a-row a-spacing-mini]").select("div[class=a-row a-spacing-none]");
    		if(links_tmp.size()!=0){
    			Elements links = links_tmp.select("a[href]");
    			if(links.size()!=0){
    				Element link = links.get(0);
        			amazon_commercial_link = link.attr("href");
        		}
        		else{
        			amazon_commercial_link = "";
        		}
    		}
    		else{
    			amazon_commercial_link = "";
    		}
    	}catch(IOException e){
    		//return "IOException Jsoup.connect(url).get(0) getAmazonMovieCommercialLink " + url_encoded_search_query;
    		return e.getMessage() + " url : " + url;
    	}
    	
    	return amazon_commercial_link;
    }
    
    

}
