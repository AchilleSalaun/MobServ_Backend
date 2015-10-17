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
public class SaveContentServlet extends javax.servlet.http.HttpServlet{

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("You called the get method on the SaveContentServlet");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Retrieve informations from the URL
        String title = req.getParameter("title");
        String contentType = req.getParameter("contentType");
        String creator = req.getParameter("creator");
        String imageURL = req.getParameter("imageURL");
        String description = req.getParameter("description");
        String commercialLink = req.getParameter("commercialLink");

        // Take a reference of the datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Generate or retrieve the key associated with an existent contact
        // Create or modify the entity associated with the contact
        Entity content;

        //This line means that the title and contentType are assembled to make the key
        content = new Entity("Content", title + contentType);
        content.setProperty("Title", title);
        content.setProperty("ContentType", contentType);
        content.setProperty("Creator", creator);
        content.setProperty("ImageURL", imageURL);
        content.setProperty("Description",description);
        content.setProperty("CommercialLink",commercialLink);

        // Save in the Datastore
        datastore.put(content);
        resp.getWriter().println("Content"+ title + " saved with key " +
                KeyFactory.keyToString(content.getKey()) + "!");

        //Go to appengine.google.com to see the DB
    }


}
