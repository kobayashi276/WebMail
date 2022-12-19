package com.javafinal.WebMail.Model;

import MyUtil.MyUtility;
import com.javafinal.WebMail.Constant.Archive;
import com.javafinal.WebMail.Constant.Status;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emailId")
    private int emailId;

    private String title;
    private String body;
    private String archive;
    private String status;
    private String sentTime;
    private String sentDate;
    private String timeZone;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private User receiver;

    @ManyToMany(mappedBy = "emailList")
    private List<Label> labelList;

    public Email(String title, String body){
        this.title = title;
        this.body = body;
        this.archive = Archive.INBOX;
        this.status = Status.UNREAD;
        this.sentTime = MyUtility.getCurrentTime();
        this.sentDate = MyUtility.getCurrentDate();
        this.timeZone = MyUtility.getTimeZone();
    }
}
