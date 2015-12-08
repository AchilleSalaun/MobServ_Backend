package com.oneri.database;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

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

    public abstract Entity getEntity();
    public void putInDB(){
        Entity entity = getEntity();

        // Take a reference of the datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        datastore.put(entity);
        key = entity.getKey();
    }

    public JSONObject toJSON(JSONObject jsonObject){
        Entity entity = getEntity();
        Map<String,Object> map = entity.getProperties();
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            String key   = (String)iterator.next();
            String value = (String) map.get(key);
            try {
                jsonObject.put(key,value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
