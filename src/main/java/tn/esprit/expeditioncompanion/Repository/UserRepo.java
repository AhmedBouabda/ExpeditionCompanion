package tn.esprit.expeditioncompanion.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.expeditioncompanion.entities.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
}
