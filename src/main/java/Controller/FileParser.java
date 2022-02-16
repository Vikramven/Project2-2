package Controller;
import javafx.geometry.Rectangle2D;

import java.awt.Point;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileParser {
    private static Variables map;

    public static Variables readFile(String path) {
        Path file = Paths.get(path);
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
        }

        return map;
    }

    public static void parseNextLine(String nextLine, int countLines) {
        try (Scanner scan = new Scanner(nextLine)) {

            if (scan.hasNext()) {
                String check = scan.next();
                String value = scan.next();
                /**
                 * trim all lead and tail spaces if any
                 */
                check = check.trim();
                value = value.trim();
                String[] locations = value.split(" ");//for variables with array values
                switch (check) {
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
                    case "gameMode":
                        map.setMode(Integer.parseInt(value));
                        /**
                         * didn't really get coordinate system, but the dimensions is 4 entries
                         * create wall of dimensions
                         * how to get the height and width, check 3rd and 4th dimensions.
                         */
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
                    case "shaded":
                        map.createShade(Integer.parseInt(locations[0]),
                                Integer.parseInt(locations[1]),
                                Integer.parseInt(locations[2]),
                                Integer.parseInt(locations[3]));
                    case "texture":
                       map.createTexture(Integer.parseInt(locations[0]),
                               Integer.parseInt(locations[1]),
                               Integer.parseInt(locations[2]),
                               Integer.parseInt(locations[3]),
                               Integer.parseInt(locations[4]),
                               Integer.parseInt(locations[5]));
                }

            }
        }
    }
}
