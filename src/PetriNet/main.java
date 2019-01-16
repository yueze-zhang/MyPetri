package PetriNet;
import java.util.Arrays;
import java.util.List;

import static PetriNet.xmlDoc.*;

public class main {
    static PetriNet petri;
    static PetriNet prepare;
    public static void main(String args[]) {
        String fileName = "G:\\Document\\FMS\\petri\\MyPetri\\MyPetri\\src\\res\\PetriNet5.xml";
        try {
            petri = getPetriNetDocument(fileName);
            prepare = getPetriNetPrepareDocument(fileName);


        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
        try {
            int[] arrivalStateTime = AStarPrepare.AStarPrepareStart(prepare).clone();
        AStarKernel.AStarStart(petri,arrivalStateTime);
        } catch (Exception e) {}
//        System.out.println(petri.getStartState().toString()); //获取初始状态
//        System.out.println(petri.getEndState().toString()); //获取结束状态
//        int[]testvalue = command.ChangeTransition(petri,petri.getStartState(),0);
//        System.out.println(Arrays.toString(testvalue));
//       testvalue = command.ChangeTransition(petri,petri.getStartState(),1);
//        System.out.println(Arrays.toString(testvalue));


    }

}