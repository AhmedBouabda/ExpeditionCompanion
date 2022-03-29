package tn.esprit.expeditioncompanion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.expeditioncompanion.IService.IFlowService;
import tn.esprit.expeditioncompanion.entities.Flow;

@RestController
public class FlowController {
    @Autowired
    IFlowService flowService ;


    @PostMapping("/add_flow/{idu}/{idp}")
    @ResponseBody
    public Flow addFlow(@PathVariable("idu")  long iduser , @PathVariable("idp")  long idprofil) {
        return flowService.addFolow(idprofil,iduser);

    }
    @DeleteMapping("/remove_flow/{profil-id}/{user-id}")
    @ResponseBody
    public void removeFlow(@PathVariable("profil-id")  long profilid,@PathVariable("user-id")  long userid) {
        flowService.DeleteFlow(profilid ,userid);

    }
}
