package com.oneri.getServlets;

import com.oneri.MyUtil;
import com.oneri.Recommendator.AttributeException;
import com.oneri.Recommendator.Recommendator;
import com.oneri.contentOriented.ExtensiveContent;
import com.oneri.userOriented.ExtensiveUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Achille on 03/12/2015.
 */
public class RecommendatorServlet extends HttpServlet
{

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {

        String email = req.getParameter("email");
        if (email == null){
            int a = 0;
            return;
        }

        String contentType = req.getParameter("contentType");
        if (contentType == null)
        {
            int a = 0;
            return;
        }
        int n_sample = Integer.parseInt(req.getParameter("n_sample"));

        if (n_sample == 0)
        {
            int a = 0;
            return;
        }
        int n_recommendator = Integer.parseInt(req.getParameter("n_recommendator"));
        if (n_recommendator == 0)
        {
            int a = 0;
            return;
        }
        ExtensiveUser user1 = new ExtensiveUser(email,1);
        Recommendator recommendator = null;
        try
        {
            recommendator = new Recommendator(n_sample,n_recommendator,contentType);
        } catch (AttributeException e)
        {
            e.printStackTrace();
            recommendator = new Recommendator() ;
        }

        ArrayList<ExtensiveContent> list1 = recommendator.recommend(user1);
        PrintWriter out = resp.getWriter();

        out.println(MyUtil.sortedListToJSON(list1));
    }
}
