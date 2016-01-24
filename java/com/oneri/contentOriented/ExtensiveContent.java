package com.oneri.contentOriented;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.XML;
import com.oneri.SuperClasses.Content;
import com.oneri.SuperClasses.User;
import com.oneri.database.ObjectFromDB;
import com.oneri.userOriented.RelationToContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaby on 24/10/2015.
 */
public class ExtensiveContent extends Content{

    private ArrayList<RelationToUser> onListOf;
    private ArrayList<RelationToUser> usersWhoLikes;
    private ArrayList<RelationToUser> usersWhoDoesntLike;

    public ExtensiveContent(String commercialLink, String contentType, String creator, String description, String imageURL, String title,
                            ArrayList<RelationToUser> onListOf, ArrayList<RelationToUser> usersWhoLikes, ArrayList<RelationToUser> userWhoDoesntLike) {
        super(commercialLink,contentType,creator,description,imageURL,title);
        this.onListOf = onListOf;
        this.usersWhoLikes = usersWhoLikes;
        this.usersWhoDoesntLike = userWhoDoesntLike;
    }

    public ExtensiveContent(Key key, String commercialLink, String contentType, String creator, String description, String imageURL, String title,
                            ArrayList<RelationToUser> onListOf, ArrayList<RelationToUser> usersWhoLikes, ArrayList<RelationToUser> userWhoDoesntLike) {
        super(key, commercialLink,contentType,creator,description,imageURL,title);
        this.onListOf = onListOf;
        this.usersWhoLikes = usersWhoLikes;
        this.usersWhoDoesntLike = userWhoDoesntLike;
    }

    public ExtensiveContent(Content content){
        super(content.getTitle(), content.getContentType());
    }

    public ExtensiveContent(String title, String contentType) {
        super(title, contentType);
    }

    //relationType=waiting, likes or doesn't like
    public void generateContentList(String relationType){
        Query.Filter titleFilter =
                new Query.FilterPredicate("Title",
                        Query.FilterOperator.EQUAL,
                        getTitle());
        Query.Filter contentTypeFilter =
                new Query.FilterPredicate("ContentType",
                        Query.FilterOperator.EQUAL,
                        getContentType());

        Query.Filter relationTypeFilter =
                new Query.FilterPredicate("RelationType",
                        Query.FilterOperator.EQUAL,
                        relationType);

        Query.Filter validFilter = Query.CompositeFilterOperator.and(titleFilter, contentTypeFilter, relationTypeFilter);

        Query q = new Query("Relation").setFilter(validFilter);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> contents = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        switch (relationType){
            case "waiting":
                this.onListOf = new ArrayList<RelationToUser>();
                for(int i = 0; i<contents.size(); i++){
                    this.onListOf.add(new RelationToUser(contents.get(i)));
                }
                break;
            case "likes":
                this.usersWhoLikes = new ArrayList<RelationToUser>();
                for(int i = 0; i<contents.size(); i++){
                    this.usersWhoLikes.add(new RelationToUser(contents.get(i)));
                }
                break;
            case "doesn't like":
                this.usersWhoDoesntLike = new ArrayList<RelationToUser>();
                for(int i = 0; i<contents.size(); i++){
                    this.usersWhoDoesntLike.add(new RelationToUser(contents.get(i)));
                }
                break;
            default: System.out.println("regarde dans le switch");
        }
    }

    public void putInDBWithRelations(){
        putInDB();
        putListInDB(onListOf);
        putListInDB(usersWhoLikes);
        putListInDB(usersWhoDoesntLike);

    }

    public void putListInDB(ArrayList<RelationToUser> relations){
        for(int i = 0; i<relations.size();i++){
            relations.get(i).putInDB();
        }
    }

    /* GETTERS */
    public ArrayList<RelationToUser> getUsersWhoLikes() {
        if(this.usersWhoLikes == null)
            generateContentList("likes");
        return this.usersWhoLikes ;
    }

    public ArrayList<RelationToUser> getUsersWhoDoesntLike() {
        if(this.usersWhoDoesntLike == null)
            generateContentList("doesn't like");
        return this.usersWhoDoesntLike ;}

    public ArrayList<RelationToUser> getOnListOf() {
        if(onListOf == null)
            generateContentList("waiting");
        return onListOf;
    }

    public String commentsToJSON(){
        JSONArray results = new JSONArray();
        ObjectFromDB content;
        for (int i = 0; i < getUsersWhoLikes().size(); i++) {
            JSONObject relationJSON = new JSONObject();
            RelationToUser relation = usersWhoLikes.get(i);
            if(relation.getComment().equals( "AUCUN COMMENTAIRE DANS LA V1"))
                continue;
            relation.toJSON(relationJSON);
            results.put(relationJSON);

            //JSONObject userJSON = new JSONObject();
            //User user = usersWhoLikes.get(i).getUser();
            //user.toJSON(userJSON);
            //results.put(userJSON);
        }
        return results.toString();
    }
}
