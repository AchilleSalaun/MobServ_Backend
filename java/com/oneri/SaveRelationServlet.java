package com.oneri;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 17/10/2015.
 */
public class SaveRelationServlet  extends javax.servlet.http.HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("You called the get method on the SaveRelationServlet");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Retrieve informations from the URL
        String userId = req.getParameter("userId");
        String contentId = req.getParameter("contentId");
        String relationType = req.getParameter("relationType");
        String comment = req.getParameter("comment");

        // Take a reference of the datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Generate or retrieve the key associated with an existent relation
        // Create or modify the entity associated with the relation
        Entity relation;

        //This line means that the contact key and content key are assembled to make the key
        relation = new Entity("Relation", userId + contentId);
        relation.setProperty("UserId", userId);
        relation.setProperty("ContentId", contentId);
        relation.setProperty("RelationType", relationType);
        relation.setProperty("Comment", comment);

        // Save in the Datastore
        datastore.put(relation);
        resp.getWriter().println("Relation saved with key " +
                KeyFactory.keyToString(relation.getKey()) + "!");

        //Go to appengine.google.com to see the DB
    }

}
