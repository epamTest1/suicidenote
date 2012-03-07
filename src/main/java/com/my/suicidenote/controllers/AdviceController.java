package com.my.suicidenote.controllers;

import com.my.suicidenote.dto.Advice;
import com.my.suicidenote.repo.AdviceRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdviceController {

    @Autowired
    AdviceRepository repository;

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
        List<Advice> adviceList = repository.findAll();
        if (!adviceList.isEmpty()) {
            int idx = (int) (Math.random() * (adviceList.size()));
            result = adviceList.get(idx);
        }
        //response.setContentType("application/json");      
        return result;
    }
}