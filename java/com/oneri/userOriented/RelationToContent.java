package com.oneri.userOriented;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.oneri.SuperClasses.Content;
import com.oneri.SuperClasses.Relation;
import com.oneri.SuperClasses.User;
import com.oneri.contentOriented.ExtensiveContent;

/**
 * Created by Gaby on 23/10/2015.
 */
public class RelationToContent extends Relation {

    private Content content;

    public RelationToContent(Key key) {
        super(key);
        setContentFromDB();
    }

    public RelationToContent(String id) {
        super(id);
        setContentFromDB();
    }

    public RelationToContent(Key key, String relationType, String comment, String title, String email, String contentType) {
        super(key, relationType, comment, title, email, contentType);
        setContentFromDB();
    }

    public RelationToContent(String relationType, String comment, String title, String email, String contentType) {
        super(relationType, comment, title, email, contentType);
        setContentFromDB();
    }


    public Content getContent() {return content;}

    public ExtensiveContent getExtensiveContent()
    {
        Content content = this.getContent() ;
        return new ExtensiveContent(content.getKey()) ;
    }

    public void setContent(Content content) {this.content = content;}

    public void setContentFromDB(){this.content = new Content(getTitle(), getContentType());}



}
