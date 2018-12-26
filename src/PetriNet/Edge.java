package PetriNet;

public class Edge {
    private int ID;
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    private int sourceID;
    public int getSourceID() { return sourceID; }
    public void setSourceID(int sourceID) { this.sourceID = sourceID; }

    private int destinationID;
    public int getDestinationID() { return destinationID; }
    public void setDestinationID(int destinationID) { this.destinationID = destinationID; }

    private Node source;
    private Node destination;
    public Edge(){}

    public Edge(Node source, Node destination){    //设置胡的起点和终点
        this.source = source;
        this.destination =destination;
            }

}
