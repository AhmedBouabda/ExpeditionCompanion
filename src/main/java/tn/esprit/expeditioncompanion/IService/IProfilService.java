package tn.esprit.expeditioncompanion.IService;

import tn.esprit.expeditioncompanion.entities.*;

import java.util.List;

public interface IProfilService {


    Profile addProfil(Profile p);

    String DeleteProfil(long idp);

    Profile updateProfil(Profile P);

    List<Profile> retrieveProfil();

    Profile retrieveProfilByUser(User u);

    UploadImageProfil saveImage(UploadImageProfil p);

    Profile AffecterDomaineToProfil(long idDom,long idProfil);


    Profile findProfilById(long id);
    public void poupilariteScud();
    public Poupilarite poupilarite(Long id);
}
