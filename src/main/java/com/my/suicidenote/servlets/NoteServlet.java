/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.suicidenote.servlets;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.my.suicidenote.common.Parameters;
import com.my.suicidenote.db.MongoDB;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Oleksandr_Shcherbyna
 */
public class NoteServlet extends HttpServlet {

    private static final String NOTES_COLLECTION_NAME = "notes";

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBCollection collection = MongoDB.findCollection(NOTES_COLLECTION_NAME);
        
        BasicDBObject document = new BasicDBObject();
	document.put("database", MongoDB.dbname);
	document.put("table", NOTES_COLLECTION_NAME);
        
        Map<String, Object> documentDetail = new HashMap<String, Object>();
        documentDetail.put(Parameters.TO, request.getParameter(Parameters.TO));
        documentDetail.put(Parameters.SAY, request.getParameter(Parameters.SAY));
        documentDetail.put(Parameters.WHEN, request.getParameter(Parameters.WHEN));
        documentDetail.put(Parameters.FROM, request.getParameter(Parameters.FROM));
        documentDetail.put(Parameters.SENT, false);
        
        StringBuilder sendTo = new StringBuilder();
        sendTo.append(request.getParameter(Parameters.SEND_TO)).append(",");
        int i = 0;
        while (request.getParameter(Parameters.SEND_TO + "-" + i) != null) {
            sendTo.append(request.getParameter(Parameters.SEND_TO + "-" + i)).append(",");
            i++;
        }
        documentDetail.put(Parameters.SEND_TO, sendTo.toString());
        
        document.put(Parameters.DETAIL, documentDetail);
                
        collection.insert(new BasicDBObject(document));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
        }
}
