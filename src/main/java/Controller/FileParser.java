package Controller;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class FileParser {

    public static Variables parser(Scanner scanner, String path) {
        //Path file = Paths.get(path);
        String content = "";
        try{
            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(content);
        String[] unparsedVals = content.split("\n");
        //System.out.println("vars: "+ unparsedVals[1]);
        Variables v = new Variables();
        //System.out.println("EEEEEEEEEE");
        /*int numberLines = 1;
        while (scanner.hasNextLine()) {
            numberLines++;
            parseVariables(v, scanner.nextLine(), numberLines);
        }*/
        for(String s: unparsedVals){
            parseVariables(v,s,0);
        }
        return v;
    }

    public static Variables parse(String[] unparsedVals){
        Variables v = new Variables();
        for(String s : unparsedVals){
            parseVariables(v,s,0);
        }
        return v;
    }


    public static void parseVariables(Variables v, String nextVariable, int numberLines) {
            //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            //scan.useDelimiter("=");
                //String id = scan.next();
                ///String value = scan.next();
                //value = unnecessaryLines(value);

                String[] unparsedVals = nextVariable.split(" ");
                String id = unparsedVals[0];

                String value = unparsedVals[2];
        System.out.println("id: "+id+", val: "+value);
        System.out.println("coords: "+unparsedVals[unparsedVals.length-1]);

                //System.out.println(Arrays.toString(unparsedVals));


        for(String s: unparsedVals){
            s = s.trim();
        }
                id = id.trim();
                value = value.trim();
                //value = value.trim();
                int n = unparsedVals.length - 2;
                String[] coords = unparsedVals;



                int i = 2;
                //System.arraycopy(unparsedVals,1,newArray,0,n);
                switch (id){

                    case "height":
                        v.setHeight(Integer.parseInt(value));
                        break;
                    case "width":
                        v.setWidth(Integer.parseInt(value));
                        break;
                    case "scaling":
                        v.setScaling((float) Double.parseDouble(value));
                        break;
                    case "numGuards":
                        v.setNumberOfGuards(Integer.parseInt(value));
                        break;
                    case "numIntruders":
                        v.setNumberOfIntruders(Integer.parseInt(value));
                        break;
                    case "baseSpeedIntruder":
                        v.setWalkingSpeedIntruder((float) Double.parseDouble(value));
                        break;
                    case "sprintSpeedIntruder":
                        //v.setSprintingSpeedIntruder(Double.parseDouble(value));
                        break;
                    case "baseSpeedGuard":
                        v.setWalkingSpeedGuard((float) Double.parseDouble(value));
                        break;
                    case "wall":
                        System.out.println("wall x1:"+coords[4]);
                        v.createWall(Integer.parseInt(coords[0+i]),Integer.parseInt(coords[1+i]),Integer.parseInt(coords[2+i]),
                                Integer.parseInt(coords[3+i]));
                        //v.setNumberWalls(Integer.parseInt(value));
                    case "teleport":
                        v.createTeleport(Integer.parseInt(coords[0+i]),Integer.parseInt(coords[1+i]),Integer.parseInt(coords[2+i]),
                                Integer.parseInt(coords[3+i]),Integer.parseInt(coords[4+i]),Integer.parseInt(coords[5+i]),
                                Double.parseDouble(coords[6+i]));
                        //v.setTeleport(Integer.parseInt(value));

                }


    }

    private static String unnecessaryLines(String s) {
        if (s.contains("//")) {
            return s.split("//")[0];
        }
        return s;
    }
}




