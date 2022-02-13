
package Agents;

import Controller.Variables;
import Controller.Vector;
import Path.Move;

public class Agent  {
    //TODO:create transform matrix method, think we will need it

    Variables variables = new Variables();
    Move agentMove; // update for agent itself and the Map
    Vector getDirection;
    double getHearing;
    int[][] agentPosition;//current position on the Map
    int[][] agentGoal;
    Trace agentTrace; //1 if Intruder, 0 if Guard
    int Team;
    int[] spawning = new int [4];

    int direction; // we try to split the 360 in a smart way

    public Agent(/*int Team,*/ ){
        spawning = variables.getSpawnAreaGuards();
        // Team = Team;
    }
    int [] getAgentSpawning(){return  spawning ; }

    public int[][] getAgentPosition(){return this.agentPosition;}
    public int[][] getAgentGoal(){return this.agentGoal;}

}
