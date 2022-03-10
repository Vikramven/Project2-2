package Agents;

import Controller.Variables;
import Controller.FileParser;
import java.lang.Math;
import Controller.Vector;
import Path.Move;

import java.util.ArrayList;

/*
goal: last tile in vision in the alpha direction is the goal
when you reach goal
 */


public class Agent  {
    //INSTANCES of class Agent
    //Agent Team information
    int teamCode; //1 if Intruder, 0 if Guard

    int relativePosX; //relative position to the start which is (0,0) the moment before you start avoiding the wall
    int relativePosY;
    double initialAngle;
    int strategy;
    int avoidance;
    int agentSize;
    int teamSize;
    int visionRange = variables.getDistanceViewing();

    Variables variables = new Variables();
    Agent[] team;

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
    }

    public int[] getAgentSpawning(){return spawning;}
    public void setAgentPositionX(int agentPositionX){this.agentPositionX = agentPositionX;}
    public void setAgentPositionY(int agentPositionY){this.agentPositionY = agentPositionY;}
    public int getAgentPositionX(){return this.agentPositionX;}
    public int getAgentPositionY(){return this.agentPositionY;}
    public int[][] getAgentGoal(){return this.agentGoal;}

    public void setInitialAngle( int angle ){this initialAngle = angle; }

    public ArrayList <int[]> getAgentTrace(){
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
        int GoalA = new int [2];
        GoalA = CoordsCreator(initialAngle, visionRange );

        if( GoalA[0] >= limitX  || GoalA[1] >= limitY ){
            return 1; //Agent is in vision range of the mapLimit
        }
            return 0; //Agent is not in vision range of the mapLimit
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



    public int (){
    }

}
