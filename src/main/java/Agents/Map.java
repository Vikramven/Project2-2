package Agents;
import Controller.Variables;
import java.lang.Integer;
import java.util.ArrayList;
import Controller.Wall;

/*
     The General Map is :
         1/ BASIC VERSION : accessible by all Agents: complete knowledge
         2/ UPGRADED VERSION : restrained vision, partial knowledge for each agent
               Works like a puzzle to be completed

   We represent the map as a double Array.
*/

public class Map{
//INSTANCES of class Map
   private int mapHeight; //map dimensions
   private int mapWidth;
   private int[][] matrix;// SubMap storing the walls and the agents
    private int[][] trace; // SubMap storing the map % exploration
   private ArrayList <Wall> walls; //check every 2 integers
   private Variables variables = new Variables(); // why ?
   private Tile[][] tiles;
   private ArrayList<Agent[]>;
   private Agent[] teamGuards;
   private Agent[] teamIntruders;

    /* METHOD(1): Map
     *   Map object constructor
     *   create the matrix and trace instances
     * */
   public Map(){
      mapHeight = variables.getHeight();
      mapWidth = variables.getWidth();
      matrix = new int[mapWidth][mapHeight]; //dimension of
      tiles = new Tile[mapWidth][mapHeight];
      teamGuards = new Agent[variables.getNumberOfGuards()];
      walls = variables.getWalls(); //placing walls on the map
      buildingWalls(matrix);//update the map with the wall
      trace = new int[mapWidth][mapHeight]; //dimension of
      createTrace();
   }
   /* METHOD(2): mapInit
   *   stores zero in every map position
   *    initialize both the Matrix (Int containing wall and agent)
   * */
public void mapInit(){
       for(int i =0; i < getMapHeight(); i++ ){
           for(int j =0; i < getMapWidth(); j++ ){
                setMatrix(i, j , 0);
                this.tiles[i][j] = new Tile(i,j);
           }
       }
}//mapInit

    /* METHOD(3): buildingWalls
     *   places the wall as infinit values on the map
     * */
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
              tiles[x1][i].placeWall();
              tiles[x2][i].placeWall();
              }
          for(int j = x1/*left border*/; j<x2+1/*right border*/; j++){
              matrix[j][y1] = Integer.MAX_VALUE;
              matrix[j][y2] = Integer.MAX_VALUE;
              tiles[j][y1].placeWall();
              tiles[j][y2].placeWall();
          }
          //this only sets the borders of the walls to infinity
      }
   }

    /* METHOD(4): createTrace
     *   create the Trace as a copy of initial Matrix with the walls
     * */
    public void createTrace(){
        for(int i =0; i < getMapHeight(); i++ ){
            for(int j =0; i < getMapWidth(); j++ ){
                this.trace[i][j] = this.matrix[i][j];
            }
        }
    }

    /* METHOD(5): teamCreation
     *   create the Agents and stores them in a fixed sized array
     * */
    public void teamCreation(/*pass an int to identify the group*/)){
        double initialAngle =  java.lang.Math.toRadians(360) / variables.getNumberOfGuards();

      for(int i = 0; i < variables.getNumberOfGuards(); i++){
          Agent newAgent = new Agent(0); //create the Agent; 0 for Guard
          newAgent.setInitialAngle(initialAngle);
          teamGuards[i] = newAgent;
          initialAngle =+ initialAngle; // increase by one basic unit
      }
   }
    /* METHOD(6): convertPosition
     *   chooses optimal positions for each Agent along the spawning rectangle area
     *  insures good perimeter coverage
     * */

   public void convertPosition(Agent[] team){
       int teamSize = team.length();
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


/* METHOD (7): TeamTrace
*   Keeps track of all the traces of every agent
*   All position that have been visited so far
* */
    public void TeamTrace(){
        for(int i = 0; i < team.length(); i++){
            updateTrace(team[i].getLastVisited());
        }
    }
    /* METHOD (8): updateTrace
     *    Helper method of TeamTrace
     * it should be called after each move
     *  OR the principal should be called after each round
     * */
    public void updateTrace(int [] latestTrace){
        setTrace(latestTrace[0],latestTrace[1], 1);
    }
    /* METHODS: GETTERS AND SETTER
    *   to access private instances of the class
     * */
    public int[][] getMatrix(){return matrix;};
   public int getMapHeight(){return mapHeight;};
   public int getMapWidth(){return mapWidth;};
   public int getFieldCost(int x, int y){
       return matrix[x][y];
   }
   public void setMatrix(int x, int y, int value){
       matrix[x][y] = value;
   }
    public void setTrace(int x, int y, int value){
       matrix[x][y] = value;
       tiles[x][y].placeTrace();
    }

}
