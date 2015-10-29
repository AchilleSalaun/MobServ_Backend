package com.oneri.getServlets;

import com.oneri.userOriented.ExtensiveUser;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 28/10/2015.
 */
public class GetRecommendationsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        if (email == null){
            return;
        }

        String contentType = req.getParameter("contentType");
        if (contentType == null){
            return;
        }

        ExtensiveUser user = new ExtensiveUser(email,0);
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/xml");
        out.println(user.myListsToXML());
        return;
    }
}
