package tn.esprit.spring.batch;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Invitation;
import tn.esprit.spring.service.EmailSender;
import tn.esprit.spring.service.IInvitationService;

@Slf4j
public class InvitationWriter implements ItemWriter<Invitation> {

    @Autowired
    private IInvitationService InvitationService;
 
    @Autowired
	EmailSender emailSender;
    
    @Override
    /* écrire nos données dans la base de données*/
    public void write(List<? extends Invitation> invitations) throws Exception {
            log.info("Enregistrement des lignes invitations dans la base de données", invitations);
invitations.stream().forEach((message)->emailSender.sendMail(message.getEmail(), "Invitation mail", "Hi, ExpeditionCompanion has invited you to use our Application to collaborate with them. Use the button below to set up your account and get started:"));            
    }}
