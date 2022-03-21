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


        return map;
    }
}
