package com.javafinal.WebMail.Controller;

import MyUtil.InputValidate;
import com.javafinal.WebMail.Constant.Role;
import com.javafinal.WebMail.Model.User;
import com.javafinal.WebMail.Service.UserService;
import com.javafinal.WebMail.ViewModel.RegisterRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/email_list")
    public String index(HttpServletResponse response){
        // TODO: add check login
        try {
            response.sendRedirect("/test");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "/email/list";
    }

    @GetMapping("/register")
    public String register(){
        return "/LoginAndRegister/Register";
    }

//    @PostMapping( value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
// khi co json thi moi lam cai nay

    @PostMapping("/register")
    public String AddAccount(RegisterRequest rr, Model model){
        String errorMsg = InputValidate.validateRegisterInput(rr);
        if(errorMsg != null)
            model.addAttribute("ErrorMsg",errorMsg);
        else{
            User u = new User(rr.getName(),rr.getEmail(), rr.getPassword(),
                    rr.getPhone(),"avtDefault.png", "ROLE_USER");

            int i = userService.findUserByNameandEmailandPhone(u);
            if(i==-1) {
                int id = userService.addUser(u).getUserId();
                model.addAttribute("SuccessMsg","Your account has been created");
                return "/LoginAndRegister/Register";
            }
            if(i==1){
                model.addAttribute("ErrorMsg","Your name is exist");
            }
            if(i==2){
                model.addAttribute("ErrorMsg","Your number phone is exist");
            }
            if(i==3){
                model.addAttribute("ErrorMsg","Your email is exist");
            }
        }
        return "/LoginAndRegister/Register";
    }


    @GetMapping("/signin")
    public String login(){
        return "/LoginAndRegister/Login";
    }

   /* @PostMapping("/signin")  //redirect toi trang ma mong mun
    public String loginAccount(LoginRequest LR, Model model) {
        String url;

        if(LR.getEmail().equals("")){
            model.addAttribute("ErrorMsg","Your email is empty");
            url = "/LoginAndRegister/Login";
        }
        else if(!LR.getEmail().split("@")[1].contains("tdtu.vn")){
            model.addAttribute("ErrorMsg","Domain Email must be tdtu.vn");
            url = "/LoginAndRegister/Login";
        }
        else if(LR.getPassword().equals("")){
            model.addAttribute("ErrorMsg","your password is empty");
            url = "/LoginAndRegister/Login";
        }
        else{
            int check = userService.findUserByEmailAndPassword(LR.getEmail(),LR.getPassword());
            if(check == 1){
                model.addAttribute("SuccessMsg","Your account login successful with admin");
                url = "/LoginAndRegister/Login";
            }
            else if(check == 0){
                model.addAttribute("SuccessMsg","Your account login successful with client");
                url = "/email/list";

            }
            else{
                model.addAttribute("ErrorMsg","Wrong account");
                url = "/LoginAndRegister/Login";
            }
        }
        return  url;

    }*/

    @GetMapping("/forgotPassword/forgotChangePassword")
    public String forgotChangePassword(){
        return "/LoginAndRegister/forgotPassword_changePass";
    }
}
