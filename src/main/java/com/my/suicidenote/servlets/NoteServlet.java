/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.suicidenote.servlets;

import com.my.suicidenote.common.Parameters;
import com.my.suicidenote.db.NoteHelper;
import com.my.suicidenote.db.object.Note;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Oleksandr_Shcherbyna
 */
public class NoteServlet extends HttpServlet {

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
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
        // recipient can be more then one
        StringBuilder sendTo = new StringBuilder();
        sendTo.append(request.getParameter(Parameters.SEND_TO)).append(",");
        int i = 0;
        while (request.getParameter(Parameters.SEND_TO + "-" + i) != null) {
            sendTo.append(request.getParameter(Parameters.SEND_TO + "-" + i)).append(",");
            i++;
        }
        note.setSentTo(sendTo.toString());
        note.setTo(request.getParameter(Parameters.TO));
        
        long datetime = new Date().getTime();
        try {
            datetime = sdf.parse(request.getParameter(Parameters.WHEN)).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        note.setWhen(datetime);
        
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