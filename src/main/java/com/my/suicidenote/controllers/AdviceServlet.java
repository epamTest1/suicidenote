package com.my.suicidenote.controllers;

import com.my.suicidenote.db.AdviceHelper;
import com.my.suicidenote.db.object.Advice;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Andrii_Manuiev
 */
@Controller
public class AdviceServlet {

    @RequestMapping(value = "/advice.json", method = {GET, POST})
    public @ResponseBody
    Advice getRandomAdviceAsJSON() throws IOException {
        return getRandomAdvice();
    }

    @RequestMapping(value = "/advice.html", method = {GET, POST})
    public @ResponseBody
    String getRandomAdviceAsText() throws IOException {
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