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
    public ExtensiveUser(String email, int dumbVarialble){
        super(email,dumbVarialble);
        generateRelations();
    }

    public void generateRelations(){
        generateContentList("waiting","myList");
        generateContentList("likes", "contentUserLikes");
        generateContentList("doesn't like", "contentUserDoesntLike");
    }

    //Cette fonction est à revoir (mais fonctionnne)
    public void generateContentList(String filter,String list){
        Query.Filter userFilter =
                new Query.FilterPredicate("UserId",
                        Query.FilterOperator.EQUAL,
                        getId());

        Query.Filter relationTypeFilter =
                new Query.FilterPredicate("RelationType",
                        Query.FilterOperator.EQUAL,
                        filter);

        Filter validFilter = CompositeFilterOperator.and(userFilter,relationTypeFilter);

        Query q = new Query("Relation").setFilter(validFilter);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> contents = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        switch (list){
            case "myList":
                this.myList = new ArrayList<RelationToContent>();
                for(int i = 0; i<contents.size(); i++){
                    this.myList.add(new RelationToContent(contents.get(i).getKey()));
                }
                break;
            case "contentUserLikes":
                this.contentUserLikes = new ArrayList<RelationToContent>();
                for(int i = 0; i<contents.size(); i++){
                    this.contentUserLikes.add(new RelationToContent(contents.get(i).getKey()));
                }
                break;
            case "contentUserDoesntLike":
                this.contentUserDoesntLike = new ArrayList<RelationToContent>();
                for(int i = 0; i<contents.size(); i++){
                    this.contentUserDoesntLike.add(new RelationToContent(contents.get(i).getKey()));
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
/*
    public String myListsToXML(){

        JSONArray results = new JSONArray();
        Content content;
        for (int i = 0; i < myList.size(); i++) {
            JSONObject contactJSON = new JSONObject();
            content = myList.get(i).getContent();
            content.toJSON(contactJSON);
            results.put(contactJSON);
        }
        String xml = null;
        try {
            xml = XML.toString(results, "song");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return results.toString();
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><music>" + xml + "</music>";
    }*/

    public void putListInDB(ArrayList<RelationToContent> relations){
        for(int i = 0; i<relations.size();i++){
            relations.get(i).putInDB();
        }
    }

    public ArrayList<RelationToContent> getMyList() {
        return myList;
    }

    public void setMyList(ArrayList<RelationToContent> myList) {
        this.myList = myList;
    }

    public ArrayList<RelationToContent> getContentUserLikes() {
        return contentUserLikes;
    }

    public void setContentUserLikes(ArrayList<RelationToContent> contentUserLikes) {
        this.contentUserLikes = contentUserLikes;
    }

    public ArrayList<RelationToContent> getContentUserDoesntLike() {
        return contentUserDoesntLike;
    }

    public void setContentUserDoesntLike(ArrayList<RelationToContent> contentUserDoesntLike) {
        this.contentUserDoesntLike = contentUserDoesntLike;
    }
}
