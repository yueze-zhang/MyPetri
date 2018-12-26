package PetriNet;

import java.util.PriorityQueue;
import java.util.Queue;

public class Place extends Node{

    Queue<Token> token = new PriorityQueue<Token>();             // 这个Place中所有的token ,按有优先度排列

    @Override
    public String toString() {
        return "Place [id=" + ID + ", label=" + label +  "]";
    }
}
