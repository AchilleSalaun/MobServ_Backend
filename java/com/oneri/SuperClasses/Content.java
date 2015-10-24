package com.oneri.SuperClasses;

import com.google.appengine.api.datastore.Key;
import com.oneri.database.ObjectFromDB;

/**
 * Created by Gaby on 24/10/2015.
 */
public class Content extends ObjectFromDB {


    public Content(Key key) {
        super(key);
    }

    public Content(String id) {
        super(id);
    }
}
