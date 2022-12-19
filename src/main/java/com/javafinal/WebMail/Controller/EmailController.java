package com.javafinal.WebMail.Controller;

import com.javafinal.WebMail.Constant.Archive;
import com.javafinal.WebMail.Model.Email;
import com.javafinal.WebMail.Model.EmailRepository;

import com.javafinal.WebMail.Service.QueryService;
import com.javafinal.WebMail.ViewModel.EmailBasic;
import com.javafinal.WebMail.ViewModel.EmailInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private QueryService queryService;

    @Autowired
    private EmailRepository repository;

    @PostMapping("/send")
    public void send(HttpServletResponse response, EmailInput emailInput){
        Boolean result = true;
        if(emailInput.getArchive().contains(Archive.INBOX))
            result = queryService.sendAnEmail(emailInput);
        else if (emailInput.getArchive().contains(Archive.DRAFT)) {
            result = queryService.saveADraft(emailInput);
        }
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/updateArchive")
    @ResponseBody
    public String updateArchive(
            @RequestParam(value = "id", required = false) int emailId,
            @RequestParam(value = "sender", required = false) String senderEmail,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) String date
            , HttpServletResponse response)
    {
        EmailBasic emailBasic = new EmailBasic(emailId,senderEmail,title,date);
        queryService.toggleEmailStarArchive(emailBasic);
        return emailBasic.toString();
    }

    @GetMapping("/viewDetail")
    @ResponseBody
    public String viewDetail(
            @RequestParam(value = "id", required = false) int emailId,
            @RequestParam(value = "sender", required = false) String senderEmail,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) String date
            , HttpServletResponse response)
    {
        EmailBasic emailBasic = new EmailBasic(emailId,senderEmail,title,date);
        return emailBasic.toString();
    }

    @GetMapping("/viewSent")
    @ResponseBody
    public String viewSent(){
       return "Sent";
    }

    @GetMapping("/write")
    public String write(){
        return "email/write";
    }

    @PostMapping("/write")
    public void write(HttpServletResponse response, Email email){
        repository.save(email);
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/delete")
    @ResponseBody
    public String delete(
            @RequestParam(value = "id", required = false) int emailId,
            @RequestParam(value = "sender", required = false) String senderEmail,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "date", required = false) String date
            , HttpServletResponse response)
    {
        EmailBasic emailBasic = new EmailBasic(emailId,senderEmail,title,date);
        boolean result = queryService.deleteEmail(emailBasic);
        return emailBasic.toString();
    }

}
