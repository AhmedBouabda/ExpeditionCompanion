package tn.esprit.expeditioncompanion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.expeditioncompanion.IService.IUserService;
import tn.esprit.expeditioncompanion.Repository.UserRepo;
import tn.esprit.expeditioncompanion.entities.User;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepo userRepo ;

    @Override
    public User findUserById(long id) {
        return this.userRepo.findById(id).orElse(null);
    }
}
