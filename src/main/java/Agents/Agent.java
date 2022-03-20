package Agents;

import Controller.Variables;
import Controller.FileParser;
import Controller.Vector;
import java.lang.Math;

import java.util.ArrayList;


public class Agent  {
    //INSTANCES of class Agent
    //Agent Team information
    int teamCode; //1 if Intruder, 0 if Guard

    Variables variables = new Variables();
    private int mapPosX; //relative to map
    private int mapPosY;
    private int spawnX;
    private int spawnY;
    private double initialAngle;
    private double currentAngle;
    private String strategy; //1 - go to end, 2 - go along end, 3 - go to spawn, 4 - explore inside
    private String  GO_TO_END = "end";
    private String  GO_ALONG_WALL = "along_wall";
    private String  GO_TO_SPAWN = "spawn";
    private String  GO_ALONG_END = "along_end";
    private String AVOID_WALL = "avoid_wall";
    private String  EXPLORE = "explore";
    private int avoidance;
    private int agentSize;
    private int teamSize;
    private int visionRange = variables.getDistanceViewing();
    private double visionWidth;
    private Agent[] team;
    private ArrayList<int[]> exploredFields = new ArrayList<int[]>();
    private ArrayList<int[]> visibleFields = new ArrayList<int[]>();
    private Vector orientation;
    private Map map;
    private int mapMaxX;
    private int mapMaxY;

    // wall avoidance material
    private int WalltoAvoid;
    private int sideWall;
    private int flagCounter;
    private int stepCounter;
    private int [] lastposition = new int [2];

    //Agent Geographical Informations
    int[] spawning = new int [4]; // starting zone of the team
    int agentPositionX; //relative to spawn
    int agentPositionY; //as above

