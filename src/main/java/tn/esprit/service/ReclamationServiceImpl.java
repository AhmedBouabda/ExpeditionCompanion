package tn.esprit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.entities.ClaimCategory;
import tn.esprit.entities.ClaimStatus;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.User;
import tn.esprit.entities.Voyage;
import tn.esprit.repository.DictionnaryRepository;
import tn.esprit.repository.ReclamationRepository;
import tn.esprit.repository.UserRepository;
import tn.esprit.repository.VoyageRepository;

@AllArgsConstructor

@Service
@Slf4j
public class ReclamationServiceImpl implements ReclamationService{
	
     @Autowired
	ReclamationRepository crep;
     
     @Autowired
     UserRepository urep;
     
     @Autowired
     DictionnaryRepository drep;
     @Autowired
     VoyageRepository vrep;


     
	public List<Reclamation> getAllReclamations() {
		
		 return (List<Reclamation>) crep.findAll();	 
	}

	
	
	public List<Reclamation> getAllReclamationsByUserId(long id_user) {
		
		 List<Reclamation> reclamations= urep.findById(id_user).orElse(null).getReclamation();
		 return reclamations;
	}
	

	
	public void deleteAllReclamations() {
		
		crep.deleteAll();
	}
	

	
	public void deleteReclamationById(long id) {
		
		crep.deleteById(id);
	}

	
	
	public Reclamation updateReclamation(Reclamation c) {
		
		return crep.save(c);
	}


	
	
	public Reclamation addReclamation(Reclamation c, Long id_user){
		
		User u= urep.findById(id_user).orElse(null);
		c.setUser(u);
		return crep.save(c);
		
		//sendEmailService.sendSimpleEmail("oussema.ouerfelli@esprit.tn","your complaint is taken care of!","Complaint Response");
		    }
	
	
	
	public Reclamation addReclamationMatching(Reclamation c, Long id_sender, Long id_reciver, Long id_voyage) {
		
	User u=urep.findById(id_sender).get();
	User ucr=urep.findById(id_reciver).get();
	Voyage voyage=vrep.findById(id_voyage).get();
	List<User> users= new ArrayList<User>();
	users.add(u);
	users.add(ucr);
	
	//if(voyage.getUsers().containsAll(users)){
	c.setComplainer(u);
	c.setComplainedAbout(ucr);
	c.setDate(new Date());
	return crep.save(c);
	}
	///else return null;}
	
	
	
	public Reclamation changerCategory(long id, Reclamation c) {
		
		//Reclamation r =
		crep.findById(id).orElse(null);
		ClaimCategory categorie1= c.getCategory();
		c.setCategory(categorie1);
		return crep.save(c);
		
	}

	
	
	
	public Reclamation changerDescription(long id, Reclamation c) {
		
		Reclamation r=crep.findById(id).orElse(null);
		String description1=c.getDescription();
		c.setDescription(description1);
	
		return crep.save(r);
	}


	

	public List<Reclamation> findReclamationByCategory(ClaimCategory category){
		
		return crep.findReclamationByCategory();
	}
	
	
	            //-------------------------------STATISTIQUE---------------------------------------------------//
	
	public List<Double> PourcentageReclamationByCategory() {
		
		
		List<Double> pourcentages=new ArrayList<Double>();
		double SERVICE = 0;
		double EMPLOYEE=0;
		double OTHER=0;
		
		
			List<Reclamation> reclamations=  (List<Reclamation>) crep.findAll();
			System.out.println(reclamations.toString());
			
			 for (Reclamation reclamation: reclamations) {
				 
				
				if (reclamation.getCategory().equals(ClaimCategory.SERVICE)) {
					SERVICE++;
					}
				
				
				else if (reclamation.getCategory().equals(ClaimCategory.EMPLOYEE)) {
					EMPLOYEE++;}
				
				
				else if (reclamation.getCategory().equals(ClaimCategory.OTHER)) {
					OTHER++;}
		}
				
				if (reclamations.size() !=0) {
					
					System.out.println("number_reclamations:"+reclamations.size());
					
					SERVICE=  ((SERVICE/(reclamations.size()))*100);
					
					 
					 EMPLOYEE= ((EMPLOYEE/reclamations.size()))*100;
					 
					 
					 OTHER= ((OTHER/reclamations.size()))*100;
					}
					
					
					pourcentages.add(SERVICE);

					pourcentages.add(EMPLOYEE);

					pourcentages.add(OTHER);

					System.out.println(pourcentages);
					
					return pourcentages;


				}
	
	//-------------------------------STATISTIQUE---------------------------------------------------//
	
	public List<Double> PourcentageReclamationByStatus() {
		
		
		List<Double> pourcentages=new ArrayList<Double>();
		double RESOLVED = 0;
		double SKIPPED=0;
		double PROCESSING=0;
		
		
			List<Reclamation> reclamations=  (List<Reclamation>) crep.findAll();
			System.out.println(reclamations.toString());
			
			 for (Reclamation reclamation: reclamations) {
				 
				
				if (reclamation.getStatus().equals(ClaimStatus.RESOLVED)) {
					RESOLVED++;
					}
				
				
				else if (reclamation.getStatus().equals(ClaimStatus.SKIPPED)) {
					SKIPPED++;}
				
				
				else if (reclamation.getStatus().equals(ClaimStatus.PROCESSING)) {
					PROCESSING++;}
		}
				
				if (reclamations.size() !=0) {
					
					System.out.println("number_reclamations:"+reclamations.size());
					
					RESOLVED=  ((RESOLVED/(reclamations.size()))*100);
					
					 
					SKIPPED= ((SKIPPED/reclamations.size()))*100;
					 
					 
					PROCESSING= ((PROCESSING/reclamations.size()))*100;
					}
					
					
					pourcentages.add(RESOLVED);

					pourcentages.add(SKIPPED);

					pourcentages.add(PROCESSING);

					System.out.println(pourcentages);
					
					return pourcentages;


				}


//********************************************STATISTIQUE***********************************
	
	
	
	public List<Double> findLastWeekReclamations(Reclamation c){
		List<Double> pourcentages=new ArrayList<Double>();
		double SERVICE = 0;
		double EMPLOYEE=0;
		double OTHER=0;
		
		
			List<Reclamation> reclamations=  (List<Reclamation>) crep.findLastWeekReclamations();
			System.out.println(reclamations.toString());
			
			 for (Reclamation reclamation: reclamations) {
				 
				
				if (reclamation.getCategory().equals(ClaimCategory.SERVICE)) {
					SERVICE++;
					}
				
				
				else if (reclamation.getCategory().equals(ClaimCategory.EMPLOYEE)) {
					EMPLOYEE++;}
				
				
				else if (reclamation.getCategory().equals(ClaimCategory.OTHER)) {
					OTHER++;}
		}
				
				if (reclamations.size() !=0) {
					
					System.out.println("number_reclamations:"+reclamations.size());
					
					SERVICE=  ((SERVICE/(reclamations.size()))*100);
					
					 
					 EMPLOYEE= ((EMPLOYEE/reclamations.size()))*100;
					 
					 
					 OTHER= ((OTHER/reclamations.size()))*100;
					}
					
					
					pourcentages.add(SERVICE);

					pourcentages.add(EMPLOYEE);

					pourcentages.add(OTHER);

					System.out.println(pourcentages);
					
					return pourcentages;


		
	}



	






	
	}
			
	
		
		
		
	
	

	
	



		 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

