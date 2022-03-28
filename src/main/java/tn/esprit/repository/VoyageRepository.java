package tn.esprit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entities.Voyage;

@Repository
public interface VoyageRepository extends CrudRepository<Voyage, Long> {

}
