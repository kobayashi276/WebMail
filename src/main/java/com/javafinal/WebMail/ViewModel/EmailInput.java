package com.javafinal.WebMail.ViewModel;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class EmailInput {

    private String receiverEmail;
    private String title;
    private String body;
    private String archive;
}
