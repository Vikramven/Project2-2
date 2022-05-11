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
    //private



    public Map(String[] unparsedVars) {
        this.variables = new Variables();
        this.variables.setVariables(unparsedVars);
        this.map = new Tile[variables.getWidth()][variables.getHeight()];
        xSize = variables.getWidth();
        ySize = variables.getHeight();
        tileInit();
        int guardsSize = this.variables.getNumberOfGuards();
        //int intrudersSize = this.variables.getNumberOfIntruders(); //parser doesnt work for this
        int intrudersSize = 4;
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
        this.rayCasting = new RayCasting(this);
        this.listOfGuards.updateAgentVision(this,rayCasting);
        this.listOfIntruders.updateAgentVision(this,rayCasting);
    }

    private void initializeMap(){
        placeWalls();
        placePortals();
        placeShade();
        //this.rayCasting = new RayCasting(this);
        allAgentsInit();
        //System.out.println(listOfGuards);
        //System.out.println(listOfIntruders);
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

    private void placePortals(){
        ArrayList<int[]> inpoints = portalPointsIn();
        ArrayList<int[]> outpoints = portalPointsOut();

        for(int[] c: inpoints){
            map[c[0]][c[1]].placeTeleportIN();
        }

        for(int[] c: outpoints){
            map[c[0]][c[1]].placeTeleportOUT();
        }
    }

    private void placeShade(){
        ArrayList<int[]> shadePoints = shadesPoints();
        for(int[] c: shadePoints){
            map[c[0]][c[1]].placeShade();
        }
    }

    /**
     * UPDATORS: THEY UPDATE THE CURRENT STATE OF THE MAP
     * */



    private void updateAgentVision(){
        /**uses raycasting to update the tiles seen by an agent*/
        RayCasting rayCasting = new RayCasting(this);
        for(AgentTeam team: listOfAllAgents){
            team.updateAgentVision(this,rayCasting);
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

    private ArrayList<int[]> portalPointsIn(){
        ArrayList<Teleport> portals = this.variables.getPortals();
        ArrayList<int[]> inPoints = new ArrayList<>();
        for (Teleport p : portals){
            ArrayList<int[]> portalPoints = p.getPoints();
            portalPoints.remove(portalPoints.size()-1);
            inPoints.addAll(portalPoints);
        }
        return inPoints;
    }

    private ArrayList<int[]> portalPointsOut(){
        ArrayList<Teleport> portals = this.variables.getPortals();
        ArrayList<int[]> outPoints = new ArrayList<>();
        for (Teleport p : portals){
            ArrayList<int[]> portalPoints = p.getPoints();
            int[] endPoint = portalPoints.get(portalPoints.size()-1);
            outPoints.add(endPoint);
        }
        return outPoints;
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
