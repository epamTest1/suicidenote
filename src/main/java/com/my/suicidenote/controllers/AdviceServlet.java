package com.my.suicidenote.controllers;

import com.my.suicidenote.db.AdviceHelper;
import com.my.suicidenote.db.object.Advice;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 *
 * @author Andrii_Manuiev
 */
@Controller
public class AdviceServlet  {
    @RequestMapping(value = "/advice.json", method = { GET, POST })    
    public @ResponseBody Advice getRandomAdviceAsJSON() throws IOException {        
        return getRandomAdvice();
    }

    @RequestMapping(value = "/advice.html", method = { GET, POST })    
    public @ResponseBody String getRandomAdviceAsText() throws IOException {       
        return getRandomAdvice().getText();
    }

	private Advice getRandomAdvice() {
		Advice result = new Advice(Advice.DEFAULT_ADVICE_TEXT);        
        List<Advice> adviceList = AdviceHelper.getAdvices();        
        if (!adviceList.isEmpty()) {
            int idx = (int) (Math.random() * (adviceList.size()));
            result = adviceList.get(idx);
        }
        //response.setContentType("application/json");      
        return result;
	}
}