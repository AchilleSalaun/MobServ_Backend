package com.oneri.getServlets;

import com.oneri.MyUtil;
import com.oneri.SuperClasses.Content;
import com.oneri.userOriented.ExtensiveUser;
import com.oneri.userOriented.RelationToContent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaby on 28/10/2015.
 */
public class GetMyContentServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        if (email == null){
            return;
        }
        String type = req.getParameter("type");
        if (type == null){
            return;
        }

        ExtensiveUser user = new ExtensiveUser(email,0);
        PrintWriter out = resp.getWriter();

        resp.setContentType("application/xml");
        ArrayList<Content> myList = new ArrayList<>();
        ArrayList<RelationToContent> relationList = new ArrayList<>();
        switch(type){
            case "myList":
                relationList = user.getMyList();
                break;
            case "contentUserDoesntLike":
                relationList = user.getContentUserDoesntLike();
                break;
            default:
                return;
        }

        for(int i = 0; i<relationList.size();i++){
            myList.add(relationList.get(i).getContent());
        }

        out.println(MyUtil.contentsListToXML(myList));
        return;
    }
}
