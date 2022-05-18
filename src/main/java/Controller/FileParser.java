package Controller;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileParser {

    public static Variables parser(String path) {
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


        String[] unparsedVals = nextVariable.split(" ");
        //ArrayList<String> unparsedVals = new ArrayList<>(Arrays.asList(unparsedValsArr));
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
                v.createWall(Integer.parseInt(coords[0+i].trim()),Integer.parseInt(coords[1+i].trim()),Integer.parseInt(coords[2+i].trim()),
                        Integer.parseInt(coords[3+i].trim()));
                break;
            case "teleport":
                System.out.print("tp coords: ");
                System.out.println(Arrays.toString(unparsedVals));
                v.createTeleport(Integer.parseInt(coords[0+i].trim()),Integer.parseInt(coords[1+i].trim()),Integer.parseInt(coords[2+i].trim()),
                        Integer.parseInt(coords[3+i].trim()),Integer.parseInt(coords[4+i].trim()),Integer.parseInt(coords[5+i].trim()),
                        Double.parseDouble(coords[6+i].trim()));
                break;
            case "spawnAreaGuards":
                v.createSpawnAreaGuards(Integer.parseInt(coords[0+i].trim()),Integer.parseInt(coords[1+i].trim()),Integer.parseInt(coords[2+i].trim()),
                        Integer.parseInt(coords[3+i].trim()));
                break;
            case "spawnAreaIntruders":
                v.createSpawnAreaIntruders(Integer.parseInt(coords[0+i].trim()),Integer.parseInt(coords[1+i].trim()),Integer.parseInt(coords[2+i].trim()),
                        Integer.parseInt(coords[3+i].trim()));
                break;
            case "isGoal":
                v.createGoal(Integer.parseInt(coords[0+i].trim()),Integer.parseInt(coords[1+i].trim()),Integer.parseInt(coords[2+i].trim()),
                        Integer.parseInt(coords[3+i].trim()));
                break;
            case "shade":
                v.createShade(Integer.parseInt(coords[0+i].trim()),Integer.parseInt(coords[1+i].trim()),Integer.parseInt(coords[2+i].trim()),
                        Integer.parseInt(coords[3+i].trim()));
                break;
        }


    }


    private static String unnecessaryLines(String s) {
        if (s.contains("//")) {
            return s.split("//")[0];
        }
        return s;
    }
}




