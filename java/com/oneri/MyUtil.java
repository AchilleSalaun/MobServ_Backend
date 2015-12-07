package com.oneri;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
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
import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

//import javafx.collections.transformation.SortedList;

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
        for(int i = 0; i<user.getContentUserLikes().size();i++)
        {
            myList.add(user.getContentUserLikes().get(i).getExtensiveContent());
        }
        return myList;
    }

    public static ArrayList<ExtensiveUser> userFromDB(int n)
    {
        ArrayList<ExtensiveUser> list = new ArrayList<ExtensiveUser>() ;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        // Take the list of contacts ordered by name
        Query query = new Query("Contact").addSort("Name", Query.SortDirection.ASCENDING);
        List<Entity> usersEntity = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        while(usersEntity.size()>0 && list.size()<n){
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(usersEntity.size());
            list.add(new ExtensiveUser(usersEntity.get(randomInt).getKey()));
            usersEntity.remove(randomInt);
        }
        return list ;
    }

    public static ArrayList<ExtensiveContent> contentFromDB(int n, String type) {
        ArrayList<ExtensiveContent> list = new ArrayList<ExtensiveContent>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        // Take the list of contacts ordered by name

        /*** Query à modifier : insertion du filtre "TYPE" ***/

        Query.Filter contentTypeFilter =
                new Query.FilterPredicate("ContentType",
                        Query.FilterOperator.EQUAL,
                        type);


        Query query = new Query("Content").addSort("Title", Query.SortDirection.ASCENDING);
        query.setFilter(contentTypeFilter);
        /*****************************************************/

        List<Entity> contentsEntity = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        while (contentsEntity.size() > 0 && list.size() < n) {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(contentsEntity.size());

            list.add(new ExtensiveContent(contentsEntity.get(randomInt).getKey()));
            contentsEntity.remove(randomInt);

        }
        return list;
    }
    public static int ressemblance(String[] query,String element){
        int result = 0;
        for(int i = 0; i<query.length;i++){
            if(element.toLowerCase().contains(query[i].toLowerCase()) ) {
                result ++;
            }
        }
        return result;
    }

    public static int bestRessemblance(String[] query,Entity entity){
        int result = 0;
        Map<String,Object> map = entity.getProperties();
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            String key   = (String)iterator.next();
            String value = (String) map.get(key);
            int a = ressemblance(query,value);
            if(a>result)
                result = a;
        }
        return result;
    }

    public static ArrayList<ContentToSort> getNonSortedResults(String[] query,ArrayList<Entity> entities){
        ArrayList<ContentToSort> result = new ArrayList<>();
        for(int i = 0;i<entities.size();i++){
            int ressemblance = bestRessemblance(query,entities.get(i));
            if(ressemblance>0){
                result.add(new ContentToSort(KeyFactory.keyToString(entities.get(i).getKey()),ressemblance));
            }
        }
        return result;
    }

    public static ArrayList<ContentToSort> getSortedResults(String[] query,ArrayList<Entity> entities){
        ArrayList<ContentToSort> result = getNonSortedResults(query,entities);
        Collections.sort(result);
        return result;
    }

    public static  ArrayList<Content> contentToSortToContent(ArrayList<ContentToSort> list){
        ArrayList<Content> result = new ArrayList<>();
        for(int i = 0; i<list.size(); i++){
            result.add(new Content(list.get(i).getId()));
        }
        return result;
    }

    public  static  String sortedListToXML(ArrayList<ExtensiveContent> myList){
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
}
