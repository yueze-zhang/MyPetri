package PetriNet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public  class PetriNet {

    public Map<Integer,Place> placeList = null;                                 //记录这个网所有place
    public Map<Integer,TransitionNode> transitionNodesList = null;     //记录这个网所有的变迁

    public int getStarNodeNumber() {return starNodeNumber; }
    public void setStarNodeNumber(int starNodeNumber) { this.starNodeNumber = starNodeNumber; }
    public int getEndNodeNumber() { return endNodeNumber; }
    public void setEndNodeNumber(int endNodeNumber) { this.endNodeNumber = endNodeNumber; }
    public int getStateNodeNumber() { return stateNodeNumber; }
    public void setStateNodeNumber(int stateNodeNumber) { this.stateNodeNumber = stateNodeNumber; }

    private int starNodeNumber;
    private int endNodeNumber;
    private int stateNodeNumber;



    private State startState = null;                            //初始状态
    private State endState = null;                              //结束状态
    public State getStartState() { return startState; }
    public void setStartState(State startState) { this.startState = startState; }
    public State getEndState() { return endState; }
    public void setEndState(State endState) { this.endState = endState; }


    PetriNet(Map<Integer,Place> placeList, Map<Integer,TransitionNode> transitionNodesList,
             State startState, State endState){
        this.placeList = placeList;
        this.transitionNodesList = transitionNodesList;
        this.startState= startState;
        this.endState = endState;
    }




}
