package com.oneri.contentOriented;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.oneri.SuperClasses.Content;
import com.oneri.SuperClasses.Relation;
import com.oneri.SuperClasses.User;

/**
 * Created by Gaby on 24/10/2015.
 */
public class RelationToUser extends Relation{

    private User user;

    public RelationToUser(Entity entity){
        super(entity);
        setUserFromRelation();
    }

    public RelationToUser(String id) {super(id);
        setUserFromRelation();}

    public RelationToUser(Key key, String relationType, String comment, String title, String email, String contentType) {
        super(key, relationType, comment, title, email, contentType);
        setUserFromRelation();}

    public RelationToUser(String relationType, String comment, String title, String email, String contentType) {
        super(relationType, comment, title, email, contentType);
        setUserFromRelation();}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public void setUserFromRelation(){this.user = new User(getEmail());}
}
