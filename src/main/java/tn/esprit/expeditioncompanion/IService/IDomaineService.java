package tn.esprit.expeditioncompanion.IService;

import tn.esprit.expeditioncompanion.entities.*;

import java.util.List;

public interface IDomaineService {


    Domaine add(Domaine p);

    String DeleteDomaine(long idp);

    Domaine updateDomaine(Domaine P);

    List<Domaine> retrieveDomaine();

     Domaine retrieveDomaineByNom(String nom);




    Domaine findDomaineById(long id);

}
