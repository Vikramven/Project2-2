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
   private ArrayList <Wall> walls; //check every 2 integers
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
            int coords = walls.get(i).getCoords();
            int x1 = coords.get(1);
            int x2 = walls.get(i)[2];
            int y1 = walls.get(i)[3];
            int y2 = walls.get(i)[4];
            //For each of the walls : use two nested for loops to place the rectangle on the map
          for(int i = y1 /*LEFT UP*/; i < y2+1 /*RIGHT UP*/; i++){
              matrix[x1][i] = Integer.MAX_VALUE;
              matrix[x2][i] = Integer.MAX_VALUE; //this sets the cost to infinity for the vertical walls
              }
          for(int i = x1; i<x2+1; i++){
              matrix[i][y1] = Integer.MAX_VALUE;
              matrix[i][y2] = Integer.MAX_VALUE;
          }
      }
   }

   public int[][] getMatrix(){return matrix;};
   public int getMapHeight(){return mapHeight;};
   public int getMapWidth(){return mapWidth;};
   public int getFieldCost(int x, int y){
       return matrix[x][y];
   }


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
