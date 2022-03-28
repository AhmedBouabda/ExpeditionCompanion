package tn.esprit.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entities.Reclamation;

@Repository
public interface ReclamationRepository extends CrudRepository<Reclamation, Long>{
	
	
	  @Query(value="select r from Reclamation where category=?1", nativeQuery=true)
		List<Reclamation> findReclamationByCategory();

	  
	  @Query("Select c from Reclamation c where c.date  >= CURRENT_TIMESTAMP -30")
		List<Reclamation> findLastWeekReclamations();

	
	
	  

}
