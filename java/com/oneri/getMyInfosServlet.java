package com.oneri;

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
import com.oneri.userOriented.ExtensiveUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 25/10/2015.
 */
public class getMyInfosServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Take the list of contacts ordered by name
        Query query = new Query("Content").addSort("Title", Query.SortDirection.ASCENDING);
        List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

        String id = req.getParameter("id");
        ExtensiveUser user = new ExtensiveUser(id);
        // Let's output the basic HTML headers
        PrintWriter out = resp.getWriter();

        /** Different response type? */
        String responseType = req.getParameter("respType");
        if (responseType.equals("json")) {

            // Set header to JSON output
            resp.setContentType("application/json");
            out.println(getJSON(contents, req, resp));
            return;
        } else if (responseType.equals("xml")) {
            resp.setContentType("application/xml");
            out.println(user.myListsToXML());
            return;
        }
    }

    private String getJSON(List<Entity> contents, HttpServletRequest req, HttpServletResponse resp) {

        // Create a JSON array that will contain all the entities converted in a JSON version
        JSONArray results = new JSONArray();
        for (Entity content : contents) {
            JSONObject contactJSON = new JSONObject();
            try {
                contactJSON.put("id", KeyFactory.keyToString(content.getKey()));
                contactJSON.put("CommercialLink", content.getProperty("CommercialLink"));
                contactJSON.put("ContentType", content.getProperty("ContentType"));
                contactJSON.put("Creator", content.getProperty("Creator"));
                contactJSON.put("Description", content.getProperty("Description"));
                contactJSON.put("ImageURL", content.getProperty("ImageURL"));
                contactJSON.put("Title", content.getProperty("Title"));

            } catch (JSONException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            results.put(contactJSON);
        }
        return results.toString();
    }
}
