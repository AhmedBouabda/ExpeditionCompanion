package tn.esprit;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsRequest {
	
	@NotBlank
	private final String PhoneNumber; //destination
	@NotBlank
	private String message;
	
	public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("message") String message) {
		super();
		PhoneNumber = phoneNumber;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	
	@Override
	public String toString() {
		return "SmsRequest [PhoneNumber=" + PhoneNumber + ", message=" + message + "]";
	}
	
	

}
