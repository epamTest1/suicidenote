/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.suicidenote.servlets;

import com.my.suicidenote.common.Parameters;
import com.my.suicidenote.db.NoteHelper;
import com.my.suicidenote.db.object.Note;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Oleksandr_Shcherbyna
 */
public class NoteServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Note note = new Note();
        
        note.setFrom(request.getParameter(Parameters.FROM));
        note.setSay(request.getParameter(Parameters.SAY));
        note.setSent(false);
        // its can be a couple of sender
        StringBuilder sendTo = new StringBuilder();
        sendTo.append(request.getParameter(Parameters.SEND_TO)).append(",");
        int i = 0;
        while (request.getParameter(Parameters.SEND_TO + "-" + i) != null) {
            sendTo.append(request.getParameter(Parameters.SEND_TO + "-" + i)).append(",");
            i++;
        }
        note.setSentTo(sendTo.toString());
        
        note.setTo(request.getParameter(Parameters.TO));
        note.setWhen(request.getParameter(Parameters.WHEN));
        
        NoteHelper.incertNote(note);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servelt insert user note in db";
    }// </editor-fold>
    
    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
