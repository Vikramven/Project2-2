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



    public Map(String[] unparsedVars) {
        this.variables = new Variables();
        this.variables.setVariables(unparsedVars);
        this.map = new Tile[variables.getHeight()][variables.getWidth()];
        int guardsSize = this.variables.getNumberOfGuards();
        //int intrudersSize = this.variables.getNumberOfIntruders(); //parser doesnt work for this
        int intrudersSize = 4;
        this.wallpoints = wallPoints();
        xSize = variables.getHeight();
        ySize = variables.getWidth();
        listOfGuards = new AgentTeam(guardsSize,0,this.variables.getSpawnAreaGuards().getCoords());
        //listOfGuards.placeOnSpawn();
        //listOfIntruders = new AgentTeam(intrudersSize,1,this.variables.getSpawnAreaIntruders().getCoords()); //parser doesnt work
        listOfIntruders = new AgentTeam(intrudersSize,1,this.intruderSpawnWorkaround());
        //listOfGuards.placeOnSpawn();
        listOfAllAgents = new ArrayList<>();
        listOfAllAgents.add(listOfGuards);
        listOfAllAgents.add(listOfIntruders);
        initializeMap();
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
        //System.out.println(this.listOfGuards);
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
        for(int i = 0; i<this.xSize; i++)
            for(int j = 0; j<this.ySize; j++){
                this.map[i][j] = determineTile(i,j);
            }
    }

    private Tile determineTile(int i, int j){
       Tile tile = new Tile(i,j);
        if(isAWall(i,j)){
            tile.placeWall();
        }
        if(hasAgent(i,j)){
            tile.placeAgent();
        }
        return new Tile(i,j);
    }

    private boolean hasAgent(int x, int y){
        int[] coord = new int[2];
        coord[0] = x;
        coord[1] = y;
        return listOfGuards.getAgentPositions().contains(coord) || listOfIntruders.getAgentPositions().contains(coord);
    }

    private boolean isAWall(int x, int y){
        int[] coord = new int[2];
        coord[0] = x;
        coord[1] = y;
        return this.wallpoints.contains(coord);
    }

    private ArrayList<int[]> wallPoints() {
        ArrayList<Wall> walls = this.variables.getWalls();
        ArrayList<int[]> wallPoints = new ArrayList<>();
        for(Wall w: walls){
            wallPoints.addAll(w.getPoints());
        }
        return new ArrayList<int[]>();
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



}
