package com.oneri.content;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

import com.google.appengine.api.datastore.KeyFactory;

import java.util.Comparator;

/**
 * Created by Gaby on 23/10/2015.
 */
public class Content {

    private Key key;
    private String commercialLink = "undefined commercial link";
    private String contentType = "undefined content type";
    private String creator = "undefined creator";
    private String description = "undefined description";
    private String imageURL = "undefined image URL";
    private String title = "undefined title";


    public Content(Key key){

        this.key = key;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        try {
            Entity entity = datastore.get(key);
            this.commercialLink = (String)entity.getProperty("CommercialLink");
            this.contentType = (String)entity.getProperty("ContentType");
            this.creator = (String)entity.getProperty("Creator");
            this.description = (String)entity.getProperty("Description");
            this.imageURL = (String)entity.getProperty("ImageURL");
            this.title = (String)entity.getProperty("Title");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    };

    public Content(String id){this(KeyFactory.createKey("Content",id));}


    public Content(Key key, String commercialLink, String contentType, String creator, String description, String imageURL, String title) {
        this.key = key;
        this.commercialLink = commercialLink;
        this.contentType = contentType;
        this.creator = creator;
        this.description = description;
        this.imageURL = imageURL;
        this.title = title;
    }

    public Content() {}

    public Key getKey() {return key;}

    public String getCommercialLink() {return commercialLink;}

    public String getContentType() {return contentType;}

    public String getCreator() {return creator;}

    public String getDescription() {return description;}

    public String getImageURL() {return imageURL;}

    public String getTitle() {return title;}

    public void setCommercialLink(String commercialLink) {this.commercialLink = commercialLink;}

    public void setContentType(String contentType) {this.contentType = contentType;}

    public void setCreator(String creator) {this.creator = creator;}

    public void setDescription(String description) {this.description = description;}

    public void setImageURL(String imageURL) {this.imageURL = imageURL;}

    public void setTitle(String title) {this.title = title;}
}
