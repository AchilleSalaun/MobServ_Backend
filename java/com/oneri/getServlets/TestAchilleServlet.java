package com.oneri.getServlets;

import com.oneri.MyUtil;
import com.oneri.Recommendator.Recommendator;
import com.oneri.contentOriented.ExtensiveContent;
import com.oneri.userOriented.ExtensiveUser;
import com.sun.javafx.collections.transformation.SortedList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 16/11/2015.
 */
public class TestAchilleServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        if (email == null){
            return;
        }

        ExtensiveUser user1 = new ExtensiveUser(email);
        SortedList<ExtensiveContent> list1 = Recommendator.recommend(user1);
        PrintWriter out = resp.getWriter();

        out.println(MyUtil.sortedListToXML(list1));
    }
}
