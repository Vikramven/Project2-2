package phase2;

import Controller.ReadFiles;
public class Tester {

    public static void main(String args[]){

        String path = "";
        String[] unparsedVars = new String[0];
        try {
            unparsedVars = ReadFiles.readFileAsString("recources/testmap.txt");
        } catch (Exception e) {
            System.out.println("AAAAAAAAAAAAAAAAAAa");
            e.printStackTrace();
        }

        Map map = new Map(unparsedVars);
        map.allAgentsInit();
        System.out.println(map);
        //map.updateAllAgents();
    }
}
