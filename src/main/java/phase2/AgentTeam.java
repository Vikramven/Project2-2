package phase2;

import phase2.RayCasting.RayCasting;

import java.util.ArrayList;

public class AgentTeam {
    private ArrayList<Agent> team;
    private int size;
    private int type;
    private ArrayList<int[]> spawnCoords; //list of x,y coordinates
    private ArrayList<int[]> spawnPoints; //x,y coordinates of tiles in the spawn area
    private ArrayList<int[]> agentPositions = new ArrayList<>();


    /***/
    /**
     * COULD HAVE:
     *  - the markers
     *  - other shared data
     * */

    public AgentTeam(int size, int type, ArrayList<Integer> spawnCoords){
        this.size = size;
        this.type = type;
        this.spawnCoords = initSpawnCoords(spawnCoords);
        this.spawnPoints = initSpawnPoints();
        this.team = new ArrayList<>();

    }

    /*
    * placeOnSpawn : creates agents and places them all on spawn, before the agent list is empty
    * */

    public void placeOnSpawn(){
        ArrayList<int[]> editedSpawn = this.spawnPoints;
        if(this.size>this.spawnPoints.size()){
            System.out.println("SPAWN: TOO MANY AGENTS, CAN'T PLACE ALL ON SPAWN");
        }
        else {
            for (int i = 0; i < this.size; i++) {
                //int[] initAgentCoords = this.spawnPoints.get(i);
                int[] initAgentCoords = getRandomSpawnPoint(editedSpawn);
                editedSpawn.remove(initAgentCoords);
                Agent agent;
                if (type == 0) {
                    agent = new Guard(0, initAgentCoords[0], initAgentCoords[1]);
                } else if (type == 1) {
                    agent = new Intruder(0, initAgentCoords[0], initAgentCoords[1]);
                } else {
                    agent = new Agent(0, 0, 0);
                    System.out.println("WARNING: you are using wrong types in the agent team class");
                }
                this.team.add(agent);
            }
        }
        updateAgentPos();
        System.out.println("agents type: "+type+" spawn coords: \n"+this.toString());
    }

    /*
    * updateAgents: update data about each agent using the Agent class method
    * */

    public void updateAgents(Map map, RayCasting raycaster){
        for(Agent agent: team){
            agent.update(map, raycaster);
        }
        updateAgentPos();
    }

    private void updateAgentPos(){
        ArrayList<int[]> positions = new ArrayList<>();
        for(Agent a: team){
            positions.add(a.getPosition());
        }
        this.agentPositions = positions;
    }

    /**
     * INITIALIZERS
     * */

    /*
    * initSpawnCoords: returns the coordinates of the spawn in an ArrayList<int[]> instead of ArrayList<Integer>
    * */

    private ArrayList<int[]> initSpawnCoords(ArrayList<Integer> values) {
        int x1y1[] = new int[2];
        int x2y2[] = new int[2];
        x1y1[0] = values.get(0);
        x1y1[1] = values.get(1);
        x2y2[0] = values.get(4);
        x2y2[1] = values.get(5);

        ArrayList<int[]> coordinates = new ArrayList<>();
        coordinates.add(x1y1);
        coordinates.add(x2y2);
        return coordinates;
    }

    /*
    * initSpawnTilesEasy: returns a list of the coords of all tiles in the spawn
    * in the order from bottom row to top
    * */

    private ArrayList<int[]> initSpawnPoints(){
        int[] start = spawnCoords.get(0);
        int[] end = spawnCoords.get(1);
        ArrayList<int[]> tiles = new ArrayList<>();

        int xLength = Math.abs(start[0]-end[0]);
        int yLength = Math.abs(start[1]-end[1]);
        for(int i = 0; i<yLength; i++){
            for(int j = 0; j<xLength; j++){
                int[] coords = new int[2];
                coords[0] = j;
                coords[1] = i;
                tiles.add(coords);
            }
        }
        return tiles;
    }

    /*
    * getRandomSpawnPoint - returns a random element from the list
    * containing spawn points
    * */
    private int[] getRandomSpawnPoint(ArrayList<int[]> coords){
        int size = coords.size();
        int i = (int) (size*Math.random());
        return coords.get(i);
    }

    /** GETTERS AND SETTERS*/


    public void setSpawnCoords(ArrayList<int[]> coordinates){
        this.spawnCoords = coordinates;
    }


    public ArrayList<int[]> getSpawnCoords(){
        return spawnCoords;
    }

    public ArrayList<int[]> getAgentPositions(){
        return this.agentPositions;
    }


    public ArrayList<Agent> getTeam(){
        return this.team;
    }

    public Agent getAgent(int i){
        try{
            return this.team.get(i);
        }
        catch(Exception e){
            System.out.println("access agent in team; exception thrown: "+e);
        }
        return new Agent(0,0,0);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        for(Agent agent: team){
            s.append(agent.toString());
        }
        return s.toString();
    }


}
