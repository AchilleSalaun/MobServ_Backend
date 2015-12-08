package com.oneri.deleteServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 03/12/2015.
 */
public class deleteContentServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String title = req.getParameter("title");
        if (title == null){
            return;
        }

        String contentType = req.getParameter("contentType");
        if (contentType == null){
            return;
        }

        Key contentKey = KeyFactory.createKey("Content", title + contentType);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        datastore.delete(contentKey);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.println("Content" + title + "with Key " +contentKey.toString() + "a bien été effacé de la BDD");
        return;
    }
}
