package Controller;

import java.util.ArrayList;

public abstract class Rectangle extends MapObject {



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
        super();
        super.coords.add(x1); super.coords.add(y1); //point A
        super.coords.add(x3); super.coords.add(y1); //point B
        super.coords.add(x3); super.coords.add(y3); //point C
        super.coords.add(x1); super.coords.add(y3); //point D
    }

    public ArrayList<int[]> getPoints(){
        int[] start = new int[2];
        int[] end = new int[2];

        start[0] = coords.get(0);
        start[1] = coords.get(1);

        end[0] = coords.get(4);
        end[1] = coords.get(5);

        ArrayList<int[]> points = new ArrayList<>();

        int xLength = Math.abs(start[0]-end[0]);
        int yLength = Math.abs(start[1]-end[1]);
        for(int i = 0; i<yLength; i++){
            for(int j = 0; j<xLength; j++){
                int[] coords = new int[2];
                coords[0] = j;
                coords[1] = i;
                points.add(coords);
            }
        }
        return points;
    }
}
