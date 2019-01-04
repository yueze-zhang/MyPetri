package PetriNet;

import java.util.*;

public class State {

    private int stateID;
    private State parent;                                               //定义当前状态的上一个状态

    private int[] currentToken;                                         //记录当前所有place的token
    public int[] getCurrentToken() { return currentToken; }
    public void setCurrentToken(int[] currentPlace) { this.currentToken = currentPlace; }

    @Override
    public String toString() {
        return "State [id=" + stateID + ", currentToken =" + currentToken +  "]";
    }
}
