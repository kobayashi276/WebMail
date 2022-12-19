package com.javafinal.WebMail.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends CrudRepository<Email, Integer> {

    List<Email> findEmailByReceiver(User receiver);
    List<Email> findEmailBySender(User sender);
    Email findEmailByEmailId(int emailId);

    List<Email> findEmailByReceiverAndArchive(User receiver, String archive);
}
