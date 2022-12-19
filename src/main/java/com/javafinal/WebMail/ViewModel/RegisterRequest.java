package com.javafinal.WebMail.ViewModel;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest{
    private String name;
    private String phone;
    private String email;
    private String password;
    private String password_confirm;
    private String gender;
}

