package com.oneri.getServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.oneri.MyUtil;
import com.oneri.SuperClasses.Content;
import com.oneri.userOriented.ExtensiveUser;

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
public class GetRecommendationsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");

        String contentType = req.getParameter("contentType");
        if (contentType == null){
            return;
        }

        Query q = new Query("Content")
                .setFilter(new Query.FilterPredicate("ContentType",
                        Query.FilterOperator.EQUAL,
                        contentType));

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> entityList = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        ArrayList<Content> contentList = new ArrayList<>();
        for(int i = 0; i<entityList.size(); i++){
            contentList.add(new Content(entityList.get(i).getKey()));
        }
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/json");
        out.println(MyUtil.contentsListToJSON(contentList));
        return;
    }
}
