package tn.esprit.expeditioncompanion.IService;

import tn.esprit.expeditioncompanion.entities.Flow;

public interface IFlowService {

    Flow addFolow ( long idp , long idu);

    String DeleteFlow (Long Idp ,Long idu);
}
