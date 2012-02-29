/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.suicidenote.servlets;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.my.suicidenote.db.MongoDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrii_Manuiev
 */
public class AdviceServlet extends HttpServlet {

    private static final String collection = "advices";

    List<String> adviceList = new ArrayList<String>();
            
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DBCollection advices = MongoDB.findCollection(collection);
        if (advices != null) {
            DBCursor cur = advices.find();
            while (cur.hasNext()) {
                DBObject advObj = cur.next();
                adviceList.add(advObj.get("text").toString());
            }
            cur.close();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String result = "It is your responsibility to make your dreams come true";

        if (!adviceList.isEmpty()) {
            int idx = (int) (Math.random() * (adviceList.size()));
            result = adviceList.get(idx);
        }

        response.setContentType("application/json");

        if (request.getParameter("text") != null) {
            response.getWriter().print(result);
        } else {
            response.getWriter().print(String.format("{ \"advice\": \"%s\"  } ", result));
        }
    }
}
