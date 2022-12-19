package com.javafinal.WebMail.conf;

import com.javafinal.WebMail.Model.User;
import com.javafinal.WebMail.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repo.findByEmail(email);
        if(u!= null){
            return new CustomUserDetail(u);
        }
        throw  new UsernameNotFoundException("user not found ");
    }
}
