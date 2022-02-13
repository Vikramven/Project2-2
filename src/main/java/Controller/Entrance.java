package Controller;

import java.util.ArrayList;

public abstract class Entrance implements MapObject {

    private ArrayList<Integer> coordIn = new ArrayList<Integer>();

    private ArrayList<Integer> coordOut = new ArrayList<Integer>();

    private double degreeOut;

    public Entrance(int x1, int y1, int x2, int y2, int x3, int y3, double degree){
        this.coordIn.add(x1); this.coordIn.add(y1); this.coordIn.add(x2); this.coordIn.add(y2); //starting points
        this.coordOut.add(x3); this.coordOut.add(y3); //exit point
        this.degreeOut = degree;
    }


}
