package tn.esprit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
	
	private final SmsSender smsSender;

	@Autowired
	public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
		this.smsSender = (SmsSender) smsSender;
	}
	
	public void SendSms( SmsRequest smsRequest) {
		smsSender.SendSms(smsRequest);
		
	}
	
}
