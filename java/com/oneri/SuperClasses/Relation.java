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
    private String contentId;
    private String userId;

    public Relation(Key key) {
        super(key);
        Entity entity = getEntityFromDB();
        initFromEntity(entity);
    }
    public Relation (String id){
        super(id);
    }
    public Relation(Key key,String relationType,String comment, String contentId, String userId) {
        super(key);
        this.relationType = relationType;
        this.comment = comment;
        this.contentId = contentId;
        this.userId = userId;
    }
    public Relation(String relationType,String comment, String contentId, String userId){
        super();
        this.relationType = relationType;
        this.comment = comment;
        this.contentId = contentId;
        this.userId = userId;
    }

    private void initFromEntity(Entity entity){
        this.relationType = (String)entity.getProperty("RelationType");
        this.comment = (String)entity.getProperty("Comment");
        this.contentId = (String)entity.getProperty("ContentId");
        this.userId = (String)entity.getProperty("UserId");
    }
    public Entity createEntity() {
        Entity entity;
        entity = new Entity(type, userId + contentId);
        entity.setProperty("ContentId", contentId);
        entity.setProperty("UserId", userId);
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

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
