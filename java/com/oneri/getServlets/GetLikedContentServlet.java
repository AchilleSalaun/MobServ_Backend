package com.oneri.getServlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.XML;
import com.oneri.MyUtil;
import com.oneri.SuperClasses.Content;
import com.oneri.database.ObjectFromDB;
import com.oneri.userOriented.ExtensiveUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 25/10/2015.
 */
public class GetLikedContentServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        if (email == null){
            return;
        }

        ExtensiveUser user = new ExtensiveUser(email,0);
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/xml");
        ArrayList<Content> myList = new ArrayList<>();
        for(int i = 0; i<user.getContentUserLikes().size();i++){
            myList.add(user.getContentUserLikes().get(i).getContent());
        }
        out.println(MyUtil.contentsListToXML(myList));
        return;
    }

}
