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

    private Key key;

    public ObjectFromDB(){this.key = null;}

    public ObjectFromDB(Key key) {this.key = key;}
    public ObjectFromDB(String id){this.key = KeyFactory.stringToKey(id);}

    public Key getKey() {return key;}
    public String getId() {return KeyFactory.keyToString(key);}

    public void setKey(Key key) {this.key = key;}
    public void setKey(String id, String type) {this.key = KeyFactory.stringToKey(id);}

    public Entity getEntityFromDB(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        try {
            return datastore.get(key);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract Entity createEntity();
    public void putInDB(){
        Entity entity = createEntity();

        // Take a reference of the datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        datastore.put(entity);
        key = entity.getKey();
    }
}
