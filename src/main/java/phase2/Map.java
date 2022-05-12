package phase2;

import Agents.Tile;
import Controller.*;
import phase2.RayCasting.RayCasting;

import java.util.ArrayList;

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
    public Map(String[] unparsedVars) {
        this.variables = new Variables();
        this.variables.setVariables(unparsedVars);
        this.map = new Tile[variables.getWidth()][variables.getHeight()];
        xSize = variables.getWidth();
        ySize = variables.getHeight();
        tileInit();
        int guardsSize = this.variables.getNumberOfGuards();
        //int intrudersSize = this.variables.getNumberOfIntruders(); //parser doesnt work for this
        int intrudersSize = 0;
        //initializeMap();
        this.wallpoints = wallPoints();
        //printWalls();

        listOfGuards = new AgentTeam(guardsSize,0,this.variables.getSpawnAreaGuards().getCoords());
        //listOfIntruders = new AgentTeam(intrudersSize,1,this.variables.getSpawnAreaIntruders().getCoords()); //parser doesnt work
        listOfIntruders = new AgentTeam(intrudersSize,1,this.intruderSpawnWorkaround());

        listOfAllAgents = new ArrayList<>();
        listOfAllAgents.add(listOfGuards);
        listOfAllAgents.add(listOfIntruders);

        initializeMap();
    }

    private ArrayList<Integer> intruderSpawnWorkaround(){
        Spawn spawn = new Spawn(140,50,160,70);
        return spawn.getCoords();
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
        updateAgentVision();
        System.out.println("seen1: " + this.seenPoints.size());
        this.placeVisible();
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

    private void placeShade(){
        ArrayList<int[]> shadePoints = shadesPoints();
        for(int[] c: shadePoints){
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

    private void placeVisible(){
        System.out.println("size of seen tiles: "+this.seenPoints.size());
        int counter = 0;
        for(int[] c: this.seenPoints){
            counter++;
            map[c[0]][c[1]].setAsVisible();
        }
        System.out.println("counter: "+counter);
    }

    /**
     * UPDATORS: THEY UPDATE THE CURRENT STATE OF THE MAP
     * */



    private void updateAgentVision(){
        /**uses raycasting to update the tiles seen by an agent*/
        this.seenPoints = new ArrayList<>();
        for(AgentTeam team: listOfAllAgents){
            team.updateAgentVision(this,rayCasting);
            this.seenPoints.addAll(team.getSeenTiles());
            System.out.println("AAAAAAAAAA"+this.seenPoints.size());

        }
    }

    private void updateAgentMoves(){
        /**updates the move (the position) of each agent within the AgentTeam class */
        RayCasting raycast = new RayCasting(this);
        for(AgentTeam team: listOfAllAgents){
            team.moveAgents(this, raycast);
        }
    }


    public void moveAllAgents(){
        /**updates the position and vision of each agent
         * updates the tiles with that info
         * */
        updateAgentMoves();
        updateAgentVision();
        placeVisible();
        placeAgentsOnTiles();
    }




    public boolean hasAgent(int x, int y){
        return map[x][y].hasAgent();
    }

    public boolean hasAgent(int[] pos){
        int x = pos[0];
        int y = pos[1];
        return map[x][y].hasAgent();
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

    private void printWalls(){
        System.out.println("wall size = "+wallpoints.size());
        for(int[] coords : wallpoints){
            System.out.println("x: "+coords[0]+" y: "+coords[1]);
        }
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
    public void setVariables(Variables variables) {
        this.variables = variables;
    }

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
