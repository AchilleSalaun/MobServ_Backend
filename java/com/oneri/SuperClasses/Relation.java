package com.oneri.SuperClasses;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.oneri.database.ObjectFromDB;

/**
 * Created by Gaby on 24/10/2015.
 */
public class Relation extends ObjectFromDB {

    private static final String type = "Relation";
    private String relationType;
    private String comment;
    private String title;
    private String contentType;
    private String email;

    /*public Relation(Key key) {
        super(key);
        Entity entity = getEntityFromDB();
        initFromEntity(entity);
    }*/
    public Relation (String id){
        super(id);
    }

    public Relation(Entity entity){
        super();
        this.setKey(entity.getKey());
        initFromEntity(entity);
    }

    public Relation(Key key,String relationType,String comment, String title, String email, String contentType) {
        super(key);
        this.relationType = relationType;
        this.comment = comment;
        this.contentType = contentType;
        this.title = title;
        this.email = email;
    }
    public Relation(String relationType,String comment, String title, String email, String contentType){
        super();
        this.relationType = relationType;
        this.comment = comment;
        this.title = title;
        this.email = email;
        this.contentType = contentType;
    }

    private void initFromEntity(Entity entity){
        this.relationType = (String)entity.getProperty("RelationType");
        this.comment = (String)entity.getProperty("Comment");
        this.title = (String)entity.getProperty("Title");
        this.contentType = (String)entity.getProperty("ContentType");
        this.email = (String)entity.getProperty("Email");
    }

    @Override
    public Entity getEntity() {
        Entity entity;
        entity = new Entity(type, email + title + contentType);
        entity.setProperty("Title", title);
        entity.setProperty("ContentType", contentType);
        entity.setProperty("Email", email);
        entity.setProperty("RelationType", relationType);
        entity.setProperty("Comment", comment);
        return entity;
    }

    public static String getType() {
        return type;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
