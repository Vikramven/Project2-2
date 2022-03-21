package Visuals.MazeGenerator;

import Visuals.Path.Move;
import Visuals.Path.Position;
import Visuals.entities.Entity;
import Visuals.terrain.Terrain;
import org.lwjglx.util.vector.Vector3f;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class GenMaze {

    public static void main(String[] args) {

        System.out.println("Euclidean - Manhattan (Step Count)");

        for (int i = 0; i < 1; i++) {
            Maze maze = generateMaze(15, 15);
            List<Position> manhattanPath = Move.getPath(maze.start, maze.end, maze.mazeMatrix);
            List<Position> euclidPath = Move.getPath(maze.start, maze.end, maze.mazeMatrix, true);

            System.out.println(euclidPath.size() + " - " + manhattanPath.size());

            System.out.println("Manhattan Path");
            System.out.println(" ");
            maze.printPath(manhattanPath);


            System.out.println(" ------ ");
            System.out.println("Euclidean Path");
            System.out.println(" ");
            maze.printPath(euclidPath);



        }

    }


    public static class Maze{

        public int[][] mazeMatrix;
        public Position start;
        public Position end;

        public Maze(int[][] mazeMatrix, Position start, Position end){
            this.mazeMatrix = mazeMatrix;
            this.start = start;
            this.end = end;
        }

        public void printPath(List<Position> path){

            for (int i = 0; i < this.mazeMatrix.length; i++) {
                String line = "";
                for (int j = 0; j < this.mazeMatrix[0].length; j++) {
                    String val = String.valueOf(this.mazeMatrix[i][j]);
                    for (Position pos : path){
                        if (pos.samePos(new Position(i,j))){
                            val = "X";
                        }
                    }
                    //val = "X";
                    line += val;
                }
                System.out.println(line);
            }
        }
    }


    public static Maze generateMaze(int mazeHeight, int mazeLength){
        int line = 0;
        int[][] mazeMatrix = new int[mazeHeight*2+1][mazeLength*2+1];

        Position start = null;
        Position end = null;

        MazeForSale maze = new MazeForSale(mazeLength,mazeHeight);
        String a = maze.toString();
        //System.out.println(a);

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        System.out.println(maze.toString());
        System.out.flush();
        System.setOut(old);
        Scanner scanner = new Scanner(baos.toString());
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            for (int i = 0; i < s.length(); i++){
                char c = s.charAt(i);
                if(c == '1'){
                    mazeMatrix[line][i] = 1;
                }
                else if(c == '0'){
                    mazeMatrix[line][i] = 0;
                }
                else if(c == 'S'){
                    start = new Position(line,i);
                    mazeMatrix[line][i] = 0;
                }
                else if(c == 'E'){
                    end = new Position(line,i);
                    mazeMatrix[line][i] = 0;
                }
            }
            line++;
        }
        scanner.close();

        Maze mazeObject = new Maze(mazeMatrix, start, end);

        return mazeObject;

    }


}
