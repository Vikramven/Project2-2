package newShit;

import Controller.ReadFiles;

public class Tester {

    public static void main(String args[]){

        String path = "";
        String[] unparsedVars = new String[0];
        try {
            unparsedVars = ReadFiles.readFileAsString("recources/testmap.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MapCreation a = new MapCreation(unparsedVars);

    }
}
