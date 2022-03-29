package tn.esprit.expeditioncompanion.Repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.expeditioncompanion.entities.Profile;
import tn.esprit.expeditioncompanion.entities.User;

public interface IProfilRepository extends CrudRepository<Profile,Long> {
    Profile findProfileByIduser(User user);
}
