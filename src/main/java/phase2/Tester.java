package phase2;

import Agents.Map;
import Controller.ReadFiles;
import phase2.RayCasting.RayCasting;

import java.util.ArrayList;

public class Tester {

    public static void main(String args[]){

        String path = "";
        String[] unparsedVars = new String[0];
        try {
            unparsedVars = ReadFiles.readFileAsString("recources/testmap.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        Map a = new Map(unparsedVars);
//        int height = a.getVariables().getHeight();
//        int width = a.getVariables().getWidth();
//        System.out.println(height + " " + width);
//        RayCasting ray = new RayCasting(height, width);
//        ray.convertToRayCastingMap(0,0, width, height, 1, width);
//        System.out.println(ray.toString());

    }
}
