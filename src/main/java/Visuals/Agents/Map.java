package Visuals.Agents;
import Visuals.Controller.FileParser;
import Visuals.Controller.Variables;
import java.lang.Integer;
import java.util.ArrayList;
import Visuals.Controller.Wall;

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
   //private int[][] matrix;// SubMap storing the walls and the agents
   //private int[][] trace; // SubMap storing the map % exploration
   private ArrayList <Wall> walls; //check every 2 integers
   private Variables variables = new Variables(); // why ?
   private Tile[][] tiles;
   private Agent[] teamGuards;
   private Agent[] teamIntruders;
   private double exploredPercentage;
   private ArrayList<int[]> exploredTiles = new ArrayList<>();
   private int numberOfTiles;

    /* METHOD(1): Map
     *   Map object constructor
     *   create the matrix and trace instances
     * */


   public Map(){
      variables =  FileParser.readFile("./resources/testmap.txt");
      mapHeight = variables.getHeight();
      mapWidth = variables.getWidth();
      numberOfTiles = mapHeight*mapWidth;
      //matrix = new int[mapWidth][mapHeight]; //dimension of
      tiles = new Tile[mapWidth][mapHeight];
      mapInit();
      teamCreation();
      walls = variables.getWalls(); //placing walls on the map
      buildingWalls();//update the map with the wall
   }
   /* METHOD(2): mapInit
   *   stores zero in every map position
   *    initialize both the Matrix (Int containing wall and agent)
   * */
    public void mapInit(){
           for(int i =0; i < mapWidth; i++){
               for(int j =0; j < mapHeight; j++){
                    //setMatrix(i, j , 0);
                    this.tiles[i][j] = new Tile(i,j);
               }
           }
    }//mapInit

    public void teamCreation(/*pass an int to identify the group*/){
    /* creates a team of agents, places them on spawn and gives them initial info */
        double initialAngle =  (double) java.lang.Math.toRadians(360) / variables.getNumberOfGuards();
        for(int i = 0; i < variables.getNumberOfGuards(); i++){
            Agent newAgent = new Agent(0,this.variables); //create the Agent; 0 for Guard
            newAgent.setInitialAngle(initialAngle);
            teamGuards[i] = newAgent;
            initialAngle =+ initialAngle; // increase by one basic unit
        }
        placeAgentsOnSpawn(0);
    }

    public void mapUpdate(){
        /*
         * This method should be called sequentially somewhere
         * 1. move agents
         * 2. update agent location
         * 3. update exploration %
         * 4. update trace, flags etc
         * 5. update seen fields
         * */
        updateAgentLocation();
        updateExplorationPercentage();
        updateSeenTiles();
        mapUpdate();
    }

    private void updateAgentLocation(){
        for(Agent agent:teamGuards){
            int x = agent.getAgentPositionX();
            int y = agent.getAgentPositionY();
            tiles[x][y].removeAgent();
            agent.move(); //method to implement, should be connected to A*
            //agent.updateMap();
            x = agent.getAgentPositionX();
            y = agent.getAgentPositionY();
            tiles[x][y].placeAgent();
        }

        for(Agent agent:teamIntruders){
            break;
            //for phase 2
        }
    }



    private void updateExplorationPercentage(){
        int exploredSum = 0;
        updateExplored();
        for(Tile[] tileRow:tiles){
            for(Tile tile:tileRow){
                if(tile.isExplored()){
                    exploredSum++;
                }
            }
        }
        this.exploredPercentage = (double) exploredSum/ (double) tiles.length;
    }

    /* METHOD(3): buildingWalls
     *   places the wall as infinit values on the map
     * */
   public void buildingWalls(){
      for(int i = 0; i < walls.size(); i++){
            ArrayList<Integer> coords = walls.get(i).getCoords();
            int x1 = coords.get(0);
            int x2 = coords.get(1);
            int y1 = coords.get(2);
            int y2 = coords.get(3);
            //@zofia For each of the walls : two separate loops, first we build the vertical walls, then the horizontal walls
          for(int j = y1 /*bottom border*/; j < y2+1 /*top border*/; j++){
              tiles[x1][i].placeWall();
              tiles[x2][i].placeWall();
              }
          for(int j = x1/*left border*/; j<x2+1/*right border*/; j++){
              tiles[j][y1].placeWall();
              tiles[j][y2].placeWall();
          }
          //this only sets the borders of the walls to infinity
      }
   }

    /* METHOD(4): createTrace
     *   create the Trace as a copy of initial Matrix with the walls
     *
    public void createTrace(){
        for(int i =0; i < getMapHeight(); i++ ){
            for(int j =0; i < getMapWidth(); j++ ){
                this.trace[i][j] = this.matrix[i][j];
            }
        }
    }*/

    public Tile[][] getTiles(){
        return this.tiles;
    }

    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

    public int getMapHeight(){return mapHeight;};
    public int getMapWidth(){return mapWidth;};
    public int getFieldCost(int x, int y){
        return tiles[x][y].getValue();
    }
    public double getExploredPercentage(){return this.exploredPercentage;}

    /* METHOD(5): teamCreation
     *   create the Agents and stores them in a fixed sized array
     *

    METHOD(6): convertPosition
     *   chooses optimal positions for each Agent along the spawning rectangle area
     *  insures good perimeter coverage
     * */

    public void placeAgentsOnSpawn(int teamNumber){
        Agent[] team;

        if(teamNumber==0){
            team = teamGuards;
        }
        else{
            team = teamIntruders;
        }
        ArrayList<Integer> spawnCoords = new ArrayList<>();
        if(team[0].teamCode == 0){
            spawnCoords = this.variables.getSpawnAreaGuards().getCoords();
        }
        else{
            spawnCoords = this.variables.getSpawnAreaIntruders().getCoords();
        }

        int x1 = spawnCoords.get(0); int y1 = spawnCoords.get(1);
        int x2 = spawnCoords.get(2); int y2 = spawnCoords.get(3);

        int width = Math.abs(x1-x2);
        int height = Math.abs(y1-y2);
        int outline = 2*(height+width)-4;
        int spacing = 1;
        ArrayList<int[]> spawnLine = new ArrayList<>();
        if (team.length<=outline){
            spawnLine = outlineOfMatrix(x1,y1,x2,y2);
            spacing = (int) (spawnLine.size()/team.length);
        }
        else {
            int a = x1;
            int b = y1;
            int c = x2;
            int d = y2;
            int[] coords = new int[2];

            do{
                coords[0] = a+1;
                coords[1] = b+1;
                spawnLine.addAll(outlineOfMatrix(a,b,c,d));
                a++;
                b++;
                c--;
                d--;
            }while(!spawnLine.contains(coords));
        }

        for(int i=0; i<team.length; i++){
            int[] coords = spawnLine.get(spacing*i);
            team[i].setInitialCoords(coords);
        }

        if(teamNumber==0){
            teamGuards = team;
        }
        else {
            teamIntruders = team;
        }

    }

    private ArrayList<int[]> outlineOfMatrix(int x1, int y1, int x2, int y2){
        int width = Math.abs(x1-x2);
        int height = Math.abs(y1-y2);
        int outlineLength = 2*(height+width);
        ArrayList<int[]> outline = new ArrayList<>();
        for(int i = y1; i < y2+1; i++){
            int[] coords = new int[2];
            coords[0] = x1;
            coords[1] = i;
            outline.add(coords);
        }
        for(int i = x1+1; i<x2+1; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = y2;
            outline.add(coords);
        }
        for(int i = y2; i>=y1; i--){
            int[] coords = new int[2];
            coords[0] = x2;
            coords[1] = i;
            outline.add(coords);
        }
        for(int i=x2; i>x1; i--){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = y1;
            outline.add(coords);
        }
        return outline;
    }


    public void setTrace(int x, int y, int value){
       tiles[x][y].placeTrace();
    }

    private void updateExplored(){
        for (Agent agent : teamGuards) {
            ArrayList<int[]> exploredTiles = agent.getExplored();
            for (int[] coords : exploredTiles) {
                tiles[coords[0]][coords[1]].isExplored();
            }
        }
    }

    private void updateFlags(){
        for (Agent agent : teamGuards) {
            ArrayList<int[]> flags = agent.getFlags();
            for (int[] coords : flags) {
                tiles[coords[0]][coords[1]].placeFlag();
            }
        }
    }

    private void updateSeenTiles(){
        // reset all the tiles to be out of vision to the agent
        for(Tile[] tileRow : tiles){
            for(Tile tile : tileRow){
                tile.setAsNotVisible();
            }
        }
        //set the tiles that are currently seen by the agent as visible
        for (Agent agent : teamGuards) {
            ArrayList<int[]> visibleFields = agent.getVisibleFields();
            for (int[] coords : visibleFields) {
                tiles[coords[0]][coords[1]].setAsVisible();
            }
        }
    }

    public void updateTiles(){
        for(Agent agent : teamGuards){
            int[] coords = agent.getLastVisited();
            tiles[coords[0]][coords[1]].overlay(agent.getTiles()[coords[0]][coords[1]]);
        }
    }

    public boolean explored(){
        for(Tile[] tileRow:tiles){
            for(Tile tile:tileRow){
                if(!tile.isExplored()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isInMap(int x, int y){
        return x>=0 && x<this.mapWidth && y>=0 && y<this.mapHeight;
    }

}