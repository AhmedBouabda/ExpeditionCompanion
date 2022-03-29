package tn.esprit.expeditioncompanion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.esprit.expeditioncompanion.IService.IDomaineService;
import tn.esprit.expeditioncompanion.Service.MapValidationErrorService;
import tn.esprit.expeditioncompanion.Validator.DomaineValidatore;
import tn.esprit.expeditioncompanion.entities.Domaine;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DomaineController {

    @Autowired
    IDomaineService domaineService ;
    @Autowired
    MapValidationErrorService mapValidationErrorService ;

    @Autowired
    DomaineValidatore domaineValidatore;

    @PostMapping("/add-Domaine")
    @ResponseBody
    public ResponseEntity<?> addProfil(@Valid @RequestBody Domaine a , BindingResult result) {
        domaineValidatore.validate(a,result);
        ResponseEntity<?> erroMap =mapValidationErrorService.MapValidationService(result);
        if(erroMap != null)return erroMap;
        Domaine p = domaineService.add(a);
        return new ResponseEntity<Domaine>( p, HttpStatus.CREATED);

    }
    @DeleteMapping("/remove-domaine/{profil-id}")
    @ResponseBody
    public void removeDomaine(@PathVariable("profil-id")  long profilid) {
        domaineService.DeleteDomaine(profilid);

    }

    @PutMapping("/remove-domaine/{nom}")
    @ResponseBody
    public void findDomaine(@PathVariable("nom")  String nom) {
        domaineService.retrieveDomaineByNom(nom);

    }


    @GetMapping("/retrieve-all-Domaine")
    @ResponseBody
    //http://localhost:8080/SpringMVC/servlet/retrieve-all-Voyage
    public List<Domaine> getProfil() {
        List<Domaine> v = domaineService.retrieveDomaine();
        return v ;
    }

}
