package com.javafinal.WebMail.Service;

import com.javafinal.WebMail.Constant.Archive;
import com.javafinal.WebMail.Constant.Role;
import com.javafinal.WebMail.Model.*;
import com.javafinal.WebMail.UserSessionInfo;
import com.javafinal.WebMail.ViewModel.EmailBasic;
import com.javafinal.WebMail.ViewModel.EmailInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QueryService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ChatFlowRepository chatFlowRepository;


    // TODO: User
    public User getUserByEmail(String email){
        List<User> user = this.userRepository.findUserByEmail(email);
        User result = user.size() == 0? null:user.get(0);
        return result;
    }

    public void addNewClient(User user) {
        user.setRole("ROLE_USER");
//        user.setRole(Role.Client);
        this.userRepository.save(user);
    }

    public void addNewAdmin(User user) {
        user.setRole("ROLE_ADMIN");
//        user.setRole(Role.Admin);
        this.userRepository.save(user);
    }

    // TODO: Email

    public ArrayList<EmailBasic> loadReceivedBasicEmails(){
        ArrayList<EmailBasic> result = new ArrayList<>();
        List<Email> emails = emailRepository.findEmailByReceiver(UserSessionInfo.getCurrentUser());
        for(Email email : emails){
            if (email.getArchive().contains(Archive.DELETE)) {
                continue;
            }
            EmailBasic basicView = new EmailBasic();
            basicView.setEmailId(email.getEmailId());
            basicView.setSenderEmail(email.getSender().getEmail());
            basicView.setTitle(email.getTitle());
            basicView.setDate(email.getSentDate());
            basicView.setArchive(email.getArchive());
            basicView.setStatus(email.getStatus());
            result.add(0, basicView);
        }
        return result;
    }


    public boolean sendEmail(EmailInput emailInput) {
        User sender = UserSessionInfo.getCurrentUser();
        User receiver = this.getUserByEmail(emailInput.getReceiverEmail());
        Email email = new Email(emailInput.getTitle(), emailInput.getBody());
        if (sender == null || receiver == null) {
            return false;
        }
        email.setSender(sender);
        email.setReceiver(receiver);
        emailRepository.save(email);
        return true;
    }

    public boolean sendAnEmail(EmailInput emailInput){
        User sender = UserSessionInfo.getCurrentUser();
        User receiver = this.getUserByEmail(emailInput.getReceiverEmail());
        Email email = new Email(emailInput.getTitle(), emailInput.getBody());
        if (sender == null || receiver == null) {
            return false;
        }
        email.setSender(sender);
        email.setReceiver(receiver);
        emailRepository.save(email);
        ChatFlow chatFlow = new ChatFlow();
        chatFlow.setEmail(email);
        chatFlow.setConversationId(email.getEmailId());
        chatFlowRepository.save(chatFlow);
        return true;
    }

    public boolean saveADraft(EmailInput emailInput){
        User sender = UserSessionInfo.getCurrentUser();
        Email email = new Email(emailInput.getTitle(), emailInput.getBody());
        if (sender == null) {
            return false;
        }
        email.setSender(sender);
        email.setArchive(Archive.DRAFT);
        emailRepository.save(email);
        return true;
    }

    public ArrayList<EmailBasic> loadBasicEmailsByArchive(String archive){
        ArrayList<EmailBasic> result = new ArrayList<>();
        List<Email> emails = emailRepository.findEmailByReceiver(UserSessionInfo.getCurrentUser());
        for(Email email : emails){
            if (!email.getArchive().contains(archive)) {
                continue;
            }
            EmailBasic basicView = new EmailBasic();
            basicView.setEmailId(email.getEmailId());
            basicView.setSenderEmail(email.getSender().getEmail());
            basicView.setTitle(email.getTitle());
            basicView.setDate(email.getSentDate());
            basicView.setArchive(email.getArchive());
            basicView.setStatus(email.getStatus());
            result.add(0, basicView);
        }
        return result;
    }

    public ArrayList<EmailBasic> loadReceivedBasicStarEmails(){
        ArrayList<EmailBasic> result = new ArrayList<>();
        List<Email> emails = emailRepository.findEmailByReceiver(UserSessionInfo.getCurrentUser());
        for(Email email : emails){
            if (!email.getArchive().contains(Archive.STAR)) {
                continue;
            }
            EmailBasic basicView = new EmailBasic();
            basicView.setEmailId(email.getEmailId());
            basicView.setSenderEmail(email.getSender().getEmail());
            basicView.setTitle(email.getTitle());
            basicView.setDate(email.getSentDate());
            basicView.setArchive(email.getArchive());
            basicView.setStatus(email.getStatus());
            result.add(0, basicView);
        }
        return result;
    }

    public Email getEmailByCriteria(EmailBasic emailBasic){
        Email email = emailRepository.findEmailByEmailId(emailBasic.getEmailId());
        if (email == null)
            return null;
        if(!email.getTitle().contains(emailBasic.getTitle()) ||
                !email.getSentDate().contains(emailBasic.getDate())
                || !email.getSender().getEmail().equals(email.getSender().getEmail())){
            return null;
        }
        return email;
    }

    public void toggleEmailStarArchive(EmailBasic emailBasic){
        Email email = getEmailByCriteria(emailBasic);
        String archive = email.getArchive();
        if(email == null){
            return;
        }
        if (archive.contains(Archive.STAR)){
            email.setArchive(Archive.INBOX);
        }
        else{
            email.setArchive(Archive.STAR);
        }
        emailRepository.save(email);
    }

    public boolean deleteEmail(EmailBasic emailBasic){
        Email email = getEmailByCriteria(emailBasic);
        String archive = email.getArchive();
        if (email == null||archive.contains(Archive.DELETE)){
            return false;
        }
        else{
            email.setArchive(Archive.DELETE);
        }
        emailRepository.save(email);
        return true;
    }
}