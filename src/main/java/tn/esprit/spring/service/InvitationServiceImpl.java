package tn.esprit.spring.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.esprit.spring.entities.Invitation;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.InvitationRepository;
import tn.esprit.spring.repository.UserRepository;
@Service
public class InvitationServiceImpl implements IInvitationService {
	@Autowired
	InvitationRepository repository;
	@Autowired
	UserRepository ur;
	@Autowired
	EmailSender email = new EmailSender();

	

	@Override
	public List<Invitation> retrieveAllInvitation() {
		// TODO Auto-generated method stub
		return (List<Invitation>) repository.findAll();
	}

	@Override
	public void deleteInvitation(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);

	}

	@Override
	public Invitation updateInvitation(Invitation c) {
		// TODO Auto-generated method stub
		return repository.save(c);
	}

	@Override
	public Invitation updateInvitationById(Invitation o, Long idInvitation) {
		// TODO Auto-generated method stub
		
		
		
		Invitation kl= repository.findById(idInvitation).orElse(null);
		
		

		kl.setState(o.isState());

	
		
		repository.saveAndFlush(kl);


		
		return kl;
			

		
		
	}

	@Override
	public Invitation addInvitation(Invitation c, List<Long> idusers) {
		
		User u= ur.findAllById(idusers).get(1);
		List<User> users= new ArrayList<User>();
		c.getUsers();
		//c.setUsers(users);
         
		repository.save(c);
	
		email.sendMail("2022montassar@gmail.com","Hello", "Content");
		
		
		
		return c;
	}

	@Override
	public List<Invitation> findBystate(boolean state) {		
		return repository.findByState(state);
	}


	@Override
	public void addUserToInvitation(Long id, Long idInv) {
        System.out.println(id);
		User u= ur.findById(id).get();
		System.out.println(u);
		Invitation inv= repository.findById(idInv).get();
		u.getInvitations().add(inv);
		ur.save(u);
	}

	@Override
	public Set<Invitation> invitationsParUser(Long id) {
		
		return ur.findById(id).get().getInvitations();
	}

	@Override
	public void accepterInvitation(Long idInv) {
		Invitation inv= repository.findById(idInv).get();
		inv.setState(true);
		repository.save(inv);
	}


	
}
