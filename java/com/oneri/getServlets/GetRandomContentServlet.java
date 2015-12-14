package com.oneri.getServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.oneri.MyUtil;
import com.oneri.SuperClasses.Content;
import com.oneri.contentOriented.ExtensiveContent;
import com.oneri.userOriented.ExtensiveUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 28/10/2015.
 */
public class GetRandomContentServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        String email = req.getParameter("email");
        if (email == null){
            return;
        }

        Query.Filter emailFilter =
                new Query.FilterPredicate("Email",
                        Query.FilterOperator.EQUAL,
                        email);
        Query q =  new Query("Relation").setFilter(emailFilter);
        q.addProjection(new PropertyProjection("Title",String.class));
        q.addProjection(new PropertyProjection("ContentType",String.class));
        PreparedQuery pq = datastore.prepare(q);
        List<Entity> relationsEntities= pq.asList(FetchOptions.Builder.withDefaults());
        ArrayList<Content> relationContent = new ArrayList<Content>();
        for(Entity a: relationsEntities)
            relationContent.add(new Content((String)a.getProperty("Title"),(String)a.getProperty("ContentType")));

        // Take the list of contacts ordered by name
        Query query = new Query("Content").addSort("Title", Query.SortDirection.ASCENDING);
        query.addProjection(new PropertyProjection("Title",String.class));
        query.addProjection(new PropertyProjection("ContentType",String.class));
        List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        ArrayList<Content> listContent = new ArrayList<Content>();
        for(Entity b: contents)
            listContent.add(new Content((String)b.getProperty("Title"),(String)b.getProperty("ContentType")));

        Content random;
        while(true) {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(listContent.size());
            random = listContent.get(randomInt);
            if(relationContent.contains(random))
                listContent.remove(randomInt);
            else
                break;
        }
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/json");
        ArrayList<Content> list = new ArrayList<>();

        list.add(random);
        out.println(MyUtil.contentsListToJSON(list));

        return;
    }
}
