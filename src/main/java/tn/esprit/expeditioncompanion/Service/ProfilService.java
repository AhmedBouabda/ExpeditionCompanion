package tn.esprit.expeditioncompanion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.expeditioncompanion.IService.IDomaineService;
import tn.esprit.expeditioncompanion.IService.IProfilService;
import tn.esprit.expeditioncompanion.Repository.IProfilRepository;
import tn.esprit.expeditioncompanion.Repository.IUploadImage;
import tn.esprit.expeditioncompanion.entities.*;


import java.util.List;

@Service
public class ProfilService implements IProfilService {

    @Autowired
    IProfilRepository profilRepository ;

    @Autowired
    IDomaineService domaineService ;


    @Autowired
    IUploadImage uploadImage ;

    @Override
    public Profile addProfil(Profile p) {
        return profilRepository.save(p);
    }

    @Override
    public String DeleteProfil(long idp) {
        profilRepository.delete(profilRepository.findById((long) idp).get());
        return "Deleted";
    }

    @Override
    public Profile updateProfil(Profile p) {
        return profilRepository.save(p);
    }

    @Override
    public List<Profile> retrieveProfil() {
        List<Profile> profils=(List<Profile>)profilRepository.findAll();
        return profils;
    }

    @Override
    public Profile retrieveProfilByUser(User u) {
        return profilRepository.findProfileByIduser(u);
    }

    @Override
    public UploadImageProfil saveImage(UploadImageProfil p) {
        return uploadImage.save(p);
    }

    @Override
    public Profile AffecterDomaineToProfil(long idDom,long idProfil) {
        Domaine d = domaineService.findDomaineById(idDom);
        Profile p = findProfilById(idProfil);
        p.setIdDomaine(d);

        return updateProfil(p);
    }

    @Override
    public Profile findProfilById(long id) {
        return this.profilRepository.findById(id).orElse(null);
    }

    @Override
    public Poupilarite poupilarite(Long id) {
        Profile p = profilRepository.findById(id).orElse(null);
        if (p.getFollowersNbr() >= 1000) {
            p.setPoupilarite(Poupilarite.superFamous);
            profilRepository.save(p);
        }

        if (p.getFollowersNbr() >= 100) {
            p.setPoupilarite(Poupilarite.famous);
            profilRepository.save(p);
            return p.getPoupilarite();
        }


        if (p.getFollowersNbr() >= 10) {
            p.setPoupilarite(Poupilarite.pouplar);
            profilRepository.save(p);
            return p.getPoupilarite();
        }
        return null;

    }


    @Override
    @Scheduled(fixedRate = 5000)
    public void poupilariteScud() {

        List<Profile>lp = retrieveProfil();
        for (Profile p :
                lp) {
            if(p.getFollowersNbr() == 0){

                p.setPoupilarite(Poupilarite.notfamous);
                profilRepository.save(p);
            }

            if (p.getFollowersNbr() <= 10 && p.getFollowersNbr()>0) {
                p.setPoupilarite(Poupilarite.pouplar);
                profilRepository.save(p);
            }


            if (p.getFollowersNbr() <= 100 && p.getFollowersNbr() >= 10) {
                p.setPoupilarite(Poupilarite.famous);
                profilRepository.save(p);
            }



            else{

                p.setPoupilarite(Poupilarite.superFamous);
                profilRepository.save(p);
            }



        }

    }

}
