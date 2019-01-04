package PetriNet;
import java.util.List;

import static PetriNet.xmlDoc.*;

public class main {
    static PetriNet petri;
    public static void main(String args[]) {
        String fileName = "src/res/PetriNet.xml";
        try {
            petri = getPetriNetDocument(fileName);
            petri.getStartState().toString();
            System.out.println(petri.getStartState().toString());



        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }

}