package com.oneri.contentOriented;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.oneri.SuperClasses.Content;
import com.oneri.userOriented.RelationToContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaby on 24/10/2015.
 */
public class ExtensiveContent extends Content{

    private ArrayList<RelationToContent> onListOf;
    private ArrayList<RelationToContent> usersWhoLikes;
    private ArrayList<RelationToContent> usersWhoDoesntLike;

    public ExtensiveContent(String commercialLink, String contentType, String creator, String description, String imageURL, String title,
                            ArrayList<RelationToContent> onListOf, ArrayList<RelationToContent> usersWhoLikes, ArrayList<RelationToContent> userWhoDoesntLike) {
        super(commercialLink,contentType,creator,description,imageURL,title);
        this.onListOf = onListOf;
        this.usersWhoLikes = usersWhoLikes;
        this.usersWhoDoesntLike = userWhoDoesntLike;
    }

    public ExtensiveContent(Key key, String commercialLink, String contentType, String creator, String description, String imageURL, String title,
                            ArrayList<RelationToContent> onListOf, ArrayList<RelationToContent> usersWhoLikes, ArrayList<RelationToContent> userWhoDoesntLike) {
        super(key, commercialLink,contentType,creator,description,imageURL,title);
        this.onListOf = onListOf;
        this.usersWhoLikes = usersWhoLikes;
        this.usersWhoDoesntLike = userWhoDoesntLike;
    }


    public ExtensiveContent(Key key) {
        super(key);
        generateRelations();
    }

    public ExtensiveContent(String id) {
        super(id);
        generateRelations();
    }

    public void generateRelations(){
        generateContentList("waiting","myList");
        generateContentList("likes", "contentUserLikes");
        generateContentList("doesn't like", "userWhoDoesntLike");
    }

    //Cette fonction est à revoir (mais fonctionnne)
    public void generateContentList(String filter,String list){
        Query.Filter userFilter =
                new Query.FilterPredicate("ContentId",
                        Query.FilterOperator.EQUAL,
                        getId());

        Query.Filter relationTypeFilter =
                new Query.FilterPredicate("RelationType",
                        Query.FilterOperator.EQUAL,
                        filter);

        Query.Filter validFilter = Query.CompositeFilterOperator.and(userFilter, relationTypeFilter);

        Query q = new Query("Relation").setFilter(validFilter);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> contents = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        switch (list){
            case "myList":
                this.onListOf = new ArrayList<RelationToContent>();
                for(int i = 0; i<contents.size(); i++){
                    this.onListOf.add(new RelationToContent(contents.get(i).getKey()));
                }
                break;
            case "contentUserLikes":
                this.usersWhoLikes = new ArrayList<RelationToContent>();
                for(int i = 0; i<contents.size(); i++){
                    this.usersWhoLikes.add(new RelationToContent(contents.get(i).getKey()));
                }
                break;
            case "userWhoDoesntLike":
                this.usersWhoDoesntLike = new ArrayList<RelationToContent>();
                for(int i = 0; i<contents.size(); i++){
                    this.usersWhoDoesntLike.add(new RelationToContent(contents.get(i).getKey()));
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

    public void putListInDB(ArrayList<RelationToContent> relations){
        for(int i = 0; i<relations.size();i++){
            relations.get(i).putInDB();
        }
    }

    /* GETTERS */
    public ArrayList<RelationToContent> getUsersWhoLikes() {return this.usersWhoLikes ;}
    public ArrayList<RelationToContent> getUsersWhoDoesntLike() {return this.usersWhoDoesntLike ;}

}
