package phase2;

//import phase2.QLearning.QStates;
import Agents.Tile;
import phase2.RayCasting.RayCasting;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Agent {

    /*AGENT INFORMATIONS
    * CURRENT LOCATION INFO
    * PAST EXPERIENCE <-- PATH 
    * */

    /**
    *
    * The agent can get map info by passing map into each method that uses it
    * NOTE: pass map to each method
     *
    * */
    private int [] position = new int[2];
    private float initialAngle;
    private float currentAngle;
    private int visionRange = 20;
    private ArrayList<int[]> path = new ArrayList<>(); // AGENT PAST EXPERIENCE
    private ArrayList<int[]> visionArea = new ArrayList<>();
    private boolean dead;
    //private boolean

    //uniqueID used to generate agent id
    static AtomicInteger uniqueId=new AtomicInteger();
    private final int id;//  could be useful to differentiate agent among their team



    //instance of QLearning
    //private QStates qLearning = new QStates();

    /*INSTANCE NAME: Tables
     * to store the Q and EM tables informations
     * PROCEDURE: Calls the Qlearn submethod (distinct from Q decision to select best move)
     * */


    public int[][] EMTable;
    public int[][] QTable;

    /*METHOD NAME: Basic Agent Constructor
     * GOAL: to create an agent 
     * TO DO: need to connect with guard and intruder extensions
     * */
    public Agent(float initialAngle, int startX, int startY){
        this.initialAngle = initialAngle;
        position[0] = startX;
        position[1] = startY;
        this.dead = false;
        this.id = uniqueId.incrementAndGet();
    }

    /*
    * update info about the agent:
    *  - vision range
    *  - location
    *  - degree of turn
    * */

    public void update(Map map, RayCasting rayCaster){
        move(map);  //<- some method to make for agent movement with qlearn
        updateVision(rayCaster);

    }



    public void move(Map map) {
        /**
         * TODO : create a method that defines a move based on Qlearn
         * */
        int[] newPos = new int[2];
        newPos[0] = this.position[0]+4;
        newPos[1] = this.position[1]+5;
        this.turnLeft();
        Tile currentTile = map.getTile(newPos[0],newPos[1]);
        if(currentTile.hasTeleportIn()){
            newPos = currentTile.getPortalOut();
        }
        if(inMap(newPos,map)){
            setPosition(newPos);
        }
        if(!path.contains(this.position)){
            this.path.add(this.position);
        }
    }

    /**
     * moveTo methods dont check wether the move is possible, they just set the new coords
     * */


    /**
     * turnRight and turnLeft assume counting degrees counter-clockwise
     * */

    public void turnRight(){
        //turns agent 90degrees to right
        if(this.currentAngle>Math.toRadians(90)) {
            this.currentAngle = (float) Math.toRadians((Math.toDegrees(this.currentAngle) - 90) % 360);
        }
        else{
            this.currentAngle = (float) Math.toRadians(360-(Math.toDegrees(this.currentAngle)-90));
        }
    }

    public void turnLeft(){
        //turns 90 degrees to left
        this.currentAngle = (float) Math.toRadians((Math.toDegrees(this.currentAngle)+90)%360);
    }

    public void moveOnAPath(ArrayList<int[]> instructions){
        for(int[] coords: instructions){
            setPosition(coords);
        }
    }

    public boolean inMap(int[] position,Map map){
        return map.inMap(position);
    }



    public boolean canMove(int[] position, Map map){
        return map.canMoveTo(position);
    }

    /*METHOD NAME: Vision Area
    * GOAL: to update the Tile directly visible by the Agent
    * PROCEDURE: Calling Ray Casting
    * */

    public void updateVision(RayCasting rayCaster){
        try {
            this.visionArea = rayCaster.getVisibleTiles(this);
            System.out.println("agent."+id+": "+visionArea.size());
        }
        catch (Exception e){
            if(this.visionArea.size()<1){
                ArrayList<int[]> vis= new ArrayList<int[]>();
                vis.add(this.getPosition());
                setVisionArea(vis);
                //e.printStackTrace();
            }
        }

    }


    // ======================= Getters n Setters ================================


    /**GETTERS*/

    public int getCurrentX() {
        return position[0];
    }

    public int getCurrentY() {
        return position[1];
    }

    public float getInitialAngle() {
        return initialAngle;
    }

    public float getCurrentAngle(){return this.currentAngle;}
    public float getCurrentAngleDegrees(){
        return (int) Math.toDegrees(this.currentAngle);
    }
    public ArrayList<int[]> getVisionArea(){return visionArea;};


    /**SETTERS*/


    public void setCurrentX(int currentX) {
        this.position[0] = currentX;
    }


    public void setCurrentY(int currentY) {
        this.position[1] = currentY;
    }



    public void setInitialAngle(float initialAngle) {
        this.initialAngle = initialAngle;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    public boolean isDead(){
        /** returns true if the agent is dead (or caught) */
        return this.dead;
    }
    public int getID(){return id;};
    //public void setID(int ID){this.id = id;};
    public int[] getPosition(){return position;};

    public void setPosition(int x, int y){
        this.position[0] = x;
        this.position[1] = y;
    }

    public void setPosition(int[] position){
        this.position = position;
    }

    public ArrayList<int[]> getPath(){return path;};
    public void setPath(ArrayList<int[]> path){this.path = path;};


    public void setVisionArea(ArrayList<int[]> visionArea){this.visionArea = visionArea;};
      //public QStates getQLearning(){return qLearning;};
      //public void setQLearning(QStates qLearning){this.qLearning = qLearning;};

    private String visionToString(){
        StringBuilder s = new StringBuilder();
        for(int[] point: visionArea){
            s.append("x: "+point[0]+" y: "+point[1]+"\n");
        }
        return s.toString();
    }

    public String toString(){
        String s ="position ("+getCurrentX()+","+getCurrentY()+") angle:"+getCurrentAngleDegrees()+", id: "+this.id+"\n";
        return s+"vision: "+visionArea.size()+"\n";
    }


}
