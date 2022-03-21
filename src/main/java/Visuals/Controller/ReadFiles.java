package Visuals.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFiles
{

    public static String[] readFileAsString(String fileName)throws Exception
    {
        String[] values = new String[20];
        int i = 0;

        String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\cmjus\\Documents\\GitHub\\Project2-2\\res\\testmap.txt")));

        Scanner scanner = new Scanner(content);
        while (scanner.hasNextLine() && i<20) {
            String line = scanner.nextLine();
            String[] output;
            output = line.split(" = ");
            values[i] = output[1];
            i++;
        }

        scanner.close();



        return values;
    }

}
