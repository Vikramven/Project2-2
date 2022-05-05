package Controller;

import java.util.ArrayList;

public class Wall extends Rectangle {

    public Wall(int x1, int y1,int x3,int y3){
        super(x1,y1,x3,y3);
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