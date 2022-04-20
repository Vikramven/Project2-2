package newShit;

import newShit.QLearning.QStates;
import newShit.RayCasting.RayCasting;

import java.util.ArrayList;

public class Agent {

    /*AGENT INFORMATIONS
    * CURRENT LOCATION INFO
    * PAST EXPERIENCE <-- PATH 
    * */
    private int ID;//  could be useful to differenciate agent among their team
    private int currentX;
    private int currentY;
    private float initialAngle;
    private int visionRange = 20;
    private ArrayList<int[]> path;
    private ArrayList<int[]> visionArea;
    private boolean dead;

    //instance of QLearning
    private QStates qLearning = new QStates();

    /*METHOD NAME: Basic Agent Constructor
     * GOAL: to create an agent 
     * TO DO: need to connect with guard and intruder extensions
     * */
    public Agent(float initialAngle, int startX, int startY){
        this.initialAngle = initialAngle;
        this.currentX = startX;
        this.currentY = startY;
        this.dead = false;
    }

    /*METHOD NAME: Vision Area
    * GOAL: to update the Tile directly visible by the Agent
    * PROCEDURE: Calling Ray Casting
    * */
    public void updateVision(){
        this.visionArea = RayCasting.getVision(this.currentX,this.currentY,this.visionRange);
    }

    /*METHOD NAME: Tables
     * GOAL: to store the Q and EM tables informations
     * PROCEDURE: Calls the Qlearn submethod (distinct from Q decision to select best move)
     * */





    // ======================= Getters n Setters ================================
    public Agents.Tile getTile(int x, int y){
        return Agents.Tile(x,y);
    }


    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
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


}
