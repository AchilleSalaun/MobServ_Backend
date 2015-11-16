package com.oneri.getServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.oneri.ContentToSort;
import com.oneri.MyUtil;
import com.oneri.SuperClasses.Content;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 28/10/2015.
 */
public class GetSearchResultsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String request = req.getParameter("request");
        if (request == null){
            return;
        }

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Take the list of contacts ordered by name
        Query query = new Query("Content").addSort("Title", Query.SortDirection.ASCENDING);
        List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        ArrayList<Entity> entities= new ArrayList<>(contents);

        String[] words = request.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            words[i] = words[i].replaceAll("[^\\w]", "");
        }

        ArrayList<ContentToSort> contentToSorts= MyUtil.getNonSortedResults(words, entities);
        ArrayList<Content> results= MyUtil.contentToSortToContent(contentToSorts);
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/xml");
        out.println(MyUtil.contentsListToXML(results));
        return;
    }
}
