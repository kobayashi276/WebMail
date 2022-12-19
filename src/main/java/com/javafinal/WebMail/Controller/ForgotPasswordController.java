package com.javafinal.WebMail.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    @GetMapping("")
    public String forgot(){
        return "/LoginAndRegister/forgotPassword";
    }
    @PostMapping("")
    public String NextforgotPassword(){
        String url = "";

        //if sai thi o lai
//        url = "/LoginAndRegister/forgotPassword_changePass";

        // if done het thi
        url = "/LoginAndRegister/forgotPassword_changePass";

        return url;
    }



}
