package tn.esprit.spring.service;





import java.util.List;

import tn.esprit.spring.entities.Role;

public interface IRoleService {
	Role addRole(Role u);
	Role ShowRole(long id);
	Role UpdateRole(Role u);
	void DeleteRole(long id);
	List<Role> ShowAllRole();
	
}
