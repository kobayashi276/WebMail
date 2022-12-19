package com.javafinal.WebMail.Model;


import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int labelId;

    private String name;

    @ManyToMany
    @JoinTable(name = "labeldetails",
            joinColumns = {
                    @JoinColumn(name = "label_id", referencedColumnName = "labelId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "email_id", referencedColumnName = "emailId")
            })
    private List<Email> emailList;
}
