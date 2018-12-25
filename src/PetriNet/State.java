package PetriNet;

import java.util.*;

public class State {

    private State parent;                                               //定义当前状态的上一个状态
    private int[] currentPlaceValue;                                    //记录当前所有place的token
    List<Place> placeList = new ArrayList<Place>();                     //记录当前状态下的所有place

    public int[] getCurrentPlaceValue() {
        for(int i = 0 ; i < placeList.size() ; i++) {                   //获取当前的所有place的token
            currentPlaceValue[i] = placeList.get(i).getToken();
        }
        return currentPlaceValue;
    }
}
