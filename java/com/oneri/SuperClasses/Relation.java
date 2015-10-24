package com.oneri.SuperClasses;

import com.google.appengine.api.datastore.Key;
import com.oneri.database.ObjectFromDB;
import com.oneri.userOriented.*;

/**
 * Created by Gaby on 24/10/2015.
 */
public class Relation extends ObjectFromDB {

    private String relationType;
    private String comment;

    public Relation(Key key) {
        super(key);
    }
}
