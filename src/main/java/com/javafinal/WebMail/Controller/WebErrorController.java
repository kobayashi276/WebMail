package com.javafinal.WebMail.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class WebErrorController implements ErrorController {

    @GetMapping("/error")
    public String error(HttpServletRequest request, Model model){
        int errorCode = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String url = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        model.addAttribute("code",errorCode);
        model.addAttribute("url",url);
        return "error";
    }
}
