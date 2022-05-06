package phase2;

import Agents.Tile;
import Controller.Variables;
import Controller.Wall;
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
        //listOfGuards.placeOnSpawn();
        //listOfIntruders = new AgentTeam(intrudersSize,1,this.variables.getSpawnAreaIntruders().getCoords()); //parser doesnt work
        listOfIntruders = new AgentTeam(intrudersSize,1,this.intruderSpawnWorkaround());
        //listOfGuards.placeOnSpawn();
        listOfAllAgents = new ArrayList<>();
        listOfAllAgents.add(listOfGuards);
        listOfAllAgents.add(listOfIntruders);
        initializeMap();
        System.out.println(this);
    }

    private void tileInit(){
        for(int i = 0; i<xSize;i++){
            for(int j = 0; j<ySize; j++){
                map[i][j] = new Tile(i,j);
            }
        }
    }

    private ArrayList<Integer> intruderSpawnWorkaround(){
        int x = 0;
        int y =0;
        ArrayList<Integer> spawn = new ArrayList<>();
        spawn.add(x);
        spawn.add(y);
        y = 5;
        spawn.add(x);
        spawn.add(y);
        x = 5;
        spawn.add(x);
        spawn.add(y);
        x =0;
        spawn.add(x);
        spawn.add(y);
        return spawn;
    }



    public void allAgentsInit(){
        this.listOfGuards.placeOnSpawn();
        this.listOfIntruders.placeOnSpawn();
    }

    /*
    * updateAllAgents: calls a method that updates a team of agents (moves them, updates their vision etc)
    * */
    public void updateAllAgents(){
        RayCasting rayCasting = new RayCasting(this);
        for(AgentTeam team: listOfAllAgents){
            team.updateAgents(this,rayCasting);
        }
    }


    private void initializeMap(){
        int[] coord = new int[2];
        coord[0] = 0;
        coord[1] = 0;
        //System.out.println(this.wallpoints.get(0)==coord);
        //System.out.println(this.wallpoints.get(0)[0]+" "+this.wallpoints.get(0)[1]);
        /*for(int i = 0; i<this.xSize; i++)
            for(int j = 0; j<this.ySize; j++){
                this.map[i][j] = determineTile(i,j);
                //System.out.println(determineTile(i,j));
            }
        //System.out.println(this);*/
        placeWalls();
        allAgentsInit();
        System.out.println(listOfGuards);
        System.out.println(listOfIntruders);
        placeAgentsOnTiles();
    }

    private void placeWalls(){
        for(int[] wallCoord : this.wallpoints){
            map[wallCoord[0]][wallCoord[1]].placeWall();
        }
    }

    private void placeAgentsOnTiles(){
        for(int i=0;i<xSize;i++){
            for(int j=0;j<ySize;j++){
                map[i][j].removeAgent();
            }
        }
        placeGuards();
        placeIntruders();
    }

    private void placeGuards(){
        for(int[] pos: listOfGuards.getAgentPositions()){
            map[pos[0]][pos[1]].placeGuard();
        }
    }

    private void placeIntruders(){
        for(int[] pos: listOfIntruders.getAgentPositions()){
            map[pos[0]][pos[1]].placeIntruder();
        }
    }

    private Tile determineTile(int i, int j){
       Tile tile = new Tile(i,j);
        if(isAWall(i,j)){
            tile.placeWall();
            //System.out.println("AAAAAAAAAAAAAAAAAAAA");
        }
        if(hasAgent(i,j)){
            //tile.placeAgent();
        }
        return tile;
    }

    private boolean hasAgent(int x, int y){
        int[] coord = new int[2];
        coord[0] = x;
        coord[1] = y;
        for(int[] wallCoord : this.wallpoints){
            if(wallCoord[0]==coord[0] && wallCoord[1]==coord[1]){
                return true;
            }
        }
        return listOfGuards.getAgentPositions().contains(coord) || listOfIntruders.getAgentPositions().contains(coord);
    }

    private boolean isAWall(int x, int y){
        for(int[] wallCoord : this.wallpoints){
            if(wallCoord[0]==x && wallCoord[1]==y){
                return true;
            }
        }
        return false;
    }

    private ArrayList<int[]> wallPoints() {
        tileInit();
        ArrayList<Wall> walls = this.variables.getWalls();
        ArrayList<int[]> wallPoints = new ArrayList<>();

        for (Wall w : walls) {
            //System.out.println(w.getPoints().size());
            ArrayList<int[]> wall = w.getPoints();
            wallPoints.addAll(wall);
            for (int[] xy : wall) {
                //System.out.println("wall xy"+xy[0]+", "+xy[1]);
                //this.map[xy[0]][xy[1]].placeWall();
                //System.out.println(this.map[xy[0]][xy[1]]);
            }
        }

        //System.out.println(this);
        return wallPoints;
    }

    private void printWalls(){
        System.out.println("wall size = "+wallpoints.size());
        for(int[] coords : wallpoints){
            System.out.println("x: "+coords[0]+" y: "+coords[1]);
        }
    }

    private ArrayList<int[]> teleportPoints(){
        return new ArrayList<int[]>();
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
            for(int j = 0; j<ySize; j++){
                String c = map[i][j].toString();
                s.append(c);
            }
            s.append("\n");
        }
        return s.toString();
    }

}
