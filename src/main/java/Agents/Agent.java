package Agents;

import Controller.Variables;
import Controller.Vector;
import Path.Move;
import Path.Position;

import java.lang.Math;

import java.util.ArrayList;

/*
goal: last tile in vision in the alpha direction is the goal
when you reach goal
 */


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
    private String strategy;
    private String  GO_TO_END = "end";
    private String  GO_TO_SPAWN = "spawn";
    private String  GO_ALONG_END = "along_end";
    private String AVOID_WALL = "avoid_wall";
    private String  EXPLORE = "explore";
    private boolean avoidWall;
    private int avoidance;
    private int agentSize;
    private int teamSize;
    private int visionRange = variables.getDistanceViewing();
    private double visionWidth;
    private Agent[] team;
    private ArrayList<int[]> exploredFields = new ArrayList<>();
    private ArrayList<int[]> visibleFields = new ArrayList<>(); //what the agent sees
    /**/
    private Vector orientation;
    private Map map; //whole map
    private int mapMaxX;
    private int mapMaxY;
    private int[] endOfVisionRange;
    private int[] lastVisited;
    private ArrayList<int[]> path;
    private int iterator;

    //Agent Geographical Informations

    int[] spawning = new int [4]; // starting zone of the team
    int agentPositionX; //relative to spawn
    int agentPositionY; //as above

     /* It is interesting for each agent to contain a copy of the map
     * but  when updating the map this also means updating each of the agents memory
      */
    int[][] agentGoal;//current Goal for its A*
    ArrayList<int[]> agentTrace = new ArrayList<>();
    ArrayList<int[]> flags = new ArrayList<>();
    //Agent Actions
    Move agentMove; // update for agent itself and the Map
     //int direction; // we try to split the 360 in a smart way

    //Agent Range features
    double getHearing; // ? for PHASE 2
    private int[] visionLeft;
    private int[] visionRight;
    private int[] aGoal;

    private NeoExploAlgoPerAgent exploAlgoMachine;

    private int WalltoAvoid;
    private int sideWall;
    private int flagCounter;
    private int stepCounter;
    private int turnCounter;
    private int[] lastPosition = new int [2];



    /* METHOD(1): Agent
     *   constructor
     *   create an agent belonging to a specific team
     * */
    public Agent(int team, Variables vars){
        this.visionWidth = java.lang.Math.toRadians(15);
        this.teamCode = team;
        this.variables = vars;// FileParser.readFile("./resources/testmap.txt");
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

        exploAlgoMachine = new NeoExploAlgoPerAgent();
        path = new ArrayList<>();
        iterator = 0;
    }
    /**
     * DOCUMENTATION FOR ZOFIA'S PART
     *
     * 1 : move
     * 2 : pathFinished
     * 3 : getPathFromAstar
     * 4 : getNextMove
     * 5 : setInitialCoords
     * 6 : turn
     * 7 : dropFlag
     * 8 : isInMap
     * 9 : currentlyVisibleFields
     * 10 : lastSeenField
     * 11 : setVision
     * 12 : convertToMap
     * 13 : convertToAgent
     * 14 : goal
     * 15 : updateStrategy
     * 16 : shouldGoToSpawnAngle
     * 17 : reachedWallFlag()
     * 18 : isAtSpawn
     * 19 : reachedEnd
     * 20 : reachedWall
     * 21 : nextCoord
     * 22 : goToEndOfMapCoords
     * 23 : exploreEdgeCoords
     * 24 : gotoSpawnCorrds
     * 25 : explore
     * */

    public void move(){
        int[] coords = new int[2];
        coords[0] = this.agentPositionX;
        coords[1] = this.agentPositionY;
        setLastVisited(coords);
        if(pathFinished()){
            aGoal = goal();
            path = getPathFromAstar();
            this.iterator = 0;
        }
        else{
            coords = getNextMove();
        }

        //ArrayList<Integer> path = getPathFromAstar();
        //nextMove = path(0);
        //path(0).delete;
        /*
        * Some methods that change the agent position, to do in the connection
        * */
        int[] mapCoords = convertToMap(this.agentPositionX,this.agentPositionY);
        this.mapPosX = mapCoords[0];
        this.mapPosY = mapCoords[1];
    }

    private boolean pathFinished(){
        return iterator == path.size();
    }

    private ArrayList<int[]> getPathFromAstar(){
        Position startPosition = new Position(this.mapPosX,this.mapPosY);
        Position goal = new Position(this.aGoal[0],this.aGoal[1]);
        return agentMove.getPath(startPosition, goal, this.map);
    }

    private int[] getNextMove(){
        iterator++;
        return path.get(iterator-1);
    }

    public void setInitialCoords(int[] coords){
        this.spawnX = coords[0];
        this.spawnY = coords[1];
        this.mapPosX = spawnX;
        this.mapPosY = spawnY;
    }

    public void turn(double alpha){
        /** angle expressed in radians counter clockwise*/
        this.orientation.turn(alpha);
        setVision();
    }

    private void dropFlag(){
        this.map.getTile(this.mapPosX,this.mapPosY).placeFlag();
        int[] coords = new int[2];
        coords[0] = this.mapPosX;
        coords[1] = this.mapPosY;
        this.flags.add(coords);
    }

    private boolean isInMap(int x, int y){
        return x>=0 && x<this.mapMaxX && y>=0 && y<this.mapMaxY;
    }

    private boolean isInMap(int[] c){
        return c[0]>=0 && c[0]<this.mapMaxX && c[1]>=0 && c[1]<this.mapMaxY;
    }

    private ArrayList<int[]> currentlyVisibleFields() {
        return this.visibleFields;
    }

    private int[] lastSeenField(){
        return this.visibleFields.get(this.visibleFields.size()-1);
    }
    /*
    * vision is set to be a line of tiles, in the direction of the orientation vector and the length of vision range
    * this method sets the visibleFields parameter. (used for exploration updating)
    * */

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
        this.visionLeft = coords;
        if(isInMap(coords[0],coords[1])) {
            fields.add(coords);
            if (!exploredFields.contains(coords)) {
                exploredFields.add(coords);
            }
        }
        coords[0]=this.mapPosX+a2;
        coords[1]=this.mapPosY+b2;
        this.visionRight = coords;
        if(isInMap(coords[0],coords[1])) {
            fields.add(coords);
            if (!exploredFields.contains(coords)) {
                exploredFields.add(coords);
            }
        }

        for(int i =0; i<=this.visionRange; i++){
            double ratio = Math.abs(this.orientation.getY2()/this.orientation.getX2());
            int x = i;
            int y = (int) (ratio*x);
            coords = convertToMap(x,y);
            fields.add(coords);
            if(!exploredFields.contains(coords)){
                exploredFields.add(coords);
            }
            if(map.getTile(x,y).hasWall() || !isInMap(x,y)){
                //this.orientation.setLength(i);
                break;
            }
        }

        this.endOfVisionRange = fields.get(fields.size()-1);
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


    /** GETTERS **/

    public Map getAgentMap(){return map;}
    public int[] getAgentSpawning(){return spawning;}
    public int getAgentPositionX(){return this.mapPosX;}
    public int getAgentPositionY(){return this.mapPosY;}
    public int[][] getAgentGoal(){return this.agentGoal;}
    public double getInitialAngle(){
        return initialAngle;
    }
    public Tile[][] getTiles(){
        return this.map.getTiles();
    }

    public ArrayList<int[]> getExplored(){
        return this.exploredFields;
    }
    public int[] getLastVisited(){
        return this.lastVisited;
    }
    public ArrayList<int[]> getAgentTrace(){
        return agentTrace;
    }
    public ArrayList<int[]> getFlags(){
        return this.flags;
    }

    public ArrayList<int[]> getVisibleFields(){
        return  this.visibleFields;
    }

    public int[] getCurrentCoordinates() {
        int[] coords = new int[2];
        coords[0] = this.mapPosX;
        coords[1] = this.mapPosY;
        return coords;
    }

    public ArrayList<int[]> getPath(){
        return this.path;
    }

    /** SETTERS **/
    public void setCurrentAngle(double alpha){
        this.orientation.setAngle(alpha);
        setVision();
    }
    public void setInitialAngle(double angle ){this.initialAngle = angle; }
    public void setAgentPositionX(int agentPositionX){this.mapPosX = agentPositionX;}
    public void setAgentPositionY(int agentPositionY){this.mapPosY = agentPositionY;}
    private void setLastVisited(int[] coords){this.lastVisited = coords;}

    public void setLastPosition(int x, int y ){this.lastPosition[0]= x; this.lastPosition[1]= y;}
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

    //the Agent tracks the number of turn(move&turns) performed
    public void resetTurnCounter(){this.turnCounter =0;}
    public void increaseTurnCounter(){this.turnCounter++;}
    public void decreaseTurnCounter(){this.turnCounter--;}
    public int getTurnCounter(){return this.turnCounter;}




    /*
    * HELPER METHOD to create a VECTOR END POINT
    *  INPUT: an angle and a distance
    * OUTPUT: the end coordonate of the Route vector
    * */







    /*METHOD (4) AGENT STRATEGY
    * CONTENT: each agent gets a specific exploration strategy to cover the map heavenly
    *  this exploration can be subdivised into 3 main routes:
    *       1/ from spawn Zone to map limit
    *       2/ from map limit to the nearest trace
    *       3/ finally back to spawn Zone
    *
    *       On the way it is possible to encounter obstacles such as walls, other agents trace or téléportation doors
    *          We created subroutines for each of these eventualities
    * */




        //WALL AVOIDANCE METHOD
    // until front position contains wall, avoid by the right
    //until side



    public int[] goal(){
        /**
         * returns an array of 2 coordinates that define the goal based ont the current strategy
         * */
        updateStrategy();
        switch(this.strategy){
            case "end":
                return goToEndOfMapCoords();
            case "along_end":
                return  exploreEdgeCoords();
            case "spawn":
                return goToSpawnCoords();
            case "avoid_wall":
                //return wall();
                break;
            case "explore":
                return explore();
        }
        return getCurrentCoordinates();
    }




    private void updateStrategy(){
        /**
         * updates the strategy of the agent
         * */

        if(reachedWall()){
            this.avoidWall = true;
        }

        if(strategy.equals(GO_TO_END) && reachedEnd()){
            this.strategy = GO_ALONG_END;
        }
        else if(this.avoidWall && reachedWallFlag()){
            this.strategy = GO_TO_SPAWN;
        }
        if(strategy.equals(GO_ALONG_END) && shouldGoToSpawnAngle()){
            this.strategy = GO_TO_SPAWN;
        }
        if(strategy.equals(GO_TO_SPAWN) && isAtSpawn()){
            this.strategy = EXPLORE;
        }
    }

    /*
    * shouldGoToSpawnAngle uses trig functions to determine
    * whether the agent has covered their designated angle
    * */

    private boolean shouldGoToSpawnAngle(){
        /**
         * returns true if agent should go back to spawn (ANGLE STRATEGY)
         * */
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
        int[] coords = nextCoord();
        boolean b = map.getTile(coords[0], coords[1]).hasTrace();
        return b;
    }

    private boolean reachedWallFlag(){
        int[] coords = nextCoord();
        if(isInMap(coords[0],coords[1])){
            return map.getTile(coords[0],coords[1]).hasFlag();
        }
        return false;
    }

    private boolean isAtSpawn(){
        return agentPositionX == 0 && agentPositionY == 0;
    }

    private boolean reachedEnd(){
        int[] coords = nextCoord();
        return !isInMap(coords[0],coords[1]);
    }

    private boolean reachedWall(){
        int[] coords = nextCoord();
        return this.map.getTile(coords[0],coords[1]).hasWall();
    }

    private int[] nextCoord(){
        /**
         * returns the coordinates of the first tile
         * */
        Vector unitVec = this.orientation.unitCopy();
        int[] coords = unitVec.getEndCoords();
        return convertToMap(coords[0],coords[1]);
    }


    /** STRATEGY IMPLEMENTATION METHODS*/

    private int[] goToEndOfMapCoords(){
        return this.endOfVisionRange; //unless wall evasion, to correct!
    }

    /*
    * for exploreEdgeCoords(): I divide the map into 4 parts relative to spawn point.
    * If agent is in the top right:
    * check if he can move (if next field is on map)
    * turn him so that he is oriented to 0 degrees (East)
    * If can move then -> just go along the wall
    * If cant move still -> orient agent to 270 degrees (South)
    * */

    private int[] exploreEdgeCoords(){
        /**returns the next set of coordinates for the go along end of map strategy*/
        double alpha = 0.0;
        int[] coords = new int[2];
        if(reachedEnd()){
            if(this.agentPositionX>=0 && this.agentPositionY>=0){
                if(this.currentAngle == Math.toRadians(0)){
                    setCurrentAngle(Math.toRadians(270));
                }
                else {
                    this.currentAngle = Math.toRadians(0);
                }
            }
            else if(this.agentPositionX<0 && this.agentPositionY>0){
                if(this.currentAngle == Math.toRadians(90)){
                    setCurrentAngle(Math.toRadians(0));
                }
                else{
                    setCurrentAngle(Math.toRadians(90));
                }
            }
            else if(this.agentPositionX<0 && this.agentPositionY<0){
                if(this.currentAngle == Math.toRadians(180)){
                    setCurrentAngle(Math.toRadians(90));
                }
                else{
                    setCurrentAngle(Math.toRadians(180));
                }
            }
            else if(this.agentPositionX > 0 && this.agentPositionY < 0){
                if(this.currentAngle == Math.toRadians(0)){
                    setCurrentAngle(Math.toRadians(90));
                }
                else{
                    setCurrentAngle(Math.toRadians(0));
                }
            }
            coords[0] = this.mapPosX;
            coords[1] = this.mapPosY;
            return coords;
        }
        else{
            return nextCoord();
        }
    }

    private int[] goToSpawnCoords(){
        this.orientation = new Vector(this.agentPositionX,this.agentPositionY,0,0);
        this.orientation.setLength(this.visionRange);
        setVision();
        return this.visibleFields.get(this.visibleFields.size() - 1);
    }

    private int[] explore(){
        return exploAlgoMachine.explore(this);
    }

    /*******************************************************************************
     *                               @TIPHANIE'S methods (documentation)
     * METHOD (1): Wall avoidance
     * METHOD (2): start Avoidance
     * METHOD(3):  End_Avoidance
     * METHOD(4): resetAvoidance
     * METHOD(5): mesureSteps
     * METHOD(6): moveCase
     * METHOD (7) : isWall
     * METHOD (8) : cases
     *
     * METHOD (9): wall_North
     * METHOD (10): wall_South
     * METHOD (11): wall_East
     * METHOD (12): wall_West
     * METHOD (13) : switchWallLeft
     * METHOD (14) : switchWallRight
     */



    /*******************************************************************************
     * METHOD (1): Wall avoidance: a control baord
     * TO DO: customize lines 360 and 361 into a boolean conditions to call the current method
     ********************************************************************************/
    public int[] wallAvoidance(){ // to be called from Goal
        System.out.println("(1) wall_Avoidance ");
        if(End_Avoidance() == false){
            if(getFlagCounter() == 0){
                startAvoidance();//initialize the parameters
            }
            return moveCase();
        }
        else {
            resetAvoidance();
            //        dropFlag();//LAST FLAG
            int[] finalPos = new int[2];
            finalPos[0] = getAgentPositionX();
            finalPos[1] = getAgentPositionY();
            return finalPos;
        }
    }

    /***************************************************************************
     * METHOD (2): start Avoidance
     *  set the parameters for the wall avoidance
     ****************************************************************************/
    public void startAvoidance(){
      System.out.println("(2) Start_Avoidance ");
        //memorize the type of challenge
        int wallType = isWall();
        if(wallType != 0){
            /*
             * TO DO: put a flag at getAgentPositionX();
             */
            setWalltoAvoid(wallType);
            dropFlag(); //oNLY UNIVERSAL FLAG
            increaseFlagCounter();
            System.out.println("flagcounter at " + getFlagCounter());
        }
        System.out.println("No need to set start: already or no wall ");
    }//end startAvoidance

    /* **************************************************************************
     * METHOD(3):  End_Avoidance
     *  a security because normally goal wont call it anymore !! DOUBLE  CHECK
     * Detects when the agent has successfully crossed the wall
     * Thats when he transition from having the wall to the North of his position
     *    to having the !!same wall!! at his South
     *    the current way might fail in narrow corridors...
     *  terminate back to original vertical/horizontal coordonates
     *  works under condition that the getWalltoAvoid() remains unchanged throughout the entire procedure
     ***************************************************************************/
    public boolean End_Avoidance(){
      System.out.println("(3 )End_Avoidance ");
        if(reachedEnd()){
            // dble security *
            return true;
        }
        else if(getFlagCounter() > 0 && getStepsCounter() == 0 ){
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

    /***************************************************************************
     * METHOD(4): resetAvoidance
     *  Make sure to initialize on null or zero at Agent Creation
     * Reset every parameters used by wall avoidance
     ***************************************************************************/
    public void resetAvoidance(){
        resetFlagCounter();
        resetStepsCounter();
        resetWalltoAvoid();
        resetSideWall();
    }

    /***************************************************************************
     * METHOD(5): mesureSteps
     * only increase step counter in first branch
     ****************************************************************************/
    public void mesureSteps(){
        if(wasWall() == getWalltoAvoid()){
            increaseStepsCounter();
        }
    }

    /*****************************************************************************
     * METHOD(6): moveCase
     * for a definite case associate the correct move to perform
     ******************************************************************************/
    public int[] moveCase(){
      System.out.println("(6) moveCase ");
        int [] miniGoal = new int [2];// contains x, y positions of next move
        int casE = cases();

        if(casE == 1  || getSideWall() == 1 ){// go left
            miniGoal[0] = getAgentPositionX()+1;
            miniGoal[1] = getAgentPositionY();
            System.out.println("North Go Left ");
        }
        else if(casE == 2  || getSideWall() == 2){// go right
            miniGoal[0] = getAgentPositionX()-1;
            miniGoal[1] = getAgentPositionY();
              System.out.println("South Go Right");
        }
        else if(casE == 3 || getSideWall() == 3){//go down
            miniGoal[0] = getAgentPositionX();
            miniGoal[1] = getAgentPositionY()-1;
        }
        else if(casE == 4 || getSideWall() == 4){//go up
            miniGoal[0] = getAgentPositionX();
            miniGoal[1] = getAgentPositionY()+1;
              System.out.println("West Go Up ");
        }
        //the #3 STAHL CASES : turn will be performed in different context
        else if(casE == 5 || casE == 6 || casE == 7){

            if(casE == 5 ){//when the space ahead is free: turn to the LEFT always
                setSideWall(switchWallLeft());//left wall considered
                turn(Math.toRadians(90));
                  System.out.println("free to turn ");
            }
            else if(casE == 6 || casE == 7 ){//when the space ahead is not free: turn to the LEFT always
                if(getTurnCounter() == 0 && casE == 6 ) {
                    turn(Math.toRadians(90)); //make it a RIGHT TURN !!!!!!!!!
                    //  setSideWall(switchWallRight()); //will the turn counter enter the above conditions ?
                    setSideWall(getWalltoAvoid());//so the next time it will enter the above position
                    increaseTurnCounter();
                }
                else if(getTurnCounter() == 0 && (casE == 6 ||casE == 7 ) ) {
                    turn(Math.toRadians(90)); //make it a RIGHT TURN !!!!!!!!!
                    increaseTurnCounter();
                    resetTurnCounter();
                      System.out.println("Front blocked turn right");
                }
                else if(getTurnCounter() == 1 && casE == 7){
                    turn(Math.toRadians(90)); //make it a RIGHT TURN !!!!!!!!!
                    setSideWall(switchWallRight()); //make sure the agent position is being updated at that stage !

                } //from here the procedure should execute base on classic cases 1 to 4

                else if(getTurnCounter() == 2 && casE == 7){
                    setSideWall(switchWallRight()); //make sure the agent position is being updated at that stage !
                    resetTurnCounter();
                }
            }
            miniGoal[0] = getAgentPositionX();
            miniGoal[1] = getAgentPositionY();
        }//end of #3 STAHL CASES

        /* Phase 1 and 2: the agent hasn't come across the obstacle
         * Current wall is parallel to the inital avoidance
         */
        if((getFlagCounter()<2) && (isWall() == getWalltoAvoid() )){
            mesureSteps();
        }
        else if(getFlagCounter() == 2){
            decreaseStepsCounter();
            System.out.println("remaining steps " + getStepsCounter());
        }
        setLastPosition(miniGoal[0],miniGoal[1]);
        return miniGoal;

    }//end of move Case



    /*****************************************************************************
     * METHOD (7) : isWall
     * if the agent has reached a goal this identifies which type
     ******************************************************************************/
    public int isWall(){
        System.out.println("(7) isWall ");
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

    public int wasWall(){
        System.out.println("(7 bis) wasWall ");
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
    /*****************************************************************************
     * METHOD (8) : cases
     * identify the wall orientation
     ******************************************************************************/
    public int cases(){
        System.out.println("(8) cases ");
        int casE = 0;
        //CASE (1): go right , increase abscisse by one whenever
        if(wall_North() == true && getFlagCounter() == 0){
            casE = 1;
        }
        else if (wall_South()== true && getFlagCounter() == 2){
            casE = 1;
        }
        else if(wall_West()== true && getFlagCounter() == 1){
            casE = 1;
        }
        //CASE (2): go left
        else if(wall_North() == true && getFlagCounter() == 2){
            casE = 2;
        }
        else if (wall_South() == true && getFlagCounter() == 0){
            casE = 2;
        }
        else if(wall_East() == true && getFlagCounter() == 1){
            casE = 2;
        }
        //CASE (3): go down
        else if(wall_East() == true && getFlagCounter() == 0){
            casE = 3;
        }
        else if (wall_West() == true && getFlagCounter() == 2){
            casE = 3;
        }
        else if(wall_South() == true && getFlagCounter() == 1){
            casE = 3;
        }
        //CASE (4): go up
        else if(wall_West() == true && getFlagCounter() == 0){
            casE = 4;
        }
        else if (wall_East() == true && getFlagCounter() == 2){
            casE = 4;
        }
        else if(wall_North() == true && getFlagCounter() == 1){
            casE = 4;
        }
        else if( wasWall() == getWalltoAvoid() && isWall() == 0){
            /*
             * Turn is required whenever we go out of the wall area
             * Stick vision concept: the tile on the right of the agent is free
             * Tif vision, call the latest stored wall avoidance
             */
            casE = 5;
        }//2 WALLS BLOCKING THE AGENT
        else if(wasWall() == getWalltoAvoid() && isWall() == switchWallLeft(/*getWalltoAvoid()*/)){
            /*LONGUER WAY: more obstacles on the way, keep circuling around  */
            casE = 6;
        }
        /* DEAD_END: 3 WALLS BLOCKING THE AGENT:
         * requires to turn 180° to go back to previous steps: call switch twice
         * NB 1: this method will overide the previous one
         * NB 2. detection is garantied by the radius of isWall()
         */
      else if(wasWall() == getWalltoAvoid()
                && isWall() == switchWallLeft(/*getWalltoAvoid()*/)
                && isWall() == switchWallRight(/*getWalltoAvoid()*/)){
            casE = 7;
        }
          System.out.println("identified case " + casE);
        return casE;
    }

    /*****************************************************************************
     * A group of 4 method that identify the wall's position
     * METHOD (9): wall_North
     * METHOD (10): wall_South
     * METHOD (11): wall_East
     * METHOD (12): wall_West
     * TO DO: !Check if using the stick would be more consistent
     *****************************************************************************/
    public boolean wall_North(){
        Tile[][] Copy = getAgentMap().getTiles();
        if(Copy[getAgentPositionX()][getAgentPositionY()+1].hasWall() == true){
            System.out.println("Wall is North ");
            return true;
        }
        else
            return false;
    }
    public boolean wall_South(){
        Tile[][] Copy = getAgentMap().getTiles();
        if(Copy[getAgentPositionX()][getAgentPositionY()-1].hasWall() == true){
            System.out.println("Wall is South ");
            return true;
        }
        else
            return false;
    }
    public boolean wall_East(){
        Tile[][] Copy = getAgentMap().getTiles();
        if(Copy[getAgentPositionX()+1][getAgentPositionY()].hasWall() == true){
            System.out.println("Wall is East ");
            return true;
        }
        else
            return false;
    }
    public boolean wall_West(){
        Tile[][] Copy = getAgentMap().getTiles();
        if(Copy[getAgentPositionX()-1][getAgentPositionY()].hasWall() == true){//check for outoff bound errors
          System.out.println("wall is West ");
            return true;
        }
        else
            return false;
    }

    /*****************************************************************************
     * METHOD (13) : switchWallLeft
     * updates sideWall reference when turning left
     ******************************************************************************/
    public int switchWallLeft(){
        System.out.println("(13) switchWallLeft");
        if(wasWall() == 1){//north left turn is west
            return 4;
        }
        else if(wasWall() == 2){//south  left turn is east
            return 3;
        }
        else if(wasWall() == 3){//west  left turn is south
            return 2;
        }
        else if(wasWall() == 4){//east  left turn is north
            return 1;
        }
        return 0;
    }
    /*****************************************************************************
     * METHOD (14) : switchWallRight
     * updates sideWall reference when turning right
     ******************************************************************************/
    public int switchWallRight(){
        System.out.println("(13) switchWallRight");
        //first turn : 90°
        if(getWalltoAvoid() == 1 && getTurnCounter() == 0){//north right turn is east
            return 3;
        }
        else if(getWalltoAvoid()  == 2 && getTurnCounter() == 0){//south  right turn is west
            return 4;
        }
        else if(getWalltoAvoid() == 3 && getTurnCounter() == 0){//west  right turn is north
            return 1;
        }
        else if(getWalltoAvoid() ==  4 && getTurnCounter() == 0){//east  right turn is south
            return 2;
        }
        //second turn : 180°
        else if(getWalltoAvoid() == 1 && getTurnCounter() == 1){//north right turn is east
            return 2;
        }
        else if(getWalltoAvoid()  == 2 && getTurnCounter() == 1){//south  right turn is west
            return 1;
        }
        else if(getWalltoAvoid() == 3 && getTurnCounter() == 1){//west  right turn is north
            return 4;
        }
        else if(getWalltoAvoid() ==  4 && getTurnCounter() == 1){//east  right turn is south
            return 3;
        }
        return 0;
    }
}