     /* It is interesting for each agent to contain a copy of the map
     * but  when updating the map this also means updating each of the agents memory
      */
    int[][] agentGoal;//current Goal for its A*
    ArrayList <int[]> AgentTrace = new ArrayList<>(int[variables.//Agent Actions&special features //Agent Actions&special features  ]);

    //Agent Actions
    Move agentMove; // update for agent itself and the Map
     int direction; // we try to split the 360 in a smart way

    //Agent Range features
    double getHearing; // ? for PHASE 2

    //GETTER AND SETTERS ASSOCIATED TO THE ABOVE INSTANCE
        public Map getAgentMap(){return map;}
        public int[] getAgentSpawning(){return spawning;}
        public void setAgentPositionX(int agentPositionX){this.mapPosX = agentPositionX;}
        public void setAgentPositionY(int agentPositionY){this.mapPosY = agentPositionY;}
        public int getAgentPositionX(){return this.mapPosX;}
        public int getAgentPositionY(){return this.mapPosY;}
        public int[][] getAgentGoal(){return this.agentGoal;}
        public double getInitialAngle(){
            return initialAngle;
        }
        public void setInitialAngle(double angle ){this.initialAngle = angle; }
        //Storing the agent past locations on the map
        public ArrayList<int[]> getAgentTrace(){return AgentTrace;}
        public int [] getLastVisited(){
            return AgentTrace.get(AgentTrace.size()-1);
        }
        //last position accessors
        public void setLastPosition(int x, int y ){this.lastPosition[0]= x; this.lastposition[1]= y;}
        public int [] getLastPosition(){return this.lastPosition;}
        // initial orientation of the Wall to Avoid
        public void setWalltoAvoid(int wall){this.WalltoAvoid = wall;}
        public int getWalltoAvoid(){return this.WalltoAvoid;}
        public void resetWalltoAvoid(){ this.WalltoAvoid = 0;}

        //side orientation of the wall to Avoid
        public void setSideWall(int wall){this.sideWall = wall;}
        public int getSideWall(){return this.sideWall;}
        public void resetSideWall(){ this.sideWall = 0;}

        // the Agent tracks the number of flags left behind
        public void resetFlagCounter(){this.flagCounter =0;}
        public void increaseFlagCounter(){ this.flagCounter++;}
        public int getFlagCounter(){return this.flagCounter;}

        //the Agent tracks the number of steps(move&turns) performed
        public void resetStepsCounter(){this.stepCounter =0;}
        public void increaseStepsCounter(){this.stepCounter++;}
        public void decreaseStepsCounter(){this.stepCounter--;}
        public int getStepsCounter(){return this.stepCounter;}


    /* METHOD(1): Agent Object CONSTRUCTOR
     *   constructor
     *   create an agent belonging to a specific team
     * */
    public Agent(int team){
        this.visionWidth = java.lang.Math.toRadians(15);
        this.teamCode = team;
        variables =  FileParser.readFile("./resources/testmap.txt");
        //team = new Agent[variables.getNumberOfGuards()];
        ArrayList<Integer> spawnCoords = variables.getSpawnAreaGuards().getCoords();
        if(team == 0){
            this.teamSize = variables.getNumberOfGuards();
        }
        else if (team == 1){
            this.teamSize = variables.getNumberOfIntruders();
        }
        this.agentPositionX = 0; //whenever agent moves, we have to update this
        this.agentPositionY = 0;
        this.visionRange = variables.getDistanceViewing();
        this.orientation = new Vector(this.agentPositionX,this.agentPositionY,this.initialAngle,this.visionRange);
        this.mapMaxX = variables.getWidth();
        this.mapMaxY = variables.getHeight();
    }

    public void setSpawnCoords(int[] coords){
        this.spawnX=coords[0];
        this.spawnY=coords[1];
    }


    private boolean inMap(int x, int y){
        return x>=0 && x<this.mapMaxX && y>=0 && y<this.mapMaxY;
    }

    private ArrayList<int[]> currentlyVisibleFields() {
        return this.visibleFields;
    }

    private int[] lastSeenField(){
        return this.visibleFields.get(this.visibleFields.size()-1);
    }




    private void setVision(){
        ArrayList<int[]> fields = new ArrayList<int[]>();

        int a1=0,a2=0,b1=0,b2=0;
        double conditionAngle = this.orientation.getAngle() - Math.toRadians(22.5);
        if(conditionAngle>=Math.toRadians(360-45) && conditionAngle<Math.toRadians(45)){
            a1 = 0; a2=0;
            b1 = 1; b2 = -1;
        }
        else if(conditionAngle>=Math.toRadians(45) && conditionAngle<Math.toRadians(90)){
            a1 = 1; a2 = 0;
            b1 = 0; b2 = 1;
        }
        else if(conditionAngle>=Math.toRadians(90) && conditionAngle<Math.toRadians(90+45)){
            a1=1; a2=-1;
            b1=0;b2=0;
        }
        else if(conditionAngle>=Math.toRadians(90+45) && conditionAngle<Math.toRadians(180)){
            a1=0;a2=-1;
            b1=1;b2=0;
        }
        else if(conditionAngle>=Math.toRadians(180) && conditionAngle<Math.toRadians(180+45)){
            a1=0;a2=0;
            b1=1;b2=-1;
        }
        else if(conditionAngle>=Math.toRadians(180+45) && conditionAngle<Math.toRadians(270)){
            a1=-1;a2=0;
            b1=0;b2=-1;
        }
        else if(conditionAngle>=Math.toRadians(270) && conditionAngle<Math.toRadians(270+45)){
            a1 = -1; a2=1;
            b1=0;b2=0;
        }
        else if(conditionAngle>=Math.toRadians(270+45) && conditionAngle<Math.toRadians(360)){
            a1=0;a2=1;
            b1=-1;b2=0;
        }
        int[] coords = new int[2];
        coords[0] = this.mapPosX+a1;
        coords[1] = this.mapPosY+b1;
        if(inMap(coords[0],coords[1])) {
            fields.add(coords);
            if (!exploredFields.contains(coords)) {
                exploredFields.add(coords);
            }
        }
        coords[0]=this.mapPosX+a2;
        coords[1]=this.mapPosY+b2;
        if(inMap(coords[0],coords[1])) {
            fields.add(coords);

            if (!exploredFields.contains(coords)) {
                exploredFields.add(coords);
            }
        }

        for(int i =0; i<=this.visionRange; i++){
            double ratio = Math.abs(this.orientation.getY2()/this.orientation.getX2());
            int x = i;
            int y = (int) (ratio*x);
            int[] coords = new int[2];
            coords = convertToMap(x,y);
            fields.add(coords);
            if(!exploredFields.contains(coords)){
                exploredFields.add(coords);
            }
            if(map.getTile(x,y).hasWall() || !inMap(x,y)){
                //this.orientation.setLength(i);
                break;
            }
        }

        this.visibleFields = fields;
    }


    private int[] convertToMap(int x, int y){
        /**
         * takes relative x,y from agent's POV
         * */
        int[] coords = new int[2];
        coords[0]=this.agentPositionX + this.spawnX;
        coords[1]=this.agentPositionY + this.spawnY;
        return coords;
    }

    private int[] convertToAgent(int xMap, int yMap){
        /**
         * takes map's x,y and converts to relative agent coords
         * */
        int[] coords = new int[2];
        coords[0]=xMap - this.spawnX;
        coords[1]=yMap - this.spawnY;
        return coords;
    }

    /*
    * HELPER METHOD to create a VECTOR END POINT
    *  INPUT: an angle and a distance
    * OUTPUT: the end coordonate of the Route vector
    * */
    public int[] CoordsCreator(double angle , int distance ){
        int[]coords = new int[2];
        coords[0] = (int) (Math.cos(angle) * distance);
        coords[1]  = (int) Math.sin(angle) * distance;
        return coords;
    }
/*
* Wall avoidance control baord
*/
    public int[] wall(){ // to be called from Goal
        if(End_Avoidance() == false)
          return moveCase();

        else {
          resetAvoidance();
  //        dropFlag();//LAST FLAG
          int[] finalPos = new int[2];
          finalPos[0] = getAgentPositionX();
          finalPos[1] = getAgentPositionY();
          return finalPos;
        }
    }


    /*METHOD (2) : IS_WALL
     * if the agent has reached a goal this identifies which type
     **/
    public int isWall(){
        //somewhere in the surrounding
        Tile[][] Copy = getAgentMap().getTiles();
        if(Copy[agentPositionX][agentPositionY+1].hasWall() == true){
            return 1;  //NORTH CASE
        }

        else if(Copy[agentPositionX][agentPositionY-1].hasWall() == true){
            return 2;   //SOUTH CASE
        }

        else if(Copy[getAgentPositionX()-1][getAgentPositionY()].hasWall() == true){
            return 3;   //EAST CASE
        }

        else if(Copy[getAgentPositionX()+1][getAgentPositionY()].hasWall() == true){//check for outoff bound errors
            return 4;   //WEST CASE
        }
        else
            return 0;
    }

    public int pastWall(){
        //somewhere in the surrounding
        Tile[][] Copy = getAgentMap().getTiles();
        if(Copy[getLastPosition()[0]][getLastPosition()[1]+1].hasWall() == true){
            return 1;  //NORTH CASE
        }

        else if(Copy[getLastPosition()[0]][getLastPosition()[1]-1].hasWall() == true){
            return 2;   //SOUTH CASE
        }

        else if(Copy[getLastPosition()[0]-1][getLastPosition()[1]].hasWall() == true){
            return 3;   //EAST CASE
        }

        else if(Copy[getLastPosition()[0]+1][getLastPosition()[1]].hasWall() == true){//check for outoff bound errors
            return 4;   //WEST CASE
        }
        else
            return 0;
    }

    /* METHOD():  END AVOIDANCE METHOD
     * Detects when the agent has successfully crossed the wall
     * Thats when he transition from having the wall to the North of his position
     *    to having the !!same wall!! at his South
     *    the current way might fail in narrow corridors...
     */
    public boolean End_Avoidance(){
      /*  ENDING CONDITIONS
      *terminate back to original vertical/horizontal coordonates
      */
        if(getFlagCounter() > 0 && getStepsCounter() == 0 ){
          if(getWalltoAvoid() == 1 && wall_South() == true)
            return true;
          else if(getWalltoAvoid() == 2 && wall_North() == true)
          return true;
        else if(getWalltoAvoid() == 3 && wall_East() == true)
          return true;
        else if(getWalltoAvoid() == 4 && wall_West() == true)
          return true;
        else if(getStepsCounter() > 200){// in case the Agent got stuck
          System.out.println("security exit " + getStepsCounter());
          return true;
        }
      }
      return false; //otherwise keep avoidig the wall
    }// end of End_Avoidance

    /*HELPER METHOD: start Avoidance
    *  set the parameters for the wall avoidance
    */
    public void startAvoidance(){
        //memorize the type of challenge
        int wallType = isWall();
        if(wallType =! 0){
          /*
          * TO DO: put a flag at getAgentPositionX();
          */
          setWalltoAvoid(wallType);
          dropFlag(); //oNLY UNIVERSAL FLAG
          increaseFlagCounter();
          System.out.println("flagcounter at " + getFlagCounter());
        }
      System.out.println("No need to set start: already or no wall");
    }//end startAvoidance

    /*HELPER METHOD: reset Avoidance
    *  Make sure to initialize on null or zero at Agent Creation
    * Reset every parameters used by wall avoidance
    */
    public void resetAvoidance(){
      resetFlagCounter();
      resetStepsCounter();
      resetWalltoAvoid();
      resetSideWall();
    }

public void mesureSteps(){
  //only increase step counter in first branch
  if(isWall(lastPosition) == getWalltoAvoid()){
    increaseStepsCounter();
  }
}
    /* METHOD (3): WALL AVOIDANCE METHOD
     * avoid by the right until :
     *     the position is free
     *   you are back on your route
     */
    public int[] wallAvoidance(){
      if(getFlagCounter() == 0){
        startAvoidance();//initialize the parameters
      }

public int [] moveCase(){

    int [] miniGoal = new int [2];// contains x, y positions of next move

            if(isMapLimit() == false){
              if(cases() == 1  || getSideWall() == 1 ){// go left
                miniGoal[0] = getAgentPositionX()+1;
                miniGoal[1] = getAgentPositionY();
              }
            else if(cases() == 2  || getSideWall() == 2){// go right
                miniGoal[0] = getAgentPositionX()-1;
                miniGoal[1] = getAgentPositionY();
              }
            else if(cases() == 3 || getSideWall() == 3){//go down
                miniGoal[0] = getAgentPositionX();
                miniGoal[1] = getAgentPositionY()-1;
              }
          else if(cases() == 4 || getSideWall() == 4){//go up
                miniGoal[0] = getAgentPositionX();
                miniGoal[1] = getAgentPositionY()+1;
              }
            else if(cases() == 5){//same position
                if(flagcounter == 0 /*first FLAG*/ || flagcounter == 1 /*second FLAG*/  ){
                  setSideWall(switchWall());
                }
                    //go turn to the LEFT (always)
                    turn(Math.toRadians(90));// internaly updates the position of the agent
  //                  dropFlag();// FIRST FLAG
                    increaseFlagCounter();
                    miniGoal[0] = getAgentPositionX();
                    miniGoal[1] = getAgentPositionY();
                }
                if(getflagCounter() == 0){
                  mesureSteps();
                }
                else if(getFlagCounter() == 2){
                  decreaseStepsCounter();
                  System.out.print("remaining steps" + getStepsCounter());
                }
                setLastPosition(miniGoal[0],miniGoal[1]);
                return miniGoal;
        }//end of isMapLimit
    }//end of WALL AVOIDANCE METHOD

/*
* METHOD cases: identify the wall
*/
    public int cases(){
      int case = 0;
      //CASE (1): go right , increase abscisse by one whenever
      if(wall_North() == true && getFlagCounter() == 0){
        case = 1;
      }
      else if (wall_South()== true && getFlagCounter() == 2){
        case = 1;
      }
      else if(wall_West()== true && getFlagCounter() == 1){
        case = 1;
      }
      //CASE (2): go left
      else if(wall_North() == true && getFlagCounter() == 2){
        case = 2;
      }
      else if (wall_South() == true && getFlagCounter() == 0){
        case = 2;
      }
      else if(wall_East() == true && getFlagCounter() == 1){
        case = 2;
      }
      //CASE (3): go down
      else if(wall_East() == true && getFlagCounter() == 0){
        case = 3;
      }
      else if (wall_West() == true && getFlagCounter() == 2){
        case = 3;
      }
      else if(wall_South() == true && getFlagCounter() == 1){
        case = 3;
      }
      //CASE (4): go up
      else if(wall_West() == true && getFlagCounter() == 0){
        case = 4;
      }
      else if (wall_East() == true && getFlagCounter() == 2){
        case = 4;
      }
      else if(wall_North() == true && getFlagCounter() == 1){
        case = 4;
      }
      else if(isWall(getLastPosition()) == getWalltoAvoid() && isWall() == 0){
        /*
        * Turn is required whenever we go out of the wall area
        * Stick vision concept: the tile on the right of the agent is free
        * Tif vision, call the latest stored wall avoidance
        */
        case = 5;
      }
      else if(isWall(getLastPosition()) == getWalltoAvoid() && isWall() == nextWall(getWalltoAvoid())){
        /* DENSE MASE LONGER AVOIDANCE
        * when another wall comes in our way it might be imposible to reach the other side
        * in a very dense maze for exemple
        * Option 1 : we interrupt the avoidance procedure of the initial wall
        * to start a new avoidance procedure on the second wall
        * Option 2: we consider this obstacle wall as another side wall,
        * until we reach the map limit AND  everytime such side wall as same orientation as the initial wall avoidance we keep increasing the getStepsCounter
        *  then only we start a global phase 3 by going anti-initial wall and decreasing the step counter in the anti_initial wall direction
        * this applies until counter = zero or we reach a teamate trace ,
        (?)we follow trace to map limit
        (?) then we spawn back !
        * improvement: update the step tracking
        */
        case = 6;
      }
      else if (){
        /*DEAD_END ; requires to turn 180° to go back to previous steps: call switch twice */
        case = 7;
      }
      return case;
    }

public int switchWall(){
  if(isWall(getLastPosition()) == 1){//north left turn is west
    return 4;
  }
  else if(isWall(getLastPosition()) == 2){//south  left turn is east
    return 3;
  }
  else if(isWall(getLastPosition()) == 3){//west  left turn is south
    return 2;
  }
  else if(isWall(getLastPosition()) == 4){//east  left turn is north
    return 1;
  }
}
  /*
  * A group of 4 method that identify the wall's position
  * TO DO: !Check if using the stick would be more consistent
  */
        public boolean wall_North(){
            Tile[][] Copy = getAgentMap().getTiles();
            if(Copy[getAgentPositionX()][getAgentPositionY()+1].hasWall() == true){
                return true;
            }
            else
                return false;
        }
        public boolean wall_South(){
            Tile[][] Copy = getAgentMap().getTiles();
            if(Copy[getAgentPositionX()][getAgentPositionY()-1].hasWall() == true){
                return true;
            }
            else
                return false;
        }
        public boolean wall_East(){
            Tile[][] Copy = getAgentMap().getTiles();
            if(Copy[getAgentPositionX()+1][getAgentPositionY()].hasWall() == true){
                return true;
            }
            else
                return false;
        }
        public boolean wall_West(){
            Tile[][] Copy = getAgentMap().getTiles();
            if(Copy[getAgentPositionX()-1][getAgentPositionY()].hasWall() == true){//check for outoff bound errors
                return true;
            }
            else
                return false;
        }

    /*METHOD () AGENT STRATEGY
    * CONTENT: each agent gets a specific exploration strategy to cover the map heavenly
    *  this exploration can be subdivised into 3 main routes:
    *       1/ from spawn Zone to map limit
    *       2/ from map limit to the nearest trace
    *       3/ finally back to spawn Zone
    *
    *       On the way it is possible to encounter obstacles such as walls, other agents trace or téléportation doors
    *          We created subroutines for each of these eventualities
    * */

    public void setStrategy(){
        if(shouldGoToSpawnAngle()){
            this.strategy = GO_TO_SPAWN;
        }
    }

    public int[] AgentStrategy(){
        // STEP 0 : set initial route
        int visionRange = variables.getDistanceViewing();
        int GoalA = new int [2];
        GoalA = CoordsCreator(initialAngle, visionRange);

    // STAGE 1: FROM SPAWNING RELATIVELY TOWARDS MAP LIMIT
        while(isEnd() == 0){
            if(isWall() == 1){
                //CAll wall avoidance routine

            }
              /*CALL  AStar with INPUT @GoalA
              * needs to be tested
                */
        }// The Map Limit has been reached successfully

    public int[] goal(){
        updateStrategy();
        switch(this.strategy){
            case "end":
                return goToEndOfMapCoords();
                break;
            case "along_end":
                return  exploreEdgeCoords();
                break;
            case "spawn":
                return goToSpawnCoords();
                break;
            case "along_wall":
                break;
            case "avoid_wall":
                break;
            case "explore":
                break;
        }
    }

    public void updateStrategy(){
        if(strategy==GO_TO_END && reachedEnd()){
            this.strategy = GO_ALONG_END;
        }
        else if(strategy==GO_TO_END && reachedWallFlag()){
            this.strategy = GO_ALONG_WALL;
        }
        if(strategy==GO_ALONG_END && shouldGoToSpawnAngle()){
            this.strategy = GO_TO_SPAWN;
        }
    }


    private boolean reachedWallFlag(){
        int[] coords = nextCoord();
        if(inMap(coords[0],coords[1])){
            return map.getTile(coords[0],coords[1]).hasFlag();
        }
        return false;
    }

    private int[] nextCoord(){
        Vector unitVec = this.orientation.unitCopy();
        int[] coords = unitVec.getEndCoords();
        return convertToMap(coords[0],coords[1]);
    }

    private boolean reachedEnd(){
        int[] coords = nextCoord();
        return !inMap(coords[0],coords[1]);
    }

    private int[] goToEndOfMapCoords(){
        return this.orientation.getEndCoords(); //unless wall evasion, to correct!
    }

    private int[] exploreEdgeCoords(){

    }

    private int[] goToSpawnCoords(){
        this.orientation = new Vector(this.agentPositionX,this.agentPositionY,0,0);
        this.orientation.setLength(this.visionRange);
        setVision();
        return this.visibleFields.get(this.visibleFields.size() - 1);
    }

    private boolean shouldGoToSpawnAngle(){
        int k = (int) this.initialAngle/this.teamSize;
        double alpha = initialAngle;
        double k_alpha = (k-1)*alpha;
        double ratioTan = 0.0;
        if(k_alpha == 0 || Math.abs(k_alpha) == Math.PI/2 || Math.abs(k_alpha) == 3*Math.PI/2){
            return this.agentPositionY == 0;
        }
        else if (k == 0){
            k_alpha = (this.teamSize - 1) * alpha;
        }

        ratioTan = (double) this.agentPositionY/this.agentPositionX;


        return Math.tan(k_alpha) == ratioTan;
    }

    private boolean shouldGoToSpawnTrace(){

    }

    public ArrayList<int[]> getExplored(){
        return this.exploredFields;
    }


}
