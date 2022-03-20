package Agents;

import Controller.Variables;
import Controller.Vector;
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
    //Move agentMove; // update for agent itself and the Map
     //int direction; // we try to split the 360 in a smart way

    //Agent Range features
    double getHearing; // ? for PHASE 2
    private int[] visionLeft;
    private int[] visionRight;

    private NeoExploAlgoPerAgent exploAlgoMachine;


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
    }

    public void move(){
        int[] coords = new int[2];
        coords[0] = this.agentPositionX;
        coords[1] = this.agentPositionY;
        setLastVisited(coords);
        int[] agentGoal = goal();
        path = getPathFromAstar();
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

    public void setInitialCoords(int[] coords){
        this.spawnX=coords[0];
        this.spawnY=coords[1];
        this.mapPosX = spawnX;
        this.mapPosY = spawnY;
    }

    public void turn(double alpha){
        /** angle expressed in radians counter clockwise*/
        this.orientation.turn(alpha);
        setVision();
    }

    public void setCurrentAngle(double alpha){
        this.orientation.setAngle(alpha);
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

    /** SETTERS **/

    public void setInitialAngle(double angle ){this.initialAngle = angle; }
    public void setAgentPositionX(int agentPositionX){this.mapPosX = agentPositionX;}
    public void setAgentPositionY(int agentPositionY){this.mapPosY = agentPositionY;}
    private void setLastVisited(int[] coords){this.lastVisited = coords;}




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
}
