package tn.esprit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsRestController {
	
	@Autowired
	private final Service service;
	
	
	public SmsRestController(Service service) {
		super();
		this.service = service;
	}

	@PostMapping("/")
	public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
		
		 ((SmsRestController) service).sendSms(smsRequest);
		
	} 
	

}
