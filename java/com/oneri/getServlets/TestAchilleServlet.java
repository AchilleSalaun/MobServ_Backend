package com.oneri.getServlets;

import com.oneri.MyUtil;
import com.oneri.Recommendator.Recommendator;
import com.oneri.contentOriented.ExtensiveContent;
import com.oneri.userOriented.ExtensiveUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javafx.collections.transformation.SortedList;

/**
 * Created by Gaby on 16/11/2015.
 */
public class TestAchilleServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        if (email == null){
            int a = 0;
            return;
        }

        ExtensiveUser user1 = new ExtensiveUser(email,1);
        ArrayList<ExtensiveContent> list1 = Recommendator.recommend(user1);
        PrintWriter out = resp.getWriter();

        out.println(MyUtil.sortedListToXML(list1));
    }
}