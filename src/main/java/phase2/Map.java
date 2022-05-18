package phase2;

import Agents.Tile;
import Controller.*;
import phase2.RayCasting.RayCasting;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {

    private Variables variables;
    private Tile[][] map;
    private int xSize;
    private int ySize;
    private ArrayList<AgentTeam> listOfAllAgents;
    private AgentTeam listOfGuards;
    private AgentTeam listOfIntruders;
    private ArrayList<int[]> wallpoints = new ArrayList<>();
    private RayCasting rayCasting;
    private ArrayList<int[]> seenPoints = new ArrayList<>();
    //private


    /**
     * Map class contains all methods that operate on map objects
     * */
    public Map(Variables variables) {
        this.variables = variables;
        //this.variables.setVariables(unparsedVars);
        this.map = new Tile[variables.getWidth()][variables.getHeight()];
        xSize = variables.getWidth();
        ySize = variables.getHeight();
        tileInit();
        int guardsSize = this.variables.getNumberOfGuards();
        int intrudersSize = this.variables.getNumberOfIntruders(); //parser doesnt work for this
        this.wallpoints = wallPoints();
        listOfGuards = new AgentTeam(guardsSize,0,this.variables.getSpawnAreaGuards().getCoords());
        listOfIntruders = new AgentTeam(intrudersSize,1,this.variables.getSpawnAreaIntruders().getCoords()); //parser doesnt work
        listOfAllAgents = new ArrayList<>();
        listOfAllAgents.add(listOfGuards);
        listOfAllAgents.add(listOfIntruders);
        initializeMap();
    }

    /**
     * METHODS USED TO INITIALIZE THE MAP
     * */

    private void tileInit(){
        for(int i = 0; i<xSize;i++){
            for(int j = 0; j<ySize; j++){
                map[i][j] = new Tile(i,j);
            }
        }
    }

    public void allAgentsInit(){
        this.listOfGuards.placeOnSpawn(this);
        this.listOfIntruders.placeOnSpawn(this);
    }

    private void initializeMap(){
        placeWalls();
        placePortals();
        placeShade();
        allAgentsInit();
        placeAgentsOnTiles();
    }

    private void placeWalls(){
        for(int[] wallCoord : this.wallpoints){
            map[wallCoord[0]][wallCoord[1]].placeWall();
        }
    }

    private void placeShade(){
        ArrayList<int[]> shadePoints = shadesPoints();
        System.out.println("shad epoints lenth" + shadePoints.size());
        for(int[] c: shadePoints){
            System.out.println("shade coord: "+ Arrays.toString(c));
            map[c[0]][c[1]].placeShade();
        }
    }

    private void placePortals(){
        ArrayList<Teleport> portals = this.variables.getPortals();
        ArrayList<int[]> inPoints = new ArrayList<>();
        for (Teleport p : portals){
            ArrayList<int[]> portalPoints = p.getPoints();
            int id = p.getId();
            int[] out = portalPoints.get(portalPoints.size()-1);
            map[out[0]][out[1]].placeTeleportOUT(id);
            portalPoints.remove(portalPoints.size()-1);
            for(int[] c: portalPoints){
                map[c[0]][c[1]].placeTeleportIN(id,out);
            }
            inPoints.addAll(portalPoints);
        }
    }

    private void placeAgentsOnTiles(){
        /**places agents on tiles of map*/
        for(int i=0;i<xSize;i++){
            for(int j=0;j<ySize;j++){
                map[i][j].removeAgent();
            }
        }
        placeGuards();
        placeIntruders();
    }

    private void placeGuards(){
        for(int[] pos: listOfGuards.getCurrentAgentPositions()){
            map[pos[0]][pos[1]].placeGuard();
        }
    }

    private void placeIntruders(){
        for(int[] pos: listOfIntruders.getCurrentAgentPositions()){
            map[pos[0]][pos[1]].placeIntruder();
        }
    }


    public boolean hasWall(int x, int y){
        return map[x][y].hasWall();
    }

    public boolean hasWall(int[] pos){
        int x = pos[0];
        int y = pos[1];
        return map[x][y].hasWall();
    }



    private ArrayList<int[]> wallPoints() {
        ArrayList<Wall> walls = this.variables.getWalls();
        ArrayList<int[]> wallPoints = new ArrayList<>();

        for (Wall w : walls) {
            ArrayList<int[]> wall = w.getPoints();
            wallPoints.addAll(wall);
        }
        return wallPoints;
    }



    private ArrayList<int[]> shadesPoints(){
        ArrayList<Shade> shades = this.variables.getShades();
        ArrayList<int[]> shadePoints = new ArrayList<>();
        for(Shade s: shades){
            shadePoints.addAll(s.getPoints());
        }
        return shadePoints;
    }


    public void printAgentPos(){
        for(AgentTeam team: listOfAllAgents){
            System.out.println(team);
        }
    }

    private ArrayList<int[]> teleportPoints(){
        return new ArrayList<int[]>();
    }

    public void moveAgentToPosition(Agent agent, int[] position){
        /** moves agent to coordinates if possible */
        if(canMoveTo(position)){
            agent.setPosition(position[0],position[1]);
        }
    }

    public boolean canMoveTo(int[] pos){
        return inMap(pos) && !map[pos[0]][pos[1]].hasWall() && !map[pos[0]][pos[1]].hasAgent();
    }

    public boolean inMap(int[] position){
        return position[0] < xSize && position[1] < ySize && position[0] > 0 && position[1] > 0;
    }

    // Getters n Setters
    public Variables getVariables() {
        return variables;
    }

    public Tile getTile(int x, int y){
        return this.map[x][y];
    }

    public Tile[][] getTiles(){
        return this.map;
    }

    public int getMapHeight(){
        return this.ySize;
    }

    public int getMapWidth(){
        return this.xSize;
    }

    public ArrayList<int[]> getWallpoints(){ return wallpoints;}

    public String toString(){
        StringBuilder s = new StringBuilder();

        for(int i = 0; i<xSize;i++){
            s.append(i+": ");
            for(int j = 0; j<ySize; j++){
                String c = map[i][j].toString();
                s.append(c);
            }
            s.append("\n");
        }
        return s.toString();
    }

}
