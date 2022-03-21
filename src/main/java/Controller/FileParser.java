package Controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileParser {
    private static Variables map;

    public static Variables readFile(String[] variables) {
        /*Path file = Paths.get(path);
        map = new Variables();//parse and set all values into variables class
        try (Scanner scan = new Scanner(file)) {
            int countNumberLines = 1;//we need to handle an exception for this case
            while (scan.hasNextLine()) {
                parseNextLine(scan.nextLine(), countNumberLines);
                countNumberLines++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Path invalid");
        }*/

        for(String line : variables){
            parseNextLine("");
        }

        return map;
    }

    public static void parseNextLine(String check, String value) {


                /**
                 * trim all lead and tail spaces if any
                 */
                check = check.trim();
                value = value.trim();
                String[] locations = value.split(" ");//for variables with array values
                switch (check) {
                    case "gameMode":
                        map.setMode(Integer.parseInt(value));
                    case "height":
                        map.setHeight(Integer.parseInt(value));
                        break;
                    case "width":
                        map.setWidth(Integer.parseInt(value));
                        break;
                    case "scaling":
                        map.setScaling(Double.parseDouble(value));
                        break;
                    case "numGuards":
                        map.setNumberOfGuards(Integer.parseInt(value));
                        break;
                    case "numIntruders":
                        map.setNumberOfIntruders(Integer.parseInt(value));
                        break;
                    case "baseSpeedIntruder":
                        map.setWalkingSpeedIntruder(Double.parseDouble(value));
                        break;
                    case "sprintSpeedIntruder":
                        map.setSprintingSpeedIntruder(Double.parseDouble(value));
                        break;
                    case "baseSpeedGuard":
                        map.setWalkingSpeedGuard(Double.parseDouble(value));
                        break;
                    case "sprintSpeedGuard":
                        map.setSprintingSpeedGuard(Double.parseDouble(value));
                        break;
                    case "distanceViewing":
                        map.setDistanceViewing(Integer.parseInt(value));
                        break;
                    case "distanceSmelling":
                        map.setDistanceSmelling(Integer.parseInt(value));
                        break;
                    case "distanceHearingWalking":
                        map.setDistanceHearingWalking(Integer.parseInt(value));
                        break;
                    case "distanceHearingSprinting":
                        map.setDistanceHearingSprinting(Integer.parseInt(value));
                        break;
                    case "numberMarkers":
                        map.setNumberMarkers(Integer.parseInt(value));
                        break;
                    case "timeStep":
                        map.setTimeStep(Double.parseDouble(value));
                        break;
                    case "targetArea":
                        map.setTargetArea(Integer.parseInt(locations[0]),
                                Integer.parseInt(locations[1]),
                                Integer.parseInt(locations[2]),
                                Integer.parseInt(locations[3]));
                        break;
                    case "spawnAreaIntruders":
                        map.setSpawnAreaIntruders(Integer.parseInt(locations[0]),
                                Integer.parseInt(locations[1]),
                                Integer.parseInt(locations[2]),
                                Integer.parseInt(locations[3]));
                        break;
                    case "spawnAreaGuards":
                        map.setSpawnAreaGuards(Integer.parseInt(locations[0]),
                                Integer.parseInt(locations[1]),
                                Integer.parseInt(locations[2]),
                                Integer.parseInt(locations[3]));
                        break;
                    case "wall":
                        map.createWall(Integer.parseInt(locations[0]),
                                Integer.parseInt(locations[1]),
                                Integer.parseInt(locations[2]),
                                Integer.parseInt(locations[3]));
                        break;
                    case "teleport":
                        map.createTeleport(Integer.parseInt(locations[0]),
                                Integer.parseInt(locations[1]),
                                Integer.parseInt(locations[2]),
                                Integer.parseInt(locations[3]),
                                Integer.parseInt(locations[4]),
                                Integer.parseInt(locations[5]),
                                Double.parseDouble(locations[6]));
                        break;
                    case "shaded":
                        map.createShade(Integer.parseInt(locations[0]),
                                Integer.parseInt(locations[1]),
                                Integer.parseInt(locations[2]),
                                Integer.parseInt(locations[3]));
                        break;
                    case "texture":
                        map.createTexture(Integer.parseInt(locations[0]),
                                Integer.parseInt(locations[1]),
                                Integer.parseInt(locations[2]),
                                Integer.parseInt(locations[3]),
                                Integer.parseInt(locations[4]),
                                Integer.parseInt(locations[5]));
                        break;
                }

            }

    }
}