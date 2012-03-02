package com.my.suicidenote.servlets;

import com.my.suicidenote.db.AdviceHelper;
import com.my.suicidenote.db.object.Advice;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrii_Manuiev
 */
public class AdviceServlet extends HttpServlet {

    static final String ADVICE_BY_DEFAULT = "It is your responsibility to make your dreams come true";
    static final String RETURN_AS_TEXT = "astext";
            
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String result = null;
        
        List<Advice> adviceList = AdviceHelper.getAdvices();
        
        if (!adviceList.isEmpty()) {
            int idx = (int) (Math.random() * (adviceList.size()));
            result = adviceList.get(idx).getText();
        }

        response.setContentType("application/json");

        result = (result == null) ? ADVICE_BY_DEFAULT : result;
        
        if (request.getParameter(RETURN_AS_TEXT) != null) {
            response.getWriter().print(result);
        } else {
            response.getWriter().print(String.format("{ \"advice\": \"%s\"  } ", result));
        }
    }
}