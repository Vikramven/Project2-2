
package Agents;

import Controller.Vector;

public class Agent  {
    //TODO:create transform matrix method, think we will need it

    public Agent(/*int Team*/, int SpawningX, int SpawningY){
        // Team = Team; //1 if Intruider, 0 if Guard
        agentXPosition = SpawningX;
        agentYPosition = SpawningY;
    }
    Move agentMove; // update for agent itself and the Map
    Vector getDirection;
    double getHearing;
    double[][] agentPosition;//current position on the Map
    Trace agentTrace;
    int Team;
    int agentXPosition;
    int agentYPosition;
    int direction; // we try to split the 360 in a smart way


    // getters&setters
    int getAgentXPosition(){return agentXPosition; }
    int getAgentYPosition(){return agentYPosition; }
}