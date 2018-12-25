package PetriNet;

public class Place extends Node{

    private int token;
    public int getToken() { return token; }
    public void setToken(int token) {this.token= token; }

    @Override
    public String toString() {
        return "Place [id=" + ID + ", label=" + label +  "]";
    }
}
