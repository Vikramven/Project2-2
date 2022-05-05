package phase2;

import Agents.Tile;
import Controller.Variables;

import java.util.ArrayList;

public class Map {

    private Variables variables;
    private Tile[][] map;
    private int xSize;
    private int ySize;
    private ArrayList<AgentTeam> listOfAllAgents;
    private AgentTeam listOfGuards;
    private AgentTeam listOfIntruders;


    public Map(String[] unparsedVars) {
        this.variables = new Variables();
        this.variables.setVariables(unparsedVars);
        this.map = new Tile[variables.getHeight()][variables.getWidth()];
        System.out.println(map.length + " and " + map[0].length);
        int guardsSize = this.variables.getNumberOfGuards();
        int intrudersSize = this.variables.getNumberOfIntruders();
        xSize = variables.getHeight();
        ySize = variables.getWidth();
        listOfGuards = new AgentTeam(guardsSize,0,this.variables.getSpawnAreaGuards().getCoords());
        listOfIntruders = new AgentTeam(intrudersSize,1,this.variables.getSpawnAreaIntruders().getCoords());
        listOfAllAgents.add(listOfGuards);
        listOfAllAgents.add(listOfIntruders);
    }



    public void allAgentsInit(){
        this.listOfGuards.placeOnSpawn();
        this.listOfIntruders.placeOnSpawn();
    }





    public void moveAgentToPosition(Agent agent, int[] position){
        /** moves agent to coordinates if possible */
        if(canMoveTo(position)){
            agent.moveTo(position[0],position[1]);
        }
    }

    private boolean canMoveTo(int[] pos){
        return inMap(pos) && !map[pos[0]][pos[1]].hasWall() && !map[pos[0]][pos[1]].hasAgent();
    }

    private boolean inMap(int[] position){
        return position[0] < xSize && position[1] < ySize && position[0] > 0 && position[1] > 0;
    }


    // Getters n Setters
    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public Variables getVariables() {
        return variables;
    }

    public Tile getTile(int x, int y){
        return this.map[x][y];
    }

    public AgentTeam getGuardsTeam(){ return AgentTeam listOfGuards;}
    public AgentTeam getIntruidersTeam(){ return AgentTeam listOfIntruders;}
}
