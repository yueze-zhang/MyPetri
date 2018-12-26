package PetriNet;
import java.util.List;
class main {

    public static void main(String args[]) {
        String fileName = "src/res/PetriNet.xml";
        try {
            List<Place> list = xmlDoc.getPlace(fileName);
            for (Place place : list) {
                System.out.println(place);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}