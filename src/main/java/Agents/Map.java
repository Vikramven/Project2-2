package Agents;
import Controller.Variables;
import java.lang.Integer;
import java.util.ArrayList;

public class Map{
/*
     The General Map is :
         1/ BASIC VERSION : accessible by all Agents: complete knowledge
         2/ UPGRADED VERSION : restrained vision, partial knowledge for each agent
               Works like a puzzle to be completed

   We represent the map as a double Array.
*/

   private int mapHeight;
   private int mapWidth;
   private int[][] matrix;
   private ArrayList <int[]> walls; //check every 2 integers
   private Variables variables = new Variables();

   public Map(){

      height = variables.getHeight();
      width = variables.getWidth();
      int[][] map = new int[height][width]; //dimension of the map

   }

   public void buildingWalls(int[][] matrix){
      for(int i = 0; i < walls.size(); i++){
            int x1 = walls.get(i)[1];
            int x2 = walls.get(i)[2];
            int y1 = walls.get(i)[3];
            int y2 = walls.get(i)[4];
            //For each of the walls : use two nested for loops to place the rectangle on the map
          for(int a = /*LEFT UP*/; a < /*RIGHT UP*/; a++){
              for(int b = /*LEFT UP*/; b < /*RIGHT DOWN*/; b++){
                  matrix[a][b] = Integer.MAX_VALUE;// infinite cost for walls is set
              }
          }
      }
   }

   public int[][] getMatrix(){return matrix;};
   public int getMapHeight(){return mapHeight;};
   public int getMapWidth(){return mapWidth;};


   public void teamCreation(){
      for(int i = 0; i < variables.getNumberOfGuards(); i++){
          Agent newAgent = new Agent(); //create the Agent

        /*     Place them on the Map
               same principle as the wall
         */

      }
   }
   public void TeamTrace(){

   }
}
