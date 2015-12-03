package com.oneri.deleteServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.oneri.MyUtil;
import com.oneri.SuperClasses.User;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 03/12/2015.
 */
public class deleteContactServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        if (email == null){
            return;
        }

        Key userKey = KeyFactory.createKey("Contact", email);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        datastore.delete(userKey);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("Contact" + email + "with Key " +userKey.toString() + "a bien été effacé de la BDD");
        return;
    }
}
