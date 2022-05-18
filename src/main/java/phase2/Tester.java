package phase2;

import Controller.FileParser;
import Controller.ReadFiles;
import Controller.Variables;

public class Tester {

    public static void main(String args[]){

        String path = "recources/testmap.txt";
        String[] unparsedVars = new String[0];
        Variables v = new Variables();

        try {
            unparsedVars = ReadFiles.readFileAsString(path);
            v = FileParser.parser(path);
        } catch (Exception e) {
            System.out.println("AAAAAAAAAAAAAAAAAAa");
            e.printStackTrace();
        }

        Map map = new Map(v);
        System.out.println(map);
        map.printAgentPos();
        //map.moveAllAgents();
        //map.printAgentPos();
        //System.out.println(map);
    }
}
