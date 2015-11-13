package com.oneri;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.XML;
import com.oneri.SuperClasses.Content;
import com.oneri.SuperClasses.User;
import com.oneri.contentOriented.ExtensiveContent;
import com.oneri.database.ObjectFromDB;
import com.oneri.userOriented.ExtensiveUser;
import com.oneri.userOriented.RelationToContent;

import java.util.ArrayList;

/**
 * Created by Gaby on 29/10/2015.
 */
public class MyUtil {

    public static String usersListToXML(ArrayList<User> myList){
        JSONArray results = new JSONArray();
        ObjectFromDB content;
        for (int i = 0; i < myList.size(); i++) {
            JSONObject contactJSON = new JSONObject();
            content = myList.get(i);
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
    }

    public static String contentsListToXML(ArrayList<Content> myList){
        JSONArray results = new JSONArray();
        ObjectFromDB content;
        for (int i = 0; i < myList.size(); i++) {
            JSONObject contactJSON = new JSONObject();
            content = myList.get(i);
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
    }

    public static ArrayList<ExtensiveContent> toArray(ExtensiveUser user){
        ArrayList<ExtensiveContent> myList = new ArrayList<>();
        for(int i = 0; i<user.getContentUserLikes().size();i++){
            myList.add((ExtensiveContent) user.getContentUserLikes().get(i).getContent());
        }
        return myList;
    }
}
