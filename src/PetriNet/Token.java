package PetriNet;

public class Token implements Comparable<Token> {

    private int ID;
    private String attribute;
    private int Priority;
    public int getPriority() { return Priority; }
    public void setPriority(int priority) { Priority = priority; }

    @Override
    public int compareTo(Token o) {
        if (o == null) return -1;
        if (Priority > o.getPriority())
            return 1;
        else if (Priority < o.getPriority()) return -1;
        return 0;
    }

}


