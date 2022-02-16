
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
    Trace agentTrace; //-11 if Intruder, 0 if Guard
    int team; //1 if Intruder, 0 if Guard
    int[] spawning = new int [4];

    int direction; // we try to split the 360 in a smart way

    public Agent(/*int Team,*/ ){
        spawning = variables.getSpawnAreaGuards();
        // Team = Team;
    }

    public void convertPosition(){
        //CASE 0:  the spawning is horizontally or vertically parallel to the map coordinates
        if(getAgentSpawning()[0] == getAgentSpawning()[2] /*same x => vertically parallel*/ ) {
            int spawnCorridorSize = Math.abs(getAgentSpawning()[1] - getAgentSpawning()[3]);/
            /* Compare the number of agents to the available size
                only one thing could crah this code
                if the test map has wall's coordonnates that conflict with spawning
            */
            //CASE 0.A : Number of guards equals number of available tiles
            if(spawnCorridorSize == variables.getNumberOfGuards()){
                for(int i = getAgentSpawning()[1]; i < getAgentSpawning()[3]; i ++){
                 //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                    agentPositionX = getAgentSpawning()[0];
                    agentPositionY = i;
                }
            }
            //CASE 0.B: Number of guards is superior to number of available tiles ; bissective


            //CASE 0.C: Number of guards is inferior to number of available tiles ; rendomized


        }
        // CASE 1: the spawning is horizontally or vertically parallel to the map coordinates
        else if(getAgentSpawning()[1] == getAgentSpawning()[3] /*same y => same x*/){
            //CASE 1.A : Number of guards equals number of available tiles

            //CASE 1.B: Number of guards is superior to number of available tiles

            //CASE 1.C: Number of guards is inferior to number of available tiles

        }

        //CASE 2:
        agentPositionX = getAgentSpawning();
        agentPositionY = getAgentSpawning();
    }

    public int[] getAgentSpawning(){return spawning;}
    public void setAgentPositionX(int agentPositionX){this.agentPositionX = agentPositionX;}
    public void setAgentPositionY(int agentPositionY){this.agentPositionY = agentPositionY;}
    public int getAgentPositionX(){return this.agentPositionX;}
    public int getAgentPositionY(){return this.agentPositionY;}
    public int[][] getAgentGoal(){return this.agentGoal;}

}
