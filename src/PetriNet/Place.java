package PetriNet;

import java.util.PriorityQueue;
import java.util.Queue;

public class Place extends Node{

    Queue<Token> token = new PriorityQueue<Token>();             // 这个Place中所有的token ,按有优先度排列

    private int processTime;                                 //这个位置的进程时间
    public int getProcessTime() { return processTime; }
    public void setProcessTime(int processTime) { this.processTime = processTime; }

    @Override
    public String toString() {
        return "Place [id=" + ID + ", label=" + label +  "]";
    }
}
