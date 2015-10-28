package com.oneri.postServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 16/10/2015.
 */
public class SaveContactServlet extends javax.servlet.http.HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("You called the get method on the SaveContactServlet");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Retrieve informations from the URL
        String userName = req.getParameter("name");
        String userPhone = req.getParameter("phone");
        String userEmail = req.getParameter("email");
        String userPict = req.getParameter("pict");

        // Take a reference of the datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Generate or retrieve the key associated with an existent contact
        // Create or modify the entity associated with the contact
        Entity contact;
        contact = new Entity("Contact", userEmail); //This line means that the email address is used as a key in the DB
        contact.setProperty("Name", userName);
        contact.setProperty("Phone", userPhone);
        contact.setProperty("Email", userEmail); //Two people can't have the same or the DB won't make the difference
        contact.setProperty("Pict", userPict);

        // Save in the Datastore
        datastore.put(contact);
        resp.getWriter().println("User " + userName + " saved with key " +
                KeyFactory.keyToString(contact.getKey()) + "!");

        //Go to appengine.google.com to see the DB
    }
}
