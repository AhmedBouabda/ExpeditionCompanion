package tn.esprit.spring.controller;

import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Invitation;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.*;
import tn.esprit.spring.service.*;


@RestController
@RequestMapping("/Invitation")
public class InvitationRestController {
	@Autowired 
	IInvitationService cs;

	@Autowired
	EmailSender emailSender;
	
     
	 @Autowired
	 UserRepository ur;
	 @Autowired
		JavaMailSender mailSender;

	//http://localhost:8089/SpringMVC/Invitation/addInvitation?id=1
	@PostMapping("/addInvitation")
	@ResponseBody
	public void addInvitation(@RequestBody Invitation c,@RequestParam(value="idUsers") List<Long> idUsers)
	{
		
		
		//cs.addInvitation(c, idUsers);
	
		for(Long id:idUsers){
			User u= ur.findById(id).get();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("2022montassar@gmail.com");
			message.setTo(u.getEmail());
			message.setSubject("This is an invitation");
			message.setText("Hi,"    + u.getFirstName()+ u.getLastName()+    
			              
					"Expedition§Companion has invited you to use our Application to collaborate with them. Use the button below to set up your account and get started:");
			/*message.setText("lf you have any questions for Expedition§Companion, you can reply to this email and it will go right to them. Alternatively "
			+ "feel free to contact our customer success team anytime. "
			+ "(We're lightning quick at replying.)"
			+ " We also offer live chat during business hours."
     + " Welcome aboard,The 'B&T' Team "
     + "P.S. Need help getting started? Check out our help documentation. "
     + "If you're having trouble with the button above, copy and paste the URL below into your web browser."
     + "{{action_url}} "
     + "2022 [Product Name] All rights eserved "
     + "'B&T', LEC] "
     + "Ariana, Raoued ");*/
			mailSender.send(message);
			}
	
	}
	
	//http://localhost:8089/SpringMVC/Invitation/addUserToInv/1/1
	@PostMapping("/addUserToInv/{id}/{idInv}")
	@ResponseBody
	public void addUserToInv(@PathVariable("id") Long idUser ,@PathVariable("idInv") Long idInv)
	{
		cs.addUserToInvitation(idUser, idInv);
		
	}
	//http://localhost:8089/SpringMVC/Invitation/accepterInv/1
	@PostMapping("/accepterInv/{idInv}")
	@ResponseBody
	public void accepterInv(@PathVariable("idInv") Long idInv )
	{
		cs.accepterInvitation(idInv);
		
	}
	//http://localhost:8089/SpringMVC/Invitation/get-invitation-by-user/1
	@GetMapping("/get-invitation-by-user/{id}")
	@ResponseBody
	public Set<Invitation> getInvitationByUser(@PathVariable("id") Long id)
	{
		return cs.invitationsParUser(id);
	}
	
	
	
	@GetMapping("/get-all-Invitation")
	@ResponseBody
	public List<Invitation> getAllInvitation()
	{
		List<Invitation> listInvitation = cs.retrieveAllInvitation();	
		return listInvitation; 	
	}
	
	//http://localhost:8089/SpringMVC/Invitation/remove-Invitation/1
	@DeleteMapping("/remove-Invitation/{idInvitation}")
	@ResponseBody
	public void removeInvitation(@PathVariable("idInvitation") Long idInvitation) {
		cs.deleteInvitation(idInvitation);
	}
  	
  	//http://localhost:8089/SpringMVC/Invitation/modify-Invitation
	@PutMapping("/modify-Invitation")
	@ResponseBody
	public Invitation modifyInvitation(@RequestBody Invitation c)
	{
		return cs.updateInvitation(c);
	}
	
	@PutMapping("/modify-Invitation-byID/{id}")
	@ResponseBody
	public Invitation ModifyInvitation(@PathVariable("id") Long idInvitation,@RequestBody Invitation t) {
	return cs.updateInvitationById(t, idInvitation);
	
	}
	//http://localhost:8089/SpringMVC/Invitation/FindByState/false     {,true}
	@GetMapping("/FindByState/{state}")
	List<Invitation>findByState(@PathVariable("state") boolean state) 
	{
		return cs.findBystate(state);
	
	}
	
	
	
}