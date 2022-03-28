package tn.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DictionnaryRepository extends CrudRepository<tn.esprit.entities.Dictionnary,String>{

	@Query("select d.description from Dictionnary d")
	 public List<String> dictionnaryList();
	

}
