package com.oneri.postServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 03/12/2015.
 */
public class PostURLServlet extends javax.servlet.http.HttpServlet{

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Retrieve informations from the URL
        String url = req.getParameter("url");


        ArrayList<String> informations = new ArrayList<>();

        //********** ICI EST LA FONCTION QUI RECUPERE LES INFOS DE WIKIPEDIA************
        //informations = ParseURL.getInfos(url);

        // Take a reference of the datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        String title = informations.get(0);
        String contentType = informations.get(1);
        String creator = informations.get(2);
        String imageURL = informations.get(3);
        String description = informations.get(4);
        String commercialLink = url;

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
                KeyFactory.keyToString(content.getKey()));

        //Go to appengine.google.com to see the DB
    }
}
