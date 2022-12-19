package com.javafinal.WebMail;

import com.javafinal.WebMail.Model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UserSessionInfo {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user){
        currentUser = user;
    }

}
