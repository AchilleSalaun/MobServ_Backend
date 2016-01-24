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
        
        
        
        String no_image_url = "http://www.designsbybethann.com/pictures/Flowers/none%20flowers.jpg";
        
        String user_input = "harry potter";

        String contentType = "Book";
        String url_content = "";
        String title = "";
        String creator = "";
        String small_description = "";
        String image_url = "";
        String amazon_commercial_link = "";
        
        if(contentType.equals("Movie")){
            url_content = MovieParser.getFirstURLIMDBSearchResult(user_input);
            if(!url_content.equals("")){
                title = MovieParser.getIMDBTitleName(url_content);
                creator = MovieParser.getIMDBMovieDirector(url_content);
                small_description = MovieParser.getIMDBSmallDescription(url_content);
                image_url = MovieParser.getIMDBImageURL(url_content);
                amazon_commercial_link = MovieParser.getAmazonMovieCommercialLink(title);
            }
            else{
                title = "No result found";
                image_url = no_image_url;
            }
        }
        if(contentType.equals("Series")){
            url_content = SeriesParser.getFirstURLIMDBSearchResult(user_input);
            if(!url_content.equals("")){
                title = SeriesParser.getIMDBTitleName(url_content);
                creator = SeriesParser.getIMDBMovieCreator(url_content);
                small_description = SeriesParser.getIMDBSmallDescription(url_content);
                image_url = SeriesParser.getIMDBImageURL(url_content);
                amazon_commercial_link = SeriesParser.getAmazonMovieCommercialLink(title);
            }
            else{
                title = "No result found";
                image_url = no_image_url;
            }
            
        }
        if(contentType.equals("Book")){
            url_content = BookParser.getFirstURLIblistSearchResult(user_input);
            if(!url_content.equals("")){
                title = BookParser.getIblistTitleName(url_content);
                creator = BookParser.getIblistAuthor(url_content);
                small_description = BookParser.getIblistDescription(url_content);
                image_url = BookParser.getIblistImageURL(url_content);
            }
            else{
                title = "No result found";
                image_url = no_image_url;
            }
        }
        
        System.out.println("Title : " + title);
        System.out.println("Director (Creator) : " + creator);
        System.out.println("Description : " + small_description);
        System.out.println("Image URL : " + image_url);
        System.out.println("Amazon Commercial Link : " + amazon_commercial_link);
        

        
        
    }
    
    
}