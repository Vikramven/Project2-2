package phase2;

//import phase2.QLearning.QStates;
import phase2.RayCasting.RayCasting;
import Agents.Tile;
import java.util.ArrayList;

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
    private int ID;//  could be useful to differentiate agent among their team
    private int [] position = new int[2];
    private float initialAngle;
    private float currentAngle;
    private int visionRange = 20;
    private ArrayList<int[]> path = new ArrayList<>(); // AGENT PAST EXPERIENCE
    private ArrayList<int[]> visionArea = new ArrayList<>();
    private boolean dead;

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

    }

    /*
    * update info about the agent:
    *  - vision range
    *  - location
    *  - degree of turn
    *  -
    * */

    public void update(Map map, RayCasting rayCaster){
        //RayCasting rayCaster = new RayCasting(map);
        updateVision(rayCaster);
        printVision();
        moveTo(position[0],position[1]);  //<- some method to make for agent movement with qlearn
        if(!path.contains(this.position)){
            this.path.add(this.position);
        }
    }

    private void printVision(){
        for(int[] point: visionArea){
            System.out.println("x: "+point[0]+" y: "+point[1]);
        }
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

    public boolean inMap(int[] position,Map map){
        return map.inMap(position);
    }

    /*METHOD NAME: Vision Area
    * GOAL: to update the Tile directly visible by the Agent
    * PROCEDURE: Calling Ray Casting
    * */

    public void updateVision(RayCasting rayCaster){
        this.visionArea = rayCaster.getVisibleTiles(this);
    }


    // ======================= Getters n Setters ================================
    public Agents.Tile getTile(int x, int y, Map map){
        return map.getTile(x,y);
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
      //public QStates getQLearning(){return qLearning;};
      //public void setQLearning(QStates qLearning){this.qLearning = qLearning;};

    public String toString(){
        return "position ("+getCurrentX()+","+getCurrentY()+") angle:"+this.currentAngle+"\n";
    }
}
