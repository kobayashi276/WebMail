package com.javafinal.WebMail.Service;

import com.javafinal.WebMail.Constant.Role;
import com.javafinal.WebMail.Model.User;
import com.javafinal.WebMail.Model.UserRepository;
import com.javafinal.WebMail.conf.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public User addUser(User u){
        u.setPassword(encoder.encode(u.getPassword()));
        return repo.save(u);
    }
    public int findUserByNameandEmailandPhone(User u){
        //TODO: ?
        ArrayList<User> lstUser = new ArrayList<>();
        repo.findAll().forEach(user -> {
            if (user != null)
                lstUser.add(user);
        });

        if(lstUser.size()>0){
            for(User u1 : lstUser){
                if(u1.getName().equalsIgnoreCase(u.getName())){
                    return 1;
                }
                else if(u1.getPhone().equals(u.getPhone())){
                    return 2;
                }
                else if(u1.getEmail().equalsIgnoreCase(u.getEmail())){
                    return 3;
                }
            }
        }
        return -1;
    }
    public int findUserByEmailAndPassword(String email, String pass){
        List<User> lstUser = new ArrayList<>();
        repo.findAll().forEach(lstUser::add);
        for(User u : lstUser){
            if(u.getEmail().equalsIgnoreCase(email) && encoder.matches(pass,u.getPassword())){
                if(u.getRole().equals(Role.ROLE_ADMIN)){
                    return 1;
                }
                else if(u.getRole().equals(Role.ROLE_USER)){
                    return 0;
                }
            }
        }
        return -1;
    }

}
