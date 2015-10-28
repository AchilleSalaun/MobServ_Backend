package com.oneri.SuperClasses;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.oneri.database.ObjectFromDB;

/**
 * Created by Gaby on 23/10/2015.
 */
public class User extends ObjectFromDB {

    private static final String type = "Contact";

    private String name = "undefined name";
    private String email = "undefined email";
    private String phone = "undefined phone number";
    private String pict = "undefined pict url";

    public User(Key key, String name, String email, String phone, String pict) {
        super(key);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pict = pict;
    }

    public User(Key key) {
        super(key);
        Entity entity = getEntityFromDB();
        this.name = (String)entity.getProperty("Name");
        this.email = (String)entity.getProperty("Email");
        this.phone = (String)entity.getProperty("Phone");
        this.pict = (String)entity.getProperty("Pict");
    }

    public User(String id) {
        super(id);
        Entity entity = getEntityFromDB();
        initFromEntity(entity);
    }

    public User(String name, String email, String phone, String pict) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pict = pict;
    }

    public User(String email, int dumbVarialble){
        super();
        Query q = new Query("Contact")
                .setFilter(new Query.FilterPredicate("Email",
                        Query.FilterOperator.EQUAL,
                        email));

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery pq = datastore.prepare(q);
        Entity result = pq.asSingleEntity();
        initFromEntity(result);
        setKey(result.getKey());
    }

    private void initFromEntity(Entity entity){
        this.name = (String)entity.getProperty("Name");
        this.email = (String)entity.getProperty("Email");
        this.phone = (String)entity.getProperty("Phone");
        this.pict = (String)entity.getProperty("Pict");
    }

    public String getName() {return name;}

    public String getEmail() {return email;}

    public String getPhone() {
        return phone;
    }

    public String getPict() {
        return pict;
    }

    public void setName(String name) {this.name = name;}

    public void setEmail(String email) {this.email = email;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setPict(String pict) {this.pict = pict;}

    @Override
    public Entity createEntity(){
        Entity contact;
        contact = new Entity(type, email); //This line means that the email address is used as a key in the DB
        contact.setProperty("name", name);
        contact.setProperty("phone", phone);
        contact.setProperty("email", email); //Two people can't have the same or the DB won't make the difference
        contact.setProperty("pict", pict);
        return contact;
    }
}
