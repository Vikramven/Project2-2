package Controller;

import javax.swing.text.html.HTML;
import java.util.ArrayList;

public class Variables {

    //simple type variables
    private boolean unlock=true;
    private int mode;
    private int height;
    private int width;
    private double scaling;
    private int numberOfGuards;
    private int numberOfIntruders;
    private double walkingSpeedGuard;
    private double sprintingSpeedGuards; //do we need this? I think only intruders sprint (to discuss) @zofia 
    private double walkingSpeedIntruder;
    private double sprintingSpeedIntruder;
    private double timeStep;


    /**
     * TODO:how to define texture type?
     */

    //complex type variables
    private ArrayList<Wall> walls;
    private ArrayList<Teleport> portals;
    private ArrayList<Shade> shades;
    private ArrayList<Texture> textures;
    private Target target;
    private Spawn spawnAreaGuards;
    private Spawn spawnAreaIntruders;



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

    public void setScaling(double scaling) {
        this.scaling = scaling;
    }

    public void setNumberOfGuards(int numberOfGuards) {
        this.numberOfGuards = numberOfGuards;
    }

    public void setNumberOfIntruders(int numberOfIntruders) {
        this.numberOfIntruders = numberOfIntruders;
    }

    public void setSpawnAreaGuards(int x1, int y1, int x2, int y2){this.spawnAreaGuards = new Spawn(x1,y1,x2,y2);}

    public void setSpawnAreaIntruders(int x1, int y1, int x2, int y2){this.spawnAreaIntruders = new Spawn(x1,y1,x2,y2);}


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

    public void setTimeStep(double timeIncrement) {
        this.timeStep = timeIncrement;
    }

    public void setTargetArea(int x1, int y1, int x2, int y2){
        this.target = new Target(x1,y1,x2,y2);
    }




    /**
     * TODO:methods for first todo
     */
    public ArrayList <Wall> getWalls() {return walls;}; //to be PARSED

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

    public Spawn getSpawnAreaGuards(){return spawnAreaGuards;}
    //we'll have to edit methods in Agent later, spawn is a rectangle like a wall

    public Spawn getSpawnAreaIntruders(){return  spawnAreaIntruders;}

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

    public double getTimeStep() {
        return timeStep;
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

    public void createShade(int x1, int y1, int x2, int y2){
        if(unlock){
            this.shades.add(new Shade(x1,y1,x2,y2));
        }
    }

    public void createTexture(int x1, int x2, int x3, int x4, int x5, int x6) {
        if(unlock){
            this.textures.add(new Texture(x1,x2,x3,x4,x5,x6));
        }
    }
}
