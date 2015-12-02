package com.oneri.contentOriented;

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

    public RelationToUser(Key key) {
        super(key);
        setUserFromDB();}
    public RelationToUser(String id) {super(id);
        setUserFromDB();}

    public RelationToUser(Key key, String relationType, String comment, String title, String email, String contentType) {
        super(key, relationType, comment, title, email, contentType);
        setUserFromDB();}

    public RelationToUser(String relationType, String comment, String title, String email, String contentType) {
        super(relationType, comment, title, email, contentType);
        setUserFromDB();}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public void setUserFromDB(){this.user = new User(KeyFactory.stringToKey(getEmail()));}
}
