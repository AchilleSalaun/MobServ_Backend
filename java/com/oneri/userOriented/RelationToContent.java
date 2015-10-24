package com.oneri.userOriented;

import com.google.appengine.api.datastore.Key;
import com.oneri.SuperClasses.User;

/**
 * Created by Gaby on 23/10/2015.
 */
public class RelationToContent {

    private Content content;
    private User user;
    private String relationType;
    private String comment;
    private Key key;


    public RelationToContent(String comment, Content content, String relationType, Key key) {
        this.comment = comment;
        this.content = content;
        this.relationType = relationType;
        this.key = key;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
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

    public Key getKey() {return key;}

    public void setKey(Key key) {
        this.key = key;
    }
}
