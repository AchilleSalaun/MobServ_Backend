package com.oneri.SuperClasses;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.oneri.database.ObjectFromDB;

/**
 * Created by Gaby on 23/10/2015.
 */
public class User extends ObjectFromDB {

    private static final String type = "Contact";

    private boolean inCache = false;
    private String name = "undefined";
    private String email = "undefined";
    private String phone = "undefined";
    private String pict = "undefined";


    public User(String email){
        super();
        Key key = KeyFactory.createKey(type, email);
        this.setKey(key);
        this.email=email;
    }

    public User(Entity entity){
        super(entity.getKey());
        initFromEntity(entity);
    }
    public User(String name, String email, String phone, String pict) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pict = pict;
    }

    public User(Key key, String name, String email, String phone, String pict) {
        super(key);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pict = pict;
    }

    private void initFromEntity(Entity entity){
        this.name = (String)entity.getProperty("Name");
        this.email = (String)entity.getProperty("Email");
        this.phone = (String)entity.getProperty("Phone");
        this.pict = (String)entity.getProperty("Pict");
        this.inCache = true;
    }

    @Override
    public Entity getEntity(){
        Entity contact;
        if(inCache){
            contact = new Entity(type, email); //This line means that the email address is used as a key in the DB
            contact.setProperty("name", name);
            contact.setProperty("phone", phone);
            contact.setProperty("email", email); //Two people can't have the same or the DB won't make the difference
            contact.setProperty("pict", pict);
        }else{
            contact = this.getEntityFromDB();
            initFromEntity(contact);
        }

        return contact;
    }

    public String getName() {
        if(name.equals("undefined"))
            this.name=getInDB("Name");
        return name;}

    public String getEmail() {
        if(email.equals("undefined")){
            this.email= getInDB("Email");
        }
        return email;}

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

    private String getInDB(String property){
        Query.Filter keyFilter =
                new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
                        Query.FilterOperator.EQUAL,
                        this.getKey());
        Query q =  new Query(type).setFilter(keyFilter);
        q.addProjection(new PropertyProjection(property,String.class));
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery pq = datastore.prepare(q);
        Entity entity= pq.asSingleEntity();
        return (String)entity.getProperty(property);
    }
}
