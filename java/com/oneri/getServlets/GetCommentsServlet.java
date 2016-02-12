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
        PrintWriter out = resp.getWriter();
        String title = req.getParameter("title");
        if (title == null){
            out.println("no title");
            return;
        }

        String contentType = req.getParameter("contentType");
        if (contentType == null){
            out.println("no contentType");
            return;
        }

        ExtensiveContent extensiveContent = new ExtensiveContent(title, contentType);


        resp.setContentType("application/json");
        out.println(extensiveContent.commentsToJSON());
        return;
    }
}
