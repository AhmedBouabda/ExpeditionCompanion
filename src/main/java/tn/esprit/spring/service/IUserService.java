package tn.esprit.spring.service;


import java.util.List;

import tn.esprit.spring.entities.User;

public interface IUserService {
	User save(User u);

	User findOne(long id);
	User updateUser(User u);
	void deleteUser(long id);

	List<User> findAll();
	void addUserAffectRole(long idRole,User u);
	
	
}
