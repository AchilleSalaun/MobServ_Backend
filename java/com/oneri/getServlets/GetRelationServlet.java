package com.oneri.getServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.apphosting.api.DatastorePb;
import com.oneri.contentOriented.ExtensiveContent;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 07/12/2015.
 */
public class GetRelationServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String title = req.getParameter("title");
        if (title == null){
            return;
        }

        String contentType = req.getParameter("contentType");
        if (contentType == null){
            return;
        }

        String email = req.getParameter("email");
        if (email == null){
            return;
        }

        Key myKey = KeyFactory.createKey("Relation",email + title+contentType);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        resp.setContentType("application/json");

        try {
            Entity relation = datastore.get(myKey);
            resp.getWriter().println("[{\"RelationType\":\"" + relation.getProperty("RelationType")+"\"}]");

        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            resp.getWriter().println("[{\"RelationType\":\"no relation\"}]");
        }
        return;
    }
}
