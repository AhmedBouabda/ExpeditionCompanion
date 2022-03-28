package tn.esprit.service;



public interface ISendEmailService {
	
	void sendSimpleEmail( String toEmail,
            String body,
            String subject);

}
