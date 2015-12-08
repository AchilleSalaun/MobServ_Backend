package com.oneri.getServlets;

import com.oneri.contentOriented.ExtensiveContent;
import com.oneri.userOriented.ExtensiveUser;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 28/10/2015.
 */
public class GetCommentsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String title = req.getParameter("title");
        if (title == null){
            return;
        }

        String contentType = req.getParameter("contentType");
        if (contentType == null){
            return;
        }

        ExtensiveContent extensiveContent = new ExtensiveContent(title, contentType);
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/json");
        out.println(extensiveContent.commentsToJSON());
        return;
    }
}
