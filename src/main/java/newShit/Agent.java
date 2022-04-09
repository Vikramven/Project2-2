package newShit;

import java.util.ArrayList;

public class Agent {

    private int ID;
    private int currentX;
    private int currentY;
    private float initialAngle;
    private int visionRange = 20;
    private ArrayList<int[]> path;
    private ArrayList<int[]> visionArea;


    public Agent(float initialAngle, int startX, int startY){
        this.initialAngle = initialAngle;
        this.currentX = startX;
        this.currentY = startY;
    }

    public static void agentInit(){

    }





    // ======================= Getters n Setters ================================

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


}
