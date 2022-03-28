package tn.esprit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
