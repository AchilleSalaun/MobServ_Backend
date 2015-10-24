package com.oneri.SuperClasses;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.oneri.database.ObjectFromDB;

/**
 * Created by Gaby on 24/10/2015.
 */
public class Content extends ObjectFromDB {

    private static final String type = "Content";

    private String commercialLink = "undefined commercial link";
    private String contentType = "undefined content type";
    private String creator = "undefined creator";
    private String description = "undefined description";
    private String imageURL = "undefined image URL";
    private String title = "undefined title";


    public Content(Key key, String commercialLink, String contentType, String creator, String description, String imageURL, String title) {
        super(key);
        this.commercialLink = commercialLink;
        this.contentType = contentType;
        this.creator = creator;
        this.description = description;
        this.imageURL = imageURL;
        this.title = title;
    }

    public Content(Key key) {
        super(key);
        Entity entity = getEntityFromDB();
        initFromEntity(entity);
    }

    public Content(String id) {
        super(type,id);
        Entity entity = getEntityFromDB();
        initFromEntity(entity);
    }

    public Content(String commercialLink, String contentType, String creator, String description, String imageURL, String title) {
        super(null);
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
    }

    public Entity createEntity(){
        Entity contact;
        contact = new Entity(type, title + contentType);
        contact.setProperty("CommercialLink", commercialLink);
        contact.setProperty("ContentType", contentType);
        contact.setProperty("Creator", creator);
        contact.setProperty("Description", description);
        contact.setProperty("ImageURL", imageURL);
        contact.setProperty("Title", title);
        return contact;
    }
}
