package com.oneri.userOriented;

import com.google.appengine.api.datastore.Key;
import com.oneri.SuperClasses.Content;
import com.oneri.SuperClasses.Relation;
import com.oneri.SuperClasses.User;

/**
 * Created by Gaby on 23/10/2015.
 */
public class RelationToContent extends Relation {

    private Content content;

    public RelationToContent(Key key) {
        super(key);
    }

    public RelationToContent(String id) {
        super(id);
    }

    public RelationToContent(Key key, String relationType, String comment, String contentId, String userId) {
        super(key, relationType, comment, contentId, userId);
    }

    public RelationToContent(String relationType, String comment, String contentId, String userId) {
        super(relationType, comment, contentId, userId);
    }


    public Content getContent() {return content;}

    public void setContent(Content content) {this.content = content;}

    public void setContentFromDB(){this.content = new Content(this.getContentId());}

}
