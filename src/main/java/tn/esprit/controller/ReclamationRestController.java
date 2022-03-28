package tn.esprit.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.Config.BadWordConfig;
import tn.esprit.entities.ClaimCategory;
import tn.esprit.entities.Reclamation;
import tn.esprit.service.ReclamationService;
import tn.esprit.service.SendEmailService;

@RestController
@RequestMapping("/reclamation")
public class ReclamationRestController {
	
	@Autowired
	ReclamationService recService;
	//UserService uService;
	BadWordConfig badWordConfig = new BadWordConfig();
	
	@Autowired
	private SendEmailService sendEmailService;
	
	
//******************************************************************************************************************
	
	/**URL: http://localhost:8081/SpringMVC/reclamation/getAllReclamations**/
	
	@PostMapping("/getAllReclamations")

	public List<Reclamation> getAllReclamations(){
    return (List<Reclamation>) recService.getAllReclamations();	 
	}
//********************************************************************************************************************
	/**URL:  http://localhost:8081/SpringMVC/reclamation/getAllReclamationsByUserId/1 ou 2..**/
	
	@GetMapping(value="/getAllReclamationsByUserId/{id_user}")
	@ResponseBody
	public List<Reclamation> getAllReclamationsByUserId(@PathVariable long id_user) {
		
		 List<Reclamation> reclamations= recService.getAllReclamationsByUserId(id_user);
		 return reclamations;
}
//*********************************************************************************************************************
	
	/** URL:  http://localhost:8081/SpringMVC/reclamation/deleteAllReclamations**/
	
	@DeleteMapping("deleteAllReclamations")

	public void deleteAllReclamations() {
		
		recService.deleteAllReclamations();
	}
	
//********************************************************************************************************************	
	/** URL:  http://localhost:8081/SpringMVC/reclamation/deleteReclamationsById/15**/
	
	@DeleteMapping("deleteReclamationsById/{id}")

   public void deleteReclamationById(@PathVariable long id) {
		
		recService.deleteReclamationById(id);
	}
//*********************************************************************************************************************
	//**URL:  http://localhost:8081/SpringMVC/reclamation/update/
	
	@PostMapping("/update")
	@ResponseBody
    public Reclamation updateReclamation(@RequestBody   Reclamation c) {
		
		return recService.updateReclamation(c);
	}
	
//*********************************************************************************************************************
	/** URL:  http://localhost:8081/SpringMVC/reclamation/addReclamation/{id_user}**/

	@PostMapping("addReclamation/{id}")
	@ResponseBody
	
      public Reclamation addReclamation(@RequestBody Reclamation c, @PathVariable("id") Long id_user){
  		recService.addReclamation(c, id_user);
  		
  		Reclamation c1=new Reclamation();
        c1.setDescription(badWordConfig.filterText(c.getDescription()));
        c1.setCategory(c.getCategory());
        c1.setDate(c.getDate());
        c1.setEmail(c.getEmail());
        c1.setStatus(c.getStatus());
        c1.setSubject(c.getSubject());
        System.out.println(c1.getDescription());
       
        sendEmailService.sendSimpleEmail("oussema.ouerfelli@esprit.tn", 
        		"Dear,"
        		+ " We have received your claim, it will be treated as soon as possible"
        		+ " Best regards" 
                ,"BUSINESS & TRAVEL CLAIM"); 
        
		return c1;

      }
	
	//*********************************ADD WITH MATCHING**************************************
	
	/** URL:  http://localhost:8081/SpringMVC/reclamation/addReclamationMatching/{id_complainer}/{id_complained}//{id_voyage}**/
	
		@ResponseBody
	    @PostMapping("/addReclamationMatching/{id_complainer}/{id_complained}/{id_voyage}")
	   public Reclamation addReclamationMatching(@RequestBody Reclamation c ,@PathVariable("id_complainer") Long Id_sender
	        ,@PathVariable("id_complained") Long id_complained , @PathVariable("id_voyage") Long id_voyage ){
	       return recService.addReclamationMatching(c, Id_sender, id_complained, id_voyage);
	   }
		
	
  		
  		
	
	
	
	
	
	
//*******************************************************************************************************************
    //URL:  http://localhost:8081/SpringMVC/reclamation/category/{category}
      
      @PostMapping("category/{category}")
  	  @ResponseBody

      public List<Reclamation> findReclamationByCategory(@PathVariable ClaimCategory category) {
    	  
    	 return recService.findReclamationByCategory(category);
    	
  	}
      
      
     //**URL: http://localhost:8081/SpringMVC/reclamation/countReclamation**/
      
      @GetMapping("countReclamation")
  	  @ResponseBody
  	  
      public List<Double> PourcentageReclamationByCategory() {
    	  
    	 return recService.PourcentageReclamationByCategory();
      
      }
      
      
    //**URL: http://localhost:8081/SpringMVC/reclamation/pourcentageReclamation**/
      
      @GetMapping("pourcentageReclamation")
  	  @ResponseBody
      
      public List<Double> PourcentageReclamationByStatus(){
    	  
    	 return recService.PourcentageReclamationByStatus(); 
      }
      
    
      
 //**URL: http://localhost:8081/SpringMVC/reclamation/countReclamationlastweek**/
      
      @GetMapping("countReclamationlastweek")
  	  @ResponseBody
  	  
  	   List<Double> findLastWeekReclamations(Reclamation c) {
    	  
    	 return recService.findLastWeekReclamations(c);
      
      }
      
      
   
      
}   
      
      
      
      
 
      
      
      
      
