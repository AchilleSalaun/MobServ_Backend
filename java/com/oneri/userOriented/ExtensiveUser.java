package com.oneri.userOriented;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.*;
import com.oneri.SuperClasses.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaby on 24/10/2015.
 */
public class ExtensiveUser extends User{

    private ArrayList<RelationToContent> myList;
    private ArrayList<RelationToContent> contentUserLikes;
    private ArrayList<RelationToContent> contentUserDoesntLike;

    public ExtensiveUser(String name, String email, String phone, String pict, ArrayList<RelationToContent> myList, ArrayList<RelationToContent> contentUserLikes, ArrayList<RelationToContent> contentUserDoesntLike) {
        super(name, email, phone, pict);
        this.myList = myList;
        this.contentUserLikes = contentUserLikes;
        this.contentUserDoesntLike = contentUserDoesntLike;
    }

    public ExtensiveUser(Key key, String name, String email, String phone, String pict, ArrayList<RelationToContent> myList, ArrayList<RelationToContent> contentUserLikes, ArrayList<RelationToContent> contentUserDoesntLike) {
        super(key, name, email, phone, pict);
        this.myList = myList;
        this.contentUserLikes = contentUserLikes;
        this.contentUserDoesntLike = contentUserDoesntLike;
    }


    public ExtensiveUser(Key key) {
        super(key);
        generateRelations();
    }

    public ExtensiveUser(String id) {
        super(id);
        generateRelations();
    }

    public void generateRelations(){
        Query.Filter lastNameFilter =
                new Query.FilterPredicate("lastName",
                        Query.FilterOperator.EQUAL,
                        targetLastName);

        Query.Filter cityFilter =
                new Query.FilterPredicate("city",
                        Query.FilterOperator.EQUAL,
                        targetCity);

        Query.Filter birthYearMinFilter =
                new FilterPredicate("birthYear",
                        FilterOperator.GREATER_THAN_OR_EQUAL,
                        minBirthYear);

        Filter birthYearMaxFilter =
                new FilterPredicate("birthYear",
                        FilterOperator.LESS_THAN_OR_EQUAL,
                        maxBirthYear);

        Filter validFilter = CompositeFilterOperator.and(lastNameFilter,
                cityFilter,
                birthYearMinFilter,
                birthYearMaxFilter);

        Query q = new Query("Person").setFilter(validFilter);
        List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

    }

    public void putInDBWithRelations(){
        putInDB();
        putListInDB(myList);
        putListInDB(contentUserLikes);
        putListInDB(contentUserDoesntLike);

    }

    public void putListInDB(ArrayList<RelationToContent> relations){
        for(int i = 0; i<relations.size();i++){
            relations.get(i).putInDB();
        }
    }
}
