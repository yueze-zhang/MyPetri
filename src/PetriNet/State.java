package PetriNet;

import java.lang.reflect.Array;
import java.util.*;

public class State implements Comparable<State>, Cloneable {

    private static int count = 0 ;                                      //用于ID自增
    private int stateID;

    private int transitionNodeID;                                      //从父状态到此状态触发的变迁ID
    public int getTransitionNodeID() { return transitionNodeID; }
    public void setTransitionNodeID(int transitionNodeID) { this.transitionNodeID = transitionNodeID; }

    private State parent;                                               //定义当前状态的上一个状态
    public State getParent() { return parent; }
    public void setParent(State parent) { this.parent = parent; }

    private int[] currentToken;                                         //记录当前所有place的token
    public int[] getCurrentToken() { return currentToken; }
    public void setCurrentToken(int[] currentPlace) { this.currentToken = currentPlace; }

    private int time;                                                   //定义当前状态所用的时间
    public int getTime() { return time; }
    public void setAddTime(int time) { this.time += time; }
    public void setTime(int time) { this.time = time; }
    private int gValue;                                                      //G：是个准确的值，是起点到当前结点的代价
    private int hValue;                                                      //H：是个估值，当前结点到目的结点的估计代价
    public int getgValue() { return gValue; }
    public void setgValue(int gValue) { this.gValue = gValue; }
    public int gethValue() { return hValue; }
    public void sethValue(int hValue) { this.hValue = hValue; }

    private int[] currentPlaceWaitTime;                                      //当前状态下place还需要等待的时间
    public int[] getCurrentPlaceWaitTime() { return currentPlaceWaitTime; }
    public void setCurrentPlaceWaitTime(int[] currentPlaceWaitTime) { this.currentPlaceWaitTime = currentPlaceWaitTime; }

    public State(){
        this.stateID = ++count;

    }
    public State(int[]stateValue, State current, int G, int H ,int transitionNodeID, int time){
        this.currentToken = stateValue;
        this.parent = current;
        this.gValue = G;
        this.hValue = H;
        this.transitionNodeID = transitionNodeID;
        this.stateID = ++count;
        this.time = time;
        this.currentPlaceWaitTime = new int[stateValue.length];
    }

    @Override
    public String toString() {
        return "State [id=" + stateID + ", currentToken =" + Arrays.toString(currentToken) +
                ", Time ="+ Integer.toString(time) +", transitionNodeID = "+ transitionNodeID+ "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof int[])
        {
            int[] c = (int[]) obj;
            for(int i = 0; i<currentToken.length; i++){
                if(c[i]!=currentToken [i]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int compareTo(State o) {
        if (o == null) return -1;
        if (time + command.sumInt(currentPlaceWaitTime) > o.getTime() + command.sumInt(o.currentPlaceWaitTime))
            return 1;
        else if (time + command.sumInt(currentPlaceWaitTime) < o.getTime() + command.sumInt(o.currentPlaceWaitTime)) return -1;
        return 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Object object = super.clone();
        return object;
    }

}
