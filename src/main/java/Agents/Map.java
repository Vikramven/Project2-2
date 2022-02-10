package Agents;
import Controller.Variables;

public class Map{
/*
     The General Map is :
         1/ BASIC VERSION : accessible by all Agents: complete knowledge
         2/ UPGRADED VERSION : restrained vision, partial knowledge for each agent
               Works like a puzzle to be completed

   We represent the map as a double Array.
*/

   private int height;
   private int width;
   private Variables variables = new Variables();

   public Map(){

      height = variables.getHeight();
      width = variables.getWidth();
      int[][] map = new int[height][width]; //dimension of the map

   }

   public int getMapHeight(){return height;};
   public int getMapWidth(){return width;};

   public void teamCreation(){
      /*where we create the Agent Object based on their spawning position and their number*/
      for(int i = 0; i < variables.getNumberOfGuards(); i ++){
        // Agent newAgent = new Agent();
      }
   }
   public void TeamTrace(){

   }
}
