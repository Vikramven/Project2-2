package Controller;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//NEEDC TO PARSE FOR WALLS
//NEED TO PARSE FOR SPAWING ZONE

public class FileParser {
    private static Variables map;

    public static Variables readFile(String path) {
        Path file = Paths.get(path);
        map = new Variables();
        try (Scanner scan = new Scanner(file)) {
            int countLines = 1;
            while (scan.hasNextLine()) {
                parseNextLine(scan.nextLine(), countLines);
                countLines++;
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
                String id = scan.next();
                String value = scan.next();
                id = id.trim();
                value = value.trim();
                String[] coords = value.split(" ");
                switch (id) {
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
                }

            }
        }
    }
}
