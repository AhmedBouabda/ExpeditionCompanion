package tn.esprit.expeditioncompanion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.expeditioncompanion.IService.IProfilService;
import tn.esprit.expeditioncompanion.IService.IUserService;
import tn.esprit.expeditioncompanion.Service.MapValidationErrorService;
import tn.esprit.expeditioncompanion.Validator.ProfilValidatore;
import tn.esprit.expeditioncompanion.entities.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profil")
public class ProfilController {


    @Autowired
    IProfilService profilService;


    @Autowired
    IUserService userService;


    @Autowired
    ProfilValidatore profilValidatore;
    @Autowired
    MapValidationErrorService mapValidationErrorService ;

    @GetMapping("/retrieve-all-profil")
    @ResponseBody
    //http://localhost:8080/SpringMVC/servlet/retrieve-all-Voyage
    public List<Profile> getProfil() {
        List<Profile> v = profilService.retrieveProfil();
        return v ;
    }


    @GetMapping("/retrieveByUser/{id}")
    @ResponseBody
    public Profile getProfilByUser(@PathVariable("id")  long iduser) {
        User u = userService.findUserById(iduser);
        Profile v = profilService.retrieveProfilByUser(u);
        return v ;
    }
    //gestion de validator
    @PostMapping("/add-profil/{id}")
    @ResponseBody
    public ResponseEntity<?> addProfil(@Valid @RequestBody Profile a , BindingResult result,@PathVariable("id")  long iduser) {
        profilValidatore.validate(a,result);
        ResponseEntity<?> erroMap =mapValidationErrorService.MapValidationService(result);
        if(erroMap != null)return erroMap;
        a.setIduser(userService.findUserById(iduser));
        Profile p = profilService.addProfil(a);
        return new ResponseEntity<Profile>( p, HttpStatus.CREATED);

    }
    @DeleteMapping("/remove-profil/{profil-id}")
    @ResponseBody
    public void removeProfil(@PathVariable("profil-id")  long profilid) {
        profilService.DeleteProfil(profilid);

    }

    @PutMapping("/modify-Profil")
    @ResponseBody
    public Profile modifyProfil(@RequestBody Profile cat) {
        return profilService.updateProfil(cat);
    }


    @GetMapping("/retrieve-Profil/{profil-id}")
    @ResponseBody
    public Profile retrieveProfil(@PathVariable("profil-id") int VoyageId) {
        return profilService.findProfilById(VoyageId);  }

    @GetMapping("/affecter/{profil-id}/{domaine-id}")
    @ResponseBody
    public Profile affecterDomaine(@PathVariable("profil-id") int profilId,@PathVariable("domaine-id") int domaineId) {
        return profilService.AffecterDomaineToProfil(domaineId,profilId);  }


    @PostMapping("/file")
    @ResponseBody
    @Transactional
    public Object  uploadImg (@RequestParam("file") @Nullable MultipartFile file, @RequestParam("identifier") Long id) {
        Profile pub =profilService.findProfilById(id);
        if(pub==null)
        {
            Map<String, String> errorDetails= new HashMap<>();
            errorDetails.put("message", "publication non definit");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
        if(file!=null) {
            UploadImageProfil image =pub.getImage();

            if(image==null)
            {
                image= new UploadImageProfil();
                image.setProfile(pub);
                pub.setImage(image);
            }

            try {
                image.setData(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setName(file.getOriginalFilename());

            profilService.saveImage(image);

            profilService.updateProfil(pub);
        }


        return pub;
    }



}
