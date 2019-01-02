package PetriNet;
import java.util.ArrayList;
import java.util.List;


public class TransitionNode extends Node {



    public int[] inputedges = null;
    public int[] outputedges = null;

    public int[] getInputedges() { return inputedges; }
    public void setInputedges(int[] inputedges) { this.inputedges = inputedges; }
    public int[] getOutputedges() { return outputedges; }
    public void setOutputedges(int[] outputedges) { this.outputedges = outputedges; }

    @Override
    public String toString() {
        return "TransitionNode [id=" + ID + ", label=" + label +  "]";
    }
}
