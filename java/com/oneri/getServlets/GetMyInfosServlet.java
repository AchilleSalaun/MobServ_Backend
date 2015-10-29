package com.oneri.getServlets;

import com.oneri.MyUtil;
import com.oneri.SuperClasses.Content;
import com.oneri.SuperClasses.User;
import com.oneri.userOriented.ExtensiveUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 28/10/2015.
 */
public class GetMyInfosServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        if (email == null){
            return;
        }

        User user = new User(email,0);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/xml");
        ArrayList<User> myList = new ArrayList<>();
        myList.add(user);
        out.println(MyUtil.usersListToXML(myList));
        return;
    }

}
