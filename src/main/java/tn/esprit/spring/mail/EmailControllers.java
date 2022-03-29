package tn.esprit.spring.mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailControllers {
	
	 @Autowired
		JavaMailSender mailSender;

	 //http://localhost:8089/SpringMVC/test
	  @GetMapping("/test")
		public String send(){
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("2022montassar@gmail.com");
			message.setTo("montassar.slama@esprit.tn");
			message.setSubject("travel Planning message");
			message.setText("Has been approved !");
			mailSender.send(message);
			
			return "done";
		}
	  //http://localhost:8089/SpringMVC/SendMail?dest=zzzzz&Mail=zzzzzz&name=zzzzzz
	  @PostMapping("/SendMail")
		@ResponseBody
	  public String ApplicationMail(String Mail , String dest, String name)
	  {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("2022montassar@gmail.com");
			message.setTo(Mail);
			message.setText("Mr/Mrs : "+name+ "\n Your flight to : " +dest+ " Has been approved !");
			message.setSubject("Travel");
			mailSender.send(message);
		 
		    return "Successfully sent";
	  }
	  
	



}