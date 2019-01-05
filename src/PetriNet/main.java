package PetriNet;
import java.util.Arrays;
import java.util.List;

import static PetriNet.xmlDoc.*;

public class main {
    static PetriNet petri;
    public static void main(String args[]) {
        String fileName = "D:\\document\\FMS\\MyPetri\\src\\res\\PetriNet.xml";
        try {
            petri = getPetriNetDocument(fileName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
//        System.out.println(petri.getStartState().toString()); //获取初始状态
//        System.out.println(petri.getEndState().toString()); //获取结束状态
//        int[]testvalue = command.ChangeTransition(petri,petri.getStartState(),0);
//        System.out.println(Arrays.toString(testvalue));
//       testvalue = command.ChangeTransition(petri,petri.getStartState(),1);
//        System.out.println(Arrays.toString(testvalue));
        AStarKernel.AStarStart(petri);

    }

}