package com.my.suicidenote.controllers;

import com.my.suicidenote.mail.Postman;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrii_Manuiev
 */
public class MailScheduler extends HttpServlet {
    // add a new job - clear the session which less then one day
    Postman postman = new Postman();
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Mail Schedular start");
        super.init(config);
        postman.init();
        postman.start();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    
    @Override
    public void destroy() {
        System.out.println("Mail Schedular stop");
        postman.stop();
    }
}