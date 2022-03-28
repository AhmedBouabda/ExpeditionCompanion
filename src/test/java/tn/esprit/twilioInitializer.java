package tn.esprit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class twilioInitializer {
	private final static Logger logger=LoggerFactory.getLogger(twilioInitializer.class);
	private final TwilioConfiguration twilioConfiguration;

	@Autowired
	public twilioInitializer(TwilioConfiguration twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
		Twilio.init(
				twilioConfiguration.getAccountSid(),
				twilioConfiguration.getAuthToken()
				);
		logger.info("twilio initialized ... with accound sid{}", twilioConfiguration.getAccountSid());
	}
	
	
	

}
