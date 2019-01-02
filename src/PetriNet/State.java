package PetriNet;

import java.util.*;

public class State {

    private State parent;                                               //定义当前状态的上一个状态

    public int[] getCurrentPlace() { return currentPlace; }
    public void setCurrentPlace(int[] currentPlace) { this.currentPlace = currentPlace; }

    private int[] currentPlace;                                    //记录当前所有place的token

}
