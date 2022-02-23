package Agents;
import Controller.Variables;
import java.lang.Integer;
import java.util.ArrayList;
import Controller.Wall;

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
   private Agent [] team = new Agent[variables.getNumberOfGuards()];

   public Map(){
      mapHeight = variables.getHeight();
      mapWidth = variables.getWidth();
      matrix = new int[mapWidth][mapHeight]; //dimension of the map
      walls = variables.getWalls(); //placing walls on the map
      buildingWalls(matrix);//update the map with the walls

   }

   public void buildingWalls(int[][] matrix){
      for(int i = 0; i < walls.size(); i++){
            ArrayList<Integer> coords = walls.get(i).getCoords();
            int x1 = coords.get(0);
            int x2 = coords.get(1);
            int y1 = coords.get(2);
            int y2 = coords.get(3);
            //@zofia For each of the walls : two separate loops, first we build the vertical walls, then the horizontal walls
          for(int j = y1 /*bottom border*/; j < y2+1 /*top border*/; j++){
              matrix[x1][j] = Integer.MAX_VALUE;
              matrix[x2][j] = Integer.MAX_VALUE; //this sets the cost to infinity for the vertical walls
              }
          for(int j = x1/*left border*/; j<x2+1/*right border*/; j++){
              matrix[j][y1] = Integer.MAX_VALUE;
              matrix[j][y2] = Integer.MAX_VALUE;
          }
          //this only sets the borders of the walls to infinity
      }
   }


   public void teamCreation(/*pass an int to identify the group*/)){
      for(int i = 0; i < variables.getNumberOfGuards(); i++){
          Agent newAgent = new Agent(0); //create the Agent; 0 for Guard
          team.add(newAgent);
      }
   }

   public void convertPosition(Agent[] team){
       int teamSize = team.size();
       int x1 = team[0].getAgentSpawning()[0];
       int y1 = team[0].getAgentSpawning()[1];
       int x2 = team[0].getAgentSpawning()[2];
       int y2 = team[0].getAgentSpawning()[3];

       int width = x1- x2;
       int height = y1 - y2;
       int counter = 0;
   //CASE 1:  available position inferior or equal to number of guards
       if(teamSize => (width * height)){
           while( counter  < teamSize ){
               for(int i = x1; i < x2 + 1; i ++){
                   for(int j = y1; j < y2; j ++){
                       //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                       team[i].setAgentPositionX() = i;
                       team[i].setAgentPositionY() = j;
                       counter++;
                   }
               }
           }
       }//END CASE 1

       //CASE 2: available position superior to number of guards
       int decisionRatio= 30/100;

       if(teamSize < decisionRatio * (width * height)){
           while( counter  < teamSize ){
               // top line
               for(int i = x1; i < x2 + 1; i ++){
                   if (counter  < teamSize){
                       //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                       team[counter].setAgentPositionX() = i;
                       team[counter].setAgentPositionY() = y2;
                       counter++;
                   }
               }
               // bottom line
               for(int i = x1; i < x2 + 1; i ++){
                   if (counter  < teamSize){
                       //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                       team[counter].setAgentPositionX() = i;
                       team[counter].setAgentPositionY() = y1;
                       counter++;
                   }
               }
               // start column
               for(int j = y1; j < y2 + 1; j ++){
                   if (counter  < teamSize){
   //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                       team[counter].setAgentPositionX() = x1;
                       team[counter].setAgentPositionY() = j;
                       counter++;
                   }
               }
               // end column
               for(int j = y1; j < y2 + 1; j ++){
                   if (counter  < teamSize){
                       //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                       team[counter].setAgentPositionX() = x2;
                       team[counter].setAgentPositionY() = j;
                       counter++;
                   }
               }
           }
       }//END CASE 2
   }//END convertPosition


   public void TeamTrace(){
     for(int i= 0; i < team.size(); i++){
      updateTrace(team[i].getLastVisited());
     }
   }

   public void updateTrace(in){

   }
   public int[][] getMatrix(){return matrix;};
   public int getMapHeight(){return mapHeight;};
   public int getMapWidth(){return mapWidth;};
   public int getFieldCost(int x, int y){
       return matrix[x][y];
   }


}
