package tn.esprit.expeditioncompanion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.expeditioncompanion.IService.IFlowService;
import tn.esprit.expeditioncompanion.IService.IProfilService;
import tn.esprit.expeditioncompanion.IService.IUserService;
import tn.esprit.expeditioncompanion.Repository.IFlowRepository;
import tn.esprit.expeditioncompanion.entities.Flow;
import tn.esprit.expeditioncompanion.entities.Profile;
import tn.esprit.expeditioncompanion.entities.User;

@Service
public class FolowService implements IFlowService {

    @Autowired
    IUserService userService;
    @Autowired
    IProfilService profilService ;
    @Autowired
    IFlowRepository flowRepository;



    @Override
    public Flow addFolow(long idp, long idu) {

        Flow f = new Flow();
        Profile p = profilService.findProfilById(idp);
        User u = userService.findUserById(idu);

        f.setIduser(u);
        f.setIdProfilEnv(p);
        p.setFollowersNbr(p.getFollowersNbr()+1);
        profilService.addProfil(p);






        return flowRepository.save(f);
    }

    @Override
    public String DeleteFlow(Long idp, Long idu) {

        Profile p = profilService.findProfilById(idp);
        User u = userService.findUserById(idu);
        Flow f = flowRepository.findFlowByIdProfilEnvAndIduser(p,u);

        flowRepository.delete(f);
        return "Inflow";
    }
}
