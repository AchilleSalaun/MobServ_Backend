package com.oneri.database;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Created by Gaby on 24/10/2015.
 */
public abstract class ObjectFromDB {

    private String type;
    private Key key;



    public ObjectFromDB(Key key) {this.key = key;}
    public ObjectFromDB(String id){this.key = KeyFactory.createKey(type, id);}

    public Key getKey() {return key;}
    public String getId() {return KeyFactory.keyToString(key);}

    public void setKey(Key key) {this.key = key;}
    public void setKey(String id) {this.key = KeyFactory.createKey(type, id);}

    public Entity getEntity(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        try {
            return datastore.get(key);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



}
