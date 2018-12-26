package PetriNet;

import java.util.ArrayList;
import java.util.List;

public class PetriNet {

    List<Place> placeList = new ArrayList<Place>();                                 //记录这个网所有place
    List<TransitionNode> transitionNodesList = new ArrayList<TransitionNode>();     //记录这个网所有的变迁
    List<InputEdge> InputEdges = new ArrayList<InputEdge>();                                    //记录所有的变迁
    List<OutputEdge> outputEdges = new ArrayList<OutputEdge>();                                    //记录所有的变迁

    PetriNet(List<Place> placeList, List<TransitionNode> transitionNodesList,List<InputEdge> InputEdges,List<OutputEdge> outputEdges){
        this.placeList = placeList;
        this.InputEdges = InputEdges;
        this.outputEdges = outputEdges;
        this.transitionNodesList = transitionNodesList;
    }



}
