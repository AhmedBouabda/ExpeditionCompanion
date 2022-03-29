package tn.esprit.expeditioncompanion.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.expeditioncompanion.entities.Domaine;


@Repository
public interface IDomaineRepository extends CrudRepository<Domaine, Long> {
    Domaine findDomaineByNom(String nom);

}
