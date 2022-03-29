package tn.esprit.expeditioncompanion.IService;

import tn.esprit.expeditioncompanion.entities.User;

public interface IUserService {

    User findUserById(long id);
}
