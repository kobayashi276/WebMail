package com.javafinal.WebMail.Model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.javafinal.WebMail.Constant.Role;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserId;

    private String name;
    private String email;
    private String password;
    private String phone;
    @Nullable
    private String avatar;
    private String role;

    public User(String email, String name, String password, String phone, String avatar){
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
        this.role = Role.ROLE_USER;
    }

    public User(String name,String email, String password, String phone, String avatar, String role){
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
        this.role = role;
    }

    @Transient
    public String getAvatarPath(){
        if(avatar==null) return null;
        return avatar;
    }
}
