package com.oneri.getServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.oneri.MyUtil;
import com.oneri.SuperClasses.Content;
import com.oneri.contentOriented.ExtensiveContent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

    // Take the list of contacts ordered by name
    Query query = new Query("Content").addSort("Title", Query.SortDirection.ASCENDING);
    List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

    Random randomGenerator = new Random();
    int randomInt = randomGenerator.nextInt(contents.size());
    PrintWriter out = resp.getWriter();

    resp.setContentType("application/json");
        ArrayList<Content> list = new ArrayList<>();
        list.add(new Content(contents.get(randomInt).getKey()));
    out.println(MyUtil.contentsListToJSON(list));
    return;
    }
}
