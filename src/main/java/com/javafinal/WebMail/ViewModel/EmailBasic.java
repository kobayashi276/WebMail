package com.javafinal.WebMail.ViewModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class EmailBasic {
    private int emailId;
    private String senderEmail;
    private String title;
    private String date;
    private String archive;
    private String status;
    public EmailBasic(int emailId, String senderEmail, String title, String date){
        this.emailId = emailId;
        this.senderEmail = senderEmail;
        this.title = title;
        this.date = date;
    }
}
