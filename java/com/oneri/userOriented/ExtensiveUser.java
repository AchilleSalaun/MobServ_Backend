package com.oneri.userOriented;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.oneri.SuperClasses.User;

import java.util.ArrayList;

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

    public ExtensiveUser(Key key, ArrayList<RelationToContent> myList, ArrayList<RelationToContent> contentUserLikes, ArrayList<RelationToContent> contentUserDoesntLike) {
        super(key);
        this.myList = myList;
        this.contentUserLikes = contentUserLikes;
        this.contentUserDoesntLike = contentUserDoesntLike;
    }

    public ExtensiveUser(String id, ArrayList<RelationToContent> myList, ArrayList<RelationToContent> contentUserLikes, ArrayList<RelationToContent> contentUserDoesntLike) {
        super(id);
        this.myList = myList;
        this.contentUserLikes = contentUserLikes;
        this.contentUserDoesntLike = contentUserDoesntLike;
    }

    public void generateRelations(){

    }
}
