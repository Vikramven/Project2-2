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
      mapHeight = variables.getHeight();
      mapWidth = variables.getWidth();
      matrix = new int[mapWidth][mapHeight]; //dimension of the map
       walls = variables.getWalls(); //placing walls on the map
      buildingWalls(matrix);//update the map with the walls

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
          Agent newAgent = new Agent(0); //create the Agent; 0 for Guard


        /*     Place them on the Map
               same principle as the wall
         */

      }
   }


   public void TeamTrace(){

   }
}
