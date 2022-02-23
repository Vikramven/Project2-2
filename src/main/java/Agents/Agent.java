:package Agents;

import Controller.Variables;
import Controller.Vector;
import Path.Move;

public class Agent  {
    //TODO:create transform matrix method, think we will need it

    Variables variables = new Variables();
    Move agentMove; // update for agent itself and the Map
    Vector getDirection;
    double getHearing;
    /*It is interesting for each agent to contain a copy of the map,
    but  when updating the map this also means updating each of the agents memory
     */

    int agentPositionX;//current position on a copy of the Map
    int agentPositionY;
    int[][] agentGoal;
    Trace agentTrace; //-11 if Intruder, 0 if Guard
    int teamCode; //1 if Intruder, 0 if Guard
    Agent[] team = new Agent[variables.getNumberOfGuards()];
    int[] spawning = new int [4];

    int direction; // we try to split the 360 in a smart way

    public Agent(int team ){
        this.team = team;
        for(int i = 0; i < 4 ; i++) {
            spawning[i] = variables.getSpawnAreaGuards()[i];
        }
        // convertPosition(this.getTeam());// setting agent inside the Map
        updateMap();//update the map With the agent knwoledge
    }


    public int[] getAgentSpawning(){return spawning;}
    public void setAgentPositionX(int agentPositionX){this.agentPositionX = agentPositionX;}
    public void setAgentPositionY(int agentPositionY){this.agentPositionY = agentPositionY;}
    public int getAgentPositionX(){return this.agentPositionX;}
    public int getAgentPositionY(){return this.agentPositionY;}
    public int[][] getAgentGoal(){return this.agentGoal;}

}
