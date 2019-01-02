package PetriNet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public  class PetriNet {

     public Map<Integer,Place> placeList = null;                                 //记录这个网所有place
     public Map<Integer,TransitionNode> transitionNodesList = null;     //记录这个网所有的变迁


    PetriNet(Map<Integer,Place> placeList, Map<Integer,TransitionNode> transitionNodesList){
        this.placeList = placeList;
        this.transitionNodesList = transitionNodesList;
    }



}
