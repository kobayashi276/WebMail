package com.javafinal.WebMail.Model;

import lombok.*;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatFlowId;


    @NonNull
    @GeneratedValue
    private int conversationId;


    @ManyToOne
    @JoinColumn(name = "email_email_id")
    private Email email;
}
