package Agents;

import Controller.Variables;
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
    Agent[] team = new Agent[variables.getNumberOfGuards()];

    //Agent Geographical Informations
    Variables variables = new Variables();
    int[] spawning = new int [4]; // starting zone of the team
    int agentPositionX;
    int agentPositionY;
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
    public Agent(int team ){
        this.team = team;
        ArrayList<Integer> spawnCoords = variables.getSpawnAreaGuards().getCoords();
    }

    public int[] getAgentSpawning(){return spawning;}
    public void setAgentPositionX(int agentPositionX){this.agentPositionX = agentPositionX;}
    public void setAgentPositionY(int agentPositionY){this.agentPositionY = agentPositionY;}
    public int getAgentPositionX(){return this.agentPositionX;}
    public int getAgentPositionY(){return this.agentPositionY;}
    public int[][] getAgentGoal(){return this.agentGoal;}

    public ArrayList <int[]> getAgentTrace(){
        return AgentTrace;
    }

    public int [] getLastVisited(){
        return AgentTrace.get(AgentTrace.size()-1);
    }
}
