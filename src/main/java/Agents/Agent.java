package Agents;

import Controller.Variables;
import Controller.FileParser;
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
    private int relativePosX; //relative position to the start which is (0,0) the moment before you start avoiding the wall
    private int relativePosY;
    private double initialAngle;
    private double currentAngle;
    private int strategy;
    private int avoidance;
    private int agentSize;
    private int teamSize;
    private int visionRange = variables.getDistanceViewing();
    private double visionWidth;
    private Agent[] team;
    private ArrayList<int[]> exploredFields = new ArrayList<int[]>();
    private Vector orientation;

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


    /* METHOD(1): Agent
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

    }

    private ArrayList<int[]> visibleFields(){
        ArrayList<int[]> fields = new ArrayList<int[]>();
        for(int i =0; i<=this.visionRange; i++){
            double ratio = this.orientation.getY2()/this.orientation.getX2();
            int x = i;
            int y = (int) (ratio*x);
            int[] coords = new int[2];
            coords[0] = x;
            coords[1] = y;
            fields.add(coords);
            if(!exploredFields.contains(coords)){
                exploredFields.add(coords);
            }
        }
        double conditionAngle = this.orientation.getAngle() - Math.toRadians(22.5)
        if(conditionAngle>0.0 && conditionAngle<Math.toRadians(45)){

        }
        return fields;
    }

    public int[] getAgentSpawning(){return spawning;}
    public void setAgentPositionX(int agentPositionX){this.agentPositionX = agentPositionX;}
    public void setAgentPositionY(int agentPositionY){this.agentPositionY = agentPositionY;}
    public int getAgentPositionX(){return this.agentPositionX;}
    public int getAgentPositionY(){return this.agentPositionY;}
    public int[][] getAgentGoal(){return this.agentGoal;}
    public double getInitialAngle(){
        return initialAngle;
    }
    public void setInitialAngle(double angle ){this.initialAngle = angle; }

    public ArrayList<int[]> getAgentTrace(){
        return AgentTrace;
    }

    public int [] getLastVisited(){
        return AgentTrace.get(AgentTrace.size()-1);
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
// TO DO DEFINE CONE VISION
/*
    int visionRange = agentPositionX + variable.getDistanceViewing();
    int visionAngle ; // to experiment on between 1° to 90°
*/

    /*METHOD (1) : IS_END
    * check if the agent has reached the end of the map
    * BE CAREFUL two distinct reference;
    * the map size is euclidian from 0,0
    * whereas, the Agentb position is centered on spawning zone
    * prepair for 4 CASES
     **/
    public boolean isEnd(){
        int limitY = variables.getHeight();
        int limitX = variables.getWidth();
        int[] GoalA = new int[2];
        GoalA = CoordsCreator(initialAngle, visionRange );

        if( GoalA[0] >= limitX  || GoalA[1] >= limitY ){
            return true; //Agent is in vision range of the mapLimit
        }
            return false; //Agent is not in vision range of the mapLimit
    }
    /*METHOD (2) : IS_WALL
     * check if the agent has reached a wall
     **/

    /*METHOD (3) : AVOID_WALL
         * circles around the wall until the route is found again
         * that means until the Agent vision is perfctly aligned with the intialAngle (x, y+width)
         **/


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

    public void setStrategy(){
        if(shouldGoToSpawnAngle()){
            this.strategy = 3;
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


    /* STAGE 2: FROM MAP LIMIT TO TRACE LIMIT
     * 2 OPTION exist here:
     *  OPTION A/ the agent progress in a similar manner :
     *              so the Agent turns right
     *               then the Agent follows the map limit until he reaches a trace!
     *
     * OPTION B/ some Agents are much slower or much faster (because they encounter more troubles)
     *              in this case we ensure consistency and prevent the problems
     *              by early returning towards the spawn area
     */


    // STAGE 3: BACK TO SPAWNING
        /*
        *
        *
        * */

        }

        //WALL AVOIDANCE METHOD
    // until front position contains wall, avoid by the right
    //until side



    public int[] goal(){
        switch(this.strategy){
            case 1:
                return goToEndOfMapCoords();
                break;
            case 2:
                return  exploreEdgeCoords();
                break;
            case 3:
                return goToSpawnCoords();
            break;
        }
    }

    private int[] goToEndOfMapCoords(){
        return this.orientation.getEndCoords(); //unless wall evasion, to correct!
    }

    private int[] exploreEdgeCoords(){

    }

    private int[] goToSpawnCoords(){
        this.orientation = new Vector(this.agentPositionX,this.agentPositionY,0,0);
        this.orientation.setLength(this.visionRange);
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

    private int[] getExplored(){

    }



    public int (){
    }

}
