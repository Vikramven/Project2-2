package Controller;

import java.util.ArrayList;

public class Texture implements MapObject {

    private int[] coords;

    public Texture(int x1, int x2, int x3, int x4, int x5, int x6){
        coords[0] = x1;
        coords[1] = x2;
        coords[2] = x3;
        coords[3] = x4;
        coords[4] = x5;
        coords[5] = x6;
    }
}
