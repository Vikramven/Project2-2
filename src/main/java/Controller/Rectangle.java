package Controller;

import java.util.ArrayList;

public abstract class Rectangle implements MapObject {


    private ArrayList<Integer> coords = new ArrayList<Integer>();

    /*
    the rectangle is represented in an int array : [x1,y1,x2,y2,x3,y3,x4,y4]

 D(x4,y4) _____________ C (x3,y3)
          |            |
          |            |
A(x1,y1)  |____________| B (x2,y2)

x2 = x3, y2 = y1 <- that's how we get all 4 points
x4 = x1, y4 = y3
     */

    public Rectangle(int x1, int y1,int x3,int y3){
        coords.add(x1); coords.add(y1); //point A
        coords.add(x3); coords.add(y1); //point B
        coords.add(x3); coords.add(y3); //point C
        coords.add(x1); coords.add(y3); //point D
    }

    public ArrayList<Integer> getCoords(){
        return coords;
    }

}
