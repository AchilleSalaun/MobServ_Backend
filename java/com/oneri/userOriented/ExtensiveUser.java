package com.oneri.userOriented;

import com.oneri.SuperClasses.User;

import java.util.ArrayList;

/**
 * Created by Gaby on 24/10/2015.
 */
public class ExtensiveUser extends User{

    private ArrayList<RelationToContent> myList;
    private ArrayList<RelationToContent> contentUserLikes;
    private ArrayList<RelationToContent> contentUserDoesntLike;

    public ExtensiveUser( ArrayList<RelationToContent> myList,
                          ArrayList<RelationToContent> contentUserLikes,
                          ArrayList<RelationToContent> contentUserDoesntLike){

        this.myList = myList;
        this.contentUserLikes = contentUserLikes;
        this.contentUserDoesntLike = contentUserDoesntLike;
    }
}
