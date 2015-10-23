package com.oneri.User;

import com.google.appengine.api.datastore.PhoneNumber;
import com.oneri.content.RelationToContent;

import java.util.ArrayList;

/**
 * Created by Gaby on 23/10/2015.
 */
public class User {

    private String id = "undefined id";
    private String name = "undefined name";
    private String email = "undefined email";
    private String phone = "undefined phone number";
    private String pict = "undefined pict url";
    private ArrayList<RelationToContent> myList;
    private ArrayList<RelationToContent> contentUserLikes;
    private ArrayList<RelationToContent> contentUserDoesntLike;

    public User(String id, String name, String email, String phone,
                String pict, ArrayList<RelationToContent> myList,
                ArrayList<RelationToContent> contentUserLikes,
                ArrayList<RelationToContent> contentUserDoesntLike) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pict = pict;
        this.myList = myList;
        this.contentUserLikes = contentUserLikes;
        this.contentUserDoesntLike = contentUserDoesntLike;
    }

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

    public ArrayList<RelationToContent> getMyList() {
        return myList;
    }

    public ArrayList<RelationToContent> getContentUserLikes() {
        return contentUserLikes;
    }

    public ArrayList<RelationToContent> getContentUserDoesntLike() {
        return contentUserDoesntLike;
    }

    public String getId() {
        return id;
    }

}
