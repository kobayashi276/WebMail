package com.javafinal.WebMail.Controller;

import com.javafinal.WebMail.Constant.Archive;
import com.javafinal.WebMail.Model.Label;
import com.javafinal.WebMail.Model.LabelRepository;
import com.javafinal.WebMail.Model.User;
import com.javafinal.WebMail.Model.UserRepository;
import com.javafinal.WebMail.Service.QueryService;
import com.javafinal.WebMail.UserSessionInfo;
import com.javafinal.WebMail.ViewModel.EmailBasic;
import com.javafinal.WebMail.UserSessionInfo;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHomeController {
    @Autowired
    private UserRepository repo;
    @Autowired
    private QueryService queryService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private LabelRepository labelRepo;

    private static final String MODEL_EMAILBASICLIST = "emailBasicList";
    private static final String MODEL_LISTSIZE = "listSize";

    private static final String MODEL_CURRENT_ARCHIVE = "archive";

    @ModelAttribute
    public void userDetail(Model model, Principal principal){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        UserSessionInfo.setCurrentUser(u);
        model.addAttribute("user", u);

        List<Label> lstLabel = labelRepo.findAll();
        model.addAttribute("listLabel", lstLabel);

    }

    @GetMapping("/whoami")
    @ResponseBody
    public String whoAmI(){
        return UserSessionInfo.getCurrentUser().toString();
    }

    @GetMapping("")
    public String home(Model model){
        List<EmailBasic> emailBasicList = queryService.loadReceivedBasicEmails();
        model.addAttribute(MODEL_EMAILBASICLIST,emailBasicList);
        model.addAttribute(MODEL_LISTSIZE,emailBasicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.INBOX);
        return "UserHome";
    }

    @GetMapping("/viewStar")
    public String viewStar(Model model){
        List<EmailBasic> basicList = queryService.loadReceivedBasicStarEmails();
        model.addAttribute(MODEL_EMAILBASICLIST,basicList);
        model.addAttribute(MODEL_LISTSIZE,basicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.STAR);
        return "UserHome";
    }

    @GetMapping("/viewTrash")
    public String viewTrash(Model model){
        List<EmailBasic> basicList = queryService.loadBasicEmailsByArchive(Archive.DELETE);
        model.addAttribute(MODEL_EMAILBASICLIST, basicList);
        model.addAttribute(MODEL_LISTSIZE,basicList.size());
        model.addAttribute(MODEL_CURRENT_ARCHIVE,Archive.DELETE);
        return "UserHome";
    }


    @PostMapping("/editInfo")
    public String edit(Model model, Principal principal,inputEdit ie){
        String email = principal.getName();
        User u = repo.findByEmail(email);

        Path root = Paths.get("src/main/resources/static/assets/");
        String fileName = StringUtils.cleanPath(ie.getAvatar().getOriginalFilename());
        System.out.println(fileName);
        if(ie.getName().equals("")){
            model.addAttribute("errorMsgOfEditInfo","pls fill your name");
        }
        else if(fileName.equals("")){
            model.addAttribute("errorMsgOfEditInfo","choose your avatar");
        }
        else{
            u.setName(ie.getName());
            u.setAvatar(fileName);
            repo.save(u);


            try {
                Files.copy(ie.getAvatar().getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                model.addAttribute("SuccessMsgOfEditInfo","Change information successfully");
            } catch (Exception e) {
                throw  new RuntimeException(e.getMessage());
            }


        }

        return "UserHome";
    }

    @PostMapping("/changePassword")
    public String changePassword(Model model, Principal principal,ChangePassword CP){
        String email = principal.getName();
        User u = repo.findByEmail(email);

        if(CP.getOldPass().equals("")){
            model.addAttribute("errorMsgOfChangePassword","your old password is empty");
        }
        else if(CP.getNewPass().equals("")){
            model.addAttribute("errorMsgOfChangePassword","your new password is empty");
        }
        else if(!CP.getNewPass().equals(CP.getConfPass())){
            model.addAttribute("errorMsgOfChangePassword","confirm password is not same");
        }
        else if(!encoder.matches(CP.getOldPass(),u.getPassword())){
            model.addAttribute("errorMsgOfChangePassword","your old password is not correct");
        }
        else{
            u.setPassword(encoder.encode(CP.getNewPass()));

            repo.save(u);
            model.addAttribute("SuccessMsgOfChangePassword","change success");
        }
        return "UserHome";
    }

    @PostMapping("/addLabel")
    public String addLabel(HttpServletResponse response, Model model, Principal principal, AddLabel AL){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        List<Label> lstLabel = labelRepo.findAll();

        if(AL.getNameLabel().equals("")){
            model.addAttribute("errorMsgOfAddLabel","Label name is empty");
        }
        else{
            boolean check = true;
            for (int i=0;i<lstLabel.size();i++){
                if (lstLabel.get(i).getName().equals(AL.getNameLabel())){
                    check = false;
                    break;
                }
            }
            if (check){
                Label l = new Label(0,AL.getNameLabel(),null);
                labelRepo.save(l);
                model.addAttribute("SuccessMsgOfAddLabel","Add success");
            }
            else{
                model.addAttribute("errorMsgOfAddLabel","Label name is exist");
            }
        }
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "UserHome";
    }
    @PostMapping("/editLabel")
    public String editLabel(HttpServletResponse response, Model model, Principal principal,AddLabel AL,@RequestParam(name = "labelId") String labelId){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        Label l = labelRepo.findById(Integer.parseInt(labelId));
        l.setName(AL.getNameLabel());
        labelRepo.save(l);
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "UserHome";
    }

    @PostMapping("/deleteLabel")
    public String deleteLabel(HttpServletResponse response, Model model, Principal principal,AddLabel AL,@RequestParam(name = "deleteLabelId") String labelId){
        String email = principal.getName();
        User u = repo.findByEmail(email);
        Label l = labelRepo.findById(Integer.parseInt(labelId));
        labelRepo.delete(l);
        try {
            response.sendRedirect("/user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "UserHome";
    }
}



@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class inputEdit{
    private String name;
    private MultipartFile avatar;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class ChangePassword{
    private String oldPass;
    private String newPass;
    private String confPass;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class AddLabel{
    private String nameLabel;
}
