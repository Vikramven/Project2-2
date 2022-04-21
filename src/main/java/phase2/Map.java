package phase2;

import Agents.Tile;
import Controller.Variables;

import java.util.ArrayList;

public class Map {

    private Variables variables;
    private Tile[][] map;
    private int xSize;
    private int ySize;
    private ArrayList<ArrayList<Agent>> listOfAllAgents;
    private ArrayList<Agent> listOfGuards;
    private ArrayList<Agent> listOfIntruders;


    public Map(String[] unparsedVars) {
        this.variables = new Variables();
        this.variables.setVariables(unparsedVars);
        this.map = new Tile[variables.getHeight()][variables.getWidth()];
        System.out.println(map.length + " and " + map[0].length);
        xSize = variables.getHeight();
        ySize = variables.getWidth();
        listOfGuards = new ArrayList<Agent>();
        listOfIntruders = new ArrayList<Agent>();

        //listOfAllAgents.add(listOfGuards);
        //listOfAllAgents.add(listOfIntruders);

        float initialAngle = (float) (Math.toRadians(360) / variables.getNumberOfGuards());

        int startX = 20;
        int startY = 20;

        for (int i = 0; i < variables.getNumberOfGuards(); i++) {
            System.out.println("Number of guards = " + i);
            System.out.println("angle is " + Math.toDegrees(initialAngle));
            System.out.println(initialAngle * (i + 1));
            // listOfAllAgents.add(new Agent(initialAngle,startX, startY));
            // for (Agent agent: listOfAllAgents) {
            //     System.out.println(agent.getCurrentX() + " ||| " +agent.getCurrentY());
            //  }
        }

    }



    public void agentsInit(int teamID){
        float initialAngle = (float) (Math.toRadians(360)/variables.getNumberOfGuards());

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
}
