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
        convertPosition(this.getTeam());// setting agent inside the Map
        updateMap();//update the map With the agent knwoledge
    }

    public void convertPosition([Agent] team){
        int teamSize = team.size();
        int x1 = team[0].getAgentSpawning()[0];
        int y1 = team[0].getAgentSpawning()[1];
        int x2 = team[0].getAgentSpawning()[2];
        int y2 = team[0].getAgentSpawning()[3];

        int width = x1- x2;
        int height = y1 - y2;
        int counter = 0;
//CASE 1:  available position inferior or equal to number of guards
        if(teamSize => (width * height)){
            while( counter  < teamSize ){
                for(int i = x1; i < x2 + 1; i ++){
                    for(int j = y1; j < y2; j ++){
                        //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                        team[i].setAgentPositionX() = i;
                        team[i].setAgentPositionY() = j;
                        counter++;
                    }
                }
            }
        }//END CASE 1

        //CASE 2: available position superior to number of guards
        int decisionRatio= 30/100;

        if(teamSize < decisionRatio * (width * height)){
            while( counter  < teamSize ){
                // top line
                for(int i = x1; i < x2 + 1; i ++){
                    if (counter  < teamSize){
                        //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                        team[counter].setAgentPositionX() = i;
                        team[counter].setAgentPositionY() = y2;
                        counter++;
                    }
                }
                // bottom line
                for(int i = x1; i < x2 + 1; i ++){
                    if (counter  < teamSize){
                        //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                        team[counter].setAgentPositionX() = i;
                        team[counter].setAgentPositionY() = y1;
                        counter++;
                    }
                }
                // start column
                for(int j = y1; j < y2 + 1; j ++){
                    if (counter  < teamSize){
//   map[getAgentSpawning()[0]][i] = 0 ; guards code
                        team[counter].setAgentPositionX() = x1;
                        team[counter].setAgentPositionY() = j;
                        counter++;
                    }
                }
                // end column
                for(int j = y1; j < y2 + 1; j ++){
                    if (counter  < teamSize){
                        //   map[getAgentSpawning()[0]][i] = 0 ; guards code
                        team[counter].setAgentPositionX() = x2;
                        team[counter].setAgentPositionY() = j;
                        counter++;
                    }
                }
            }
        }//END CASE 2
    }//END convertPosition

    public int[] getAgentSpawning(){return spawning;}
    public void setAgentPositionX(int agentPositionX){this.agentPositionX = agentPositionX;}
    public void setAgentPositionY(int agentPositionY){this.agentPositionY = agentPositionY;}
    public int getAgentPositionX(){return this.agentPositionX;}
    public int getAgentPositionY(){return this.agentPositionY;}
    public int[][] getAgentGoal(){return this.agentGoal;}

}