package com.oneri.SuperClasses;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.oneri.database.ObjectFromDB;
import com.oneri.userOriented.RelationToContent;

import java.util.ArrayList;

/**
 * Created by Gaby on 23/10/2015.
 */
public class User extends ObjectFromDB {

    private String type = "Contact";
    private String name = "undefined name";
    private String email = "undefined email";
    private String phone = "undefined phone number";
    private String pict = "undefined pict url";

    public User(Key key, String name, String email, String phone,
                 String pict) {
        super(key);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pict = pict;

    }

    public User(Key key) {
        super(key);
        Entity entity = getEntity();
        this.name = (String)entity.getProperty("Name");
        this.email = (String)entity.getProperty("Email");
        this.phone = (String)entity.getProperty("Phone");
        this.pict = (String)entity.getProperty("Pict");
    }

    public User(String id) {
        super(id);
        Entity entity = getEntity();
        this.name = (String)entity.getProperty("Name");
        this.email = (String)entity.getProperty("Email");
        this.phone = (String)entity.getProperty("Phone");
        this.pict = (String)entity.getProperty("Pict");
    }

    public User(Key key, String name, String email, String phone,
                String pict) {
        super(key);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pict = pict;

    }


    public static String getType(){return "";}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPict() {
        return pict;
    }



}
