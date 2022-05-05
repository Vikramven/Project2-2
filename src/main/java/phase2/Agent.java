package phase2;

import phase2.QLearning.QStates;
import Agents.Tile;
import java.util.ArrayList;

public class Agent {

    /*AGENT INFORMATIONS
     * CURRENT LOCATION INFO
     * PAST EXPERIENCE <-- PATH
     * */
    private int ID;//  could be useful to differentiate agent among their team
    private int [] position = new int[2];
    private float initialAngle;
    private float currentAngle;
    private int visionRange = 20;

    private ArrayList<int[]> path; // AGENT PAST EXPERIENCE
    private ArrayList<int[]> visionArea;
    private boolean dead;

    /*  AGENT's MARKERS for collaboration via stigmerical:UNDIRECT communication
     *   1/ TRACE historical past time information, long distance
     *   2/ WAGGLE DANSE : real time update immediate information in time and space
     */
    private Yell dance = new Yell(this);//passing the current Agent Object as parameter ?
    private Trace trace = new Trace(this);

    /* TO DO LIST:
     *       TASK 1. (BASIC) Agent's Markers be connected to the reward table criteria so that Q takes into account
     *       TASK 2. Q should contain a higher level consolidated criteria
     *                by evaluating the markers over a subgroup of geographically close agent
     */

    //instance of QLearning
    private QStates qLearning = new QStates();

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

    }

    public void moveTo(int x, int y){
        this.position[0] = x;
        this.position[1] = y;
    }

    public void moveTo(int[] position){
        this.position = position;
    }

    public void turnLeft(){
        if(this.currentAngle>Math.toRadians(90)) {
            this.currentAngle = (float) Math.toRadians((Math.toDegrees(this.currentAngle) - 90) % 360);
        }
        else{
            this.currentAngle = (float) Math.toRadians(360-(Math.toDegrees(this.currentAngle)-90));
        }
    }

    public void turnRight(){}

    public void moveOnAPath(ArrayList<int[]> instructions){
        for(int[] coords: instructions){
            moveTo(coords);
        }
    }

    public boolean inMap(int[] position){
        /** this should return true if a position is in map
         * TODO : connect agent with map and implement this
         * */
        return true;
    }

    /*METHOD NAME: Vision Area
     * GOAL: to update the Tile directly visible by the Agent
     * PROCEDURE: Calling Ray Casting
     * */

//    public void updateVision(){
//        this.visionArea = RayCasting.getVision(this.position[0],this.position[1],this.visionRange);
//    }


    // ======================= Getters n Setters ================================
    public Agents.Tile getTile(int x, int y){
        return new Tile(x,y);
    }


    public int getCurrentX() {
        return position[0];
    }

    public void setCurrentX(int currentX) {
        this.position[0] = currentX;
    }

    public int getCurrentY() {
        return position[1];
    }

    public void setCurrentY(int currentY) {
        this.position[1] = currentY;
    }

    public float getInitialAngle() {
        return initialAngle;
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
    public int getID(){return ID;};
    public void setID(int ID){this.ID = ID;};
    public int[] getPosition(){return position;};
    public void setPosition(int[] position){this.position = position;};
    public ArrayList<int[]> getPath(){return path;};
    public void setPath(ArrayList<int[]> path){this.path = path;};
    public ArrayList<int[]> getVisionArea(){return visionArea;};
    public float getCurrentAngle(){return this.currentAngle;}
    public void setVisionArea(ArrayList<int[]> visionArea){this.visionArea = visionArea;};
    public QStates getQLearning(){return qLearning;};
    public void setQLearning(QStates qLearning){this.qLearning = qLearning;};
}
