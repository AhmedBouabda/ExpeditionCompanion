package tn.esprit.service;

import java.util.List;

import tn.esprit.entities.ClaimCategory;
import tn.esprit.entities.Reclamation;


public interface ReclamationService {
	
	public List<Reclamation> getAllReclamations();
	public List<Reclamation> getAllReclamationsByUserId(long id_user);
	
	public void deleteAllReclamations();
	public void deleteReclamationById(long id);
	
	public Reclamation updateReclamation(Reclamation c);
	
	public Reclamation addReclamation(Reclamation c, Long id_user);
	
	public Reclamation addReclamationMatching(Reclamation c, Long id_sender, Long id_reciver, Long id_voygae);
	
	
	public List<Reclamation> findReclamationByCategory(ClaimCategory category);
	
	public List<Double> PourcentageReclamationByCategory();
	
	public List<Double> PourcentageReclamationByStatus();
	public List<Double> findLastWeekReclamations(Reclamation c);
	
	
	
	
	


	
}

