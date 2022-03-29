package tn.esprit.expeditioncompanion.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.expeditioncompanion.entities.Flow;
import tn.esprit.expeditioncompanion.entities.Profile;
import tn.esprit.expeditioncompanion.entities.User;

@Repository
public interface IFlowRepository extends CrudRepository<Flow, Long> {

    Flow findFlowByIdProfilEnvAndIduser(Profile profil , User user);
}
