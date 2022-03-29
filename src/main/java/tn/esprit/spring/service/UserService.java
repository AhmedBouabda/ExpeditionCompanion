package tn.esprit.spring.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.entities.VerificationToken;
import tn.esprit.spring.repository.RoleRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.VerificationTokenRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
    private VerificationTokenRepository tokenRepository;
	
	/*Chercher un utilisateur*/

	public User findOne(long id){
	return userRepository.findById(id).get();
	}
	@Override
	public User save(User u) {
		return userRepository.save(u);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	/*Update d'un user*/
	@Override
	public  User updateUser(User user)	{
		return userRepository.save(user);
		
	}




	/*Delete user */
	@Override
	public  void deleteUser(long id)	{
		 userRepository.deleteById(id);

	}
	
	/*get all Users by Role*/
	public List<User> getAllUsers(){
		 List<Long> listUsersId=userRepository.ListeUsers();
	
			List<User> listUsers = new ArrayList();
		 User u = new User();
		 for(Long  a : listUsersId)
		 {
			 u=findOne(a);
			// if(u.getEtatAcc().equals("waiting"))
			// {
				 listUsers.add(u);
			// }
		 }
		return  listUsers;		
	}
	
	public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
	
	public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
	
	public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
	@Override
	public void addUserAffectRole(long idRole, User u) {
		// TODO Auto-generated method stub
		
	}





}
