package Controller;

import com.sun.javafx.geom.Rectangle;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Variables {
    /*
    general comment: variables class is supposed to have publicly available data,
    so it should have public attributes,
     */
    private boolean unlock=true;
    private int mode;
    private int height;
    private int width;
    private int numberOfGuards;
    private int numberOfIntruders;
    private double walkingSpeedGuard;
    private double sprintingSpeedGuards; //do we need this? I think only intruders sprint (to discuss) @zofia 
    private double walkingSpeedIntruder;
    private double sprintingSpeedIntruder;
    private double timeIncrement;
    private double scaling;
    /**
     * TODO:how to define target and spawn area?, texture type?
     */
    private ArrayList<Wall> walls;
    private ArrayList<Teleport> portals;
    private ArrayList<Shade> shades;
    private ArrayList<Texture> textures;



    public Variables(){
        /**
         * constructor for TODO
         */
    }
    public void setMode(int mode){
        if(unlock){
            this.mode=mode;
        }
    }



    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setNumberOfGuards(int numberOfGuards) {
        this.numberOfGuards = numberOfGuards;
    }

    public void setNumberOfIntruders(int numberOfIntruders) {
        this.numberOfIntruders = numberOfIntruders;
    }

    public void setWalkingSpeedGuard(double walkingSpeedGuard) {
        this.walkingSpeedGuard = walkingSpeedGuard;
    }

    public void setSprintingSpeedGuards(double sprintingSpeedGuards) {
        this.sprintingSpeedGuards = sprintingSpeedGuards;
    }

    public void setWalkingSpeedIntruder(double walkingSpeedIntruder) {
        this.walkingSpeedIntruder = walkingSpeedIntruder;
    }

    public void setSprintingSpeedIntruder(double sprintingSpeedIntruder) {
        this.sprintingSpeedIntruder = sprintingSpeedIntruder;
    }

    public void setTimeIncrement(double timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public void setScaling(double scaling) {
        this.scaling = scaling;
    }

    /**
     * TODO:methods for first todo
     */

    public int getMode() {
        return mode;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getNumberOfGuards() {
        return numberOfGuards;
    }

    public int getNumberOfIntruders() {
        return numberOfIntruders;
    }

    public double getWalkingSpeedGuard() {
        return walkingSpeedGuard;
    }

    public double getSprintingSpeedGuards() {
        return sprintingSpeedGuards;
    }

    public double getWalkingSpeedIntruder() {
        return walkingSpeedIntruder;
    }

    public double getSprintingSpeedIntruder() {
        return sprintingSpeedIntruder;
    }

    public double getScaling() {
        return scaling;
    }

    public double getTimeIncrement() {
        return timeIncrement;
    }

    public void createWall(int x1, int y1, int x2, int y2){
        if(unlock){
            this.walls.add(new Wall(x1,y1,x2,y2));
        }
    }

    public void createTeleport(int x1, int y1, int x2, int y2, int x3, int y3, double degree){
        if(unlock){
            this.portals.add(new Teleport(x1,y1,x2,y2,x3,y3,degree));
        }
    }
    public ArrayList<Rectangle2D> getWalls()
    {
        return (ArrayList<Rectangle2D>) this.walls.clone();
    }



    public void createShade(int x1, int y1, int x2, int y2){
        if(unlock){
            this.shades.add(new Shade(x1,y1,x2,y2));
        }
    }


    public void createTexture(int x1, int x2, int x3, int x4, int x5, int x6) {
    }
}
