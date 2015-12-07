package com.oneri.SuperClasses;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.oneri.database.ObjectFromDB;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Gaby on 24/10/2015.
 */
public class Content extends ObjectFromDB {

    private static final String type = "Content";
    private boolean inCache = false;
    private String commercialLink = "undefined";
    private String contentType = "undefined";
    private String creator = "undefined";
    private String description = "undefined";
    private String imageURL = "undefined";
    private String title = "undefined";

    public Content(Key key) {
        super(key);
    }
    public Content(String id) {
        super(id);
    }
    public Content(String title,String contentType){
        super();
        Key key = KeyFactory.createKey(type, title + contentType);
        this.setKey(key);
    }
    public Content(String commercialLink, String contentType, String creator, String description, String imageURL, String title) {
        super();
        this.commercialLink = commercialLink;
        this.contentType = contentType;
        this.creator = creator;
        this.description = description;
        this.imageURL = imageURL;
        this.title = title;
    }

    public Content(Key key, String commercialLink, String contentType, String creator, String description, String imageURL, String title) {
        super(key);
        this.commercialLink = commercialLink;
        this.contentType = contentType;
        this.creator = creator;
        this.description = description;
        this.imageURL = imageURL;
        this.title = title;
    }

    private void initFromEntity(Entity entity){
        this.commercialLink = (String) entity.getProperty("CommercialLink");
        this.contentType = (String) entity.getProperty("ContentType");
        this.creator = (String) entity.getProperty("Creator");
        this.description = (String) entity.getProperty("Description");
        this.imageURL = (String) entity.getProperty("ImageURL");
        this.title = (String) entity.getProperty("Title");
        this.inCache = true;
    }
    public Entity createEntity(){
        Entity contact;
        if(inCache){
            contact = new Entity(type, title + contentType);
            contact.setProperty("CommercialLink", commercialLink);
            contact.setProperty("ContentType", contentType);
            contact.setProperty("Creator", creator);
            contact.setProperty("Description", description);
            contact.setProperty("ImageURL", imageURL);
            contact.setProperty("Title", title);
        }else{
            contact = this.getEntityFromDB();
            initFromEntity(contact);
        }
        return contact;
    }

    public static String getType() {
        return type;
    }

    public String getCommercialLink() {return commercialLink;}

    public void setCommercialLink(String commercialLink) {
        this.commercialLink = commercialLink;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
