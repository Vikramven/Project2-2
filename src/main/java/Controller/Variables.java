package Controller;

import com.sun.javafx.geom.Rectangle;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.Light;

import java.util.ArrayList;

public class Variables {
    private boolean unlock=true;
    private int mode;
    private int height;
    private int width;
    private int numberOfGuards;
    private int numberOfIntruders;
    private double walkingSpeedGuard;
    private double sprintingSpeedGuards;
    private double walkingSpeedIntruder;
    private double sprintingSpeedIntruder;
    private double timeIncrement;
    private double scaling;
    /**
     * TODO:how to define target and spawn area?, texture type?
     */
    private ArrayList<Rectangle2D> walls;
    private ArrayList<Rectangle2D> towers;
    private ArrayList<Rectangle2D> portals;
    private ArrayList<Rectangle2D> shade;
    private ArrayList<Rectangle2D> textures;
    private ArrayList<Point2D> locationTeleport;
    private Rectangle2D targetArea;
    private Rectangle2D spawnIntruder;
    private Rectangle2D spawnGuards;



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

    public void createWall(Rectangle2D wall){
        if(unlock){
            this.walls.add(wall);
        }
    }

    public void createShade(Rectangle2D shade){
        if(unlock){
            this.shade.add(shade);
        }
    }



}
