package com.oneri.userOriented;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.XML;
import com.oneri.SuperClasses.Content;
import com.oneri.SuperClasses.Relation;
import com.oneri.SuperClasses.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gaby on 24/10/2015.
 */
public class ExtensiveUser extends User{

    private ArrayList<RelationToContent> myList;
    private ArrayList<RelationToContent> contentUserLikes;
    private ArrayList<RelationToContent> contentUserDoesntLike;
    private HashMap<String, Boolean> tableInCache;

    public ExtensiveUser(String name, String email, String phone, String pict, ArrayList<RelationToContent> myList, ArrayList<RelationToContent> contentUserLikes, ArrayList<RelationToContent> contentUserDoesntLike) {
        super(name, email, phone, pict);
        this.myList = myList;
        this.contentUserLikes = contentUserLikes;
        this.contentUserDoesntLike = contentUserDoesntLike;
        initializeCache();
    }

    public ExtensiveUser(Key key, String name, String email, String phone, String pict, ArrayList<RelationToContent> myList, ArrayList<RelationToContent> contentUserLikes, ArrayList<RelationToContent> contentUserDoesntLike) {
        super(key, name, email, phone, pict);
        this.myList = myList;
        this.contentUserLikes = contentUserLikes;
        this.contentUserDoesntLike = contentUserDoesntLike;
        initializeCache();
    }

    public ExtensiveUser(Entity entity) {
        super(entity);
        initializeCache();
        this.myList = new ArrayList<>();
    }

    public ExtensiveUser(String email){
        super(email);
        initializeCache();
        this.myList = new ArrayList<>();
    }

    //Cette fonction est à revoir (mais fonctionnne)
    public void generateContentList(String relationType){
        Query.Filter userFilter =
                new Query.FilterPredicate("Email",
                        Query.FilterOperator.EQUAL,
                        getEmail());

        Query.Filter relationTypeFilter =
                new Query.FilterPredicate("RelationType",
                        Query.FilterOperator.EQUAL,
                        relationType);

        Filter validFilter = CompositeFilterOperator.and(userFilter,relationTypeFilter);

        Query q = new Query("Relation").setFilter(validFilter);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> contents = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        switch (relationType){
            case "waiting":
                this.myList = new ArrayList<>();
                for(int i = 0; i<contents.size(); i++){
                    this.myList.add(new RelationToContent(contents.get(i)));
                }
                break;
            case "likes":
                this.contentUserLikes = new ArrayList<>();
                for(int i = 0; i<contents.size(); i++){
                    this.contentUserLikes.add(new RelationToContent(contents.get(i)));
                }
                break;
            case "doesn't like":
                this.contentUserDoesntLike = new ArrayList<>();
                for(int i = 0; i<contents.size(); i++){
                    this.contentUserDoesntLike.add(new RelationToContent(contents.get(i)));
                }
                break;
            default: System.out.println("regarde dans le switch");
        }
    }

    public void generateContentList(String relationType,String contentType){
        Query.Filter userFilter =
                new Query.FilterPredicate("Email",
                        Query.FilterOperator.EQUAL,
                        getEmail());

        Query.Filter relationTypeFilter =
                new Query.FilterPredicate("RelationType",
                        Query.FilterOperator.EQUAL,
                        relationType);

        Query.Filter contentTypeFilter =
                new Query.FilterPredicate("ContentType",
                        Query.FilterOperator.EQUAL,
                        contentType);

        Filter validFilter = CompositeFilterOperator.and(userFilter,relationTypeFilter,contentTypeFilter);

        Query q = new Query("Relation").setFilter(validFilter);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> contents = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        switch (relationType){
            case "waiting":

                for(int i = 0; i<contents.size(); i++){
                    this.myList.add(new RelationToContent(contents.get(i)));
                }
                break;
            case "likes":
                this.contentUserLikes = new ArrayList<>();
                for(int i = 0; i<contents.size(); i++){
                    this.contentUserLikes.add(new RelationToContent(contents.get(i)));
                }
                break;
            case "doesn't like":
                this.contentUserDoesntLike = new ArrayList<>();
                for(int i = 0; i<contents.size(); i++){
                    this.contentUserDoesntLike.add(new RelationToContent(contents.get(i)));
                }
                break;
            default: System.out.println("regarde dans le switch");
        }
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

    //à gerer
    public ArrayList<RelationToContent> getMyList() {
        if(myList == null)
            generateContentList("waiting");
        return myList;
    }

    public ArrayList<RelationToContent> getMyList(String contentType) {
        if(myList == null)
            generateContentList("waiting");
        return myList;
    }

    //à gerer
    public ArrayList<RelationToContent> getContentUserLikes() {
        if(contentUserLikes == null)
            generateContentList("likes");
        return contentUserLikes;
    }

    //à gerer
    public ArrayList<RelationToContent> getContentUserDoesntLike() {
        if(contentUserDoesntLike == null)
            generateContentList("doesn't like");
        return contentUserDoesntLike;
    }

    public void setContentUserDoesntLike(ArrayList<RelationToContent> contentUserDoesntLike) {
        this.contentUserDoesntLike = contentUserDoesntLike;
    }
    public void setMyList(ArrayList<RelationToContent> myList) {
        this.myList = myList;
    }
    public void setContentUserLikes(ArrayList<RelationToContent> contentUserLikes) {
        this.contentUserLikes = contentUserLikes;
    }

    public void initializeCache(){
        this.tableInCache = new HashMap<>();
        String relationTypes[]= {"likes","doesn't like","waiting"};
        String contentTypes[]= {"movie","series","music","video game","comic","book","all"};
        for(String a: relationTypes){
            for(String b: contentTypes){
                this.tableInCache.put(a+b,false);
            }
        }

    }
}
