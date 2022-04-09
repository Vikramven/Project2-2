package newShit;

import Agents.Tile;
import Controller.Variables;

import java.util.ArrayList;

public class MapCreation {

    private Variables variables;
    private Tile[][] map;
    private int xSize;
    private int ySize;
    private ArrayList<Agent> listOfAgents;

    public MapCreation(String[] unparsedVars ){
        this.variables = new Variables();
        this.variables.setVariables(unparsedVars);
        this.map = new Tile[variables.getHeight()][variables.getWidth()];
        System.out.println(map.length + " and " + map[0].length);
        xSize = variables.getHeight();
        ySize = variables.getWidth();

        listOfAgents = new ArrayList<>();

        float initialAngle = (float) (Math.toRadians(360) / variables.getNumberOfGuards());

        int startX = 20;
        int startY = 20;

        for (int i = 0; i < variables.getNumberOfGuards(); i++) {
            System.out.println("Number of guards = "+ i);
            System.out.println("angle is " + Math.toDegrees(initialAngle));
            System.out.println(initialAngle*(i+1));
            listOfAgents.add(new Agent(initialAngle,startX, startY));
            for (Agent agent: listOfAgents) {
                System.out.println(agent.getCurrentX() + " ||| " +agent.getCurrentY());
            }
            }

        }






    // Getters n Setters
    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public Variables getVariables() {
        return variables;
    }
}
