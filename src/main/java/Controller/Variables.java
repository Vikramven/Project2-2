package Controller;
import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.Random;

/**
 * TO DO: add a getter and a setter for @distanceViewing and adapt the parser accordingly
 */

public class Variables {

    //simple type variables
    private boolean unlock=true;

    private String name;
    private String gameFile;
    private int mode;
    private int height;
    private int width;
    private float scaling;
    private int numberOfGuards;
    private int numberOfIntruders;
    private float walkingSpeedGuard;
    private float sprintingSpeedGuard;
    private float walkingSpeedIntruder;
    private float sprintingSpeedIntruder;
    private float timeStep;

    private Wall wall1Obj;
    private Wall wall2Obj;
    private Wall wall3Obj;
    private Wall wall4Obj;
    private Wall wall5Obj;
    private Teleport teleport1;
    private Shade shade1;
    private Texture texture1;

    private int distanceViewing;
    private int distanceSmelling;
    private int distanceHearingWalking;
    private int distanceHearingSprinting;
    private int numberMarkers;


    /**
     * TODO:how to define texture type?
     */

    //complex type variables
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private ArrayList<Teleport> portals = new ArrayList<Teleport>();
    private ArrayList<Shade> shades = new ArrayList<Shade>();
    private ArrayList<Texture> textures = new ArrayList<Texture>();
    private Target target;
    private Spawn spawnAreaGuards;
    private Spawn spawnAreaIntruders;



    public Variables(){
        /**
         * constructor for TODO
         */
    }

    public void setVariables(String[] stringOfAllVariables){

        //this.name = stringOfAllVariables[0];
        //this.gameFile = stringOfAllVariables[1];
        //this.mode = Integer.parseInt(stringOfAllVariables[2]);
        this.height = Integer.parseInt(stringOfAllVariables[0]);
        this.width = Integer.parseInt(stringOfAllVariables[1]);
        //this.scaling = Float.parseFloat(stringOfAllVariables[5]);
        this.numberOfGuards = Integer.parseInt(stringOfAllVariables[2]);
        this.walkingSpeedGuard = Float.parseFloat(stringOfAllVariables[3]);
        this.distanceViewing = Integer.parseInt(stringOfAllVariables[4]);
        this.numberMarkers = Integer.parseInt(stringOfAllVariables[5]);
        this.distanceSmelling = Integer.parseInt(stringOfAllVariables[6]);

        String[] spawnAreaGuards1 = stringOfAllVariables[7].split(" ");

        String[] wall1 = stringOfAllVariables[8].split(" ");
        String[] wall2 = stringOfAllVariables[9].split(" ");
        String[] wall3 = stringOfAllVariables[10].split(" ");
        String[] wall4 = stringOfAllVariables[11].split(" ");
        String[] wall5 = stringOfAllVariables[12].split(" ");
        String[] teleport = stringOfAllVariables[13].split(" ");

        //String[] spawnAreaIntruders1 = stringOfAllVariables[7].split(" ");


        //this.numberOfIntruders = Integer.parseInt(stringOfAllVariables[7]);
        //this.walkingSpeedIntruder = Float.parseFloat(stringOfAllVariables[8]);
        //this.sprintingSpeedIntruder = Float.parseFloat(stringOfAllVariables[9]);
        //this.walkingSpeedGuard = Float.parseFloat(stringOfAllVariables[10]);
        //this.timeStep = Float.parseFloat(stringOfAllVariables[11]);


        //String[] targetArea = stringOfAllVariables[12].split(" ");

        //String[] spawnAreaGuards1 = stringOfAllVariables[14].split(" ");




        //this.target = new Target(Integer.parseInt(targetArea[0]), Integer.parseInt(targetArea[1]), Integer.parseInt(targetArea[2]), Integer.parseInt(targetArea[3]));
        //this.spawnAreaIntruders = new Spawn(Integer.parseInt(spawnAreaIntruders1[0]), Integer.parseInt(spawnAreaIntruders1[1]), Integer.parseInt(spawnAreaIntruders1[2]), Integer.parseInt(spawnAreaIntruders1[3]));
        this.spawnAreaGuards = new Spawn(Integer.parseInt(spawnAreaGuards1[0]), Integer.parseInt(spawnAreaGuards1[1]), Integer.parseInt(spawnAreaGuards1[2]), Integer.parseInt(spawnAreaGuards1[3]));
        this.wall1Obj = new Wall(Integer.parseInt(wall1[0]), Integer.parseInt(wall1[1]), Integer.parseInt(wall1[2]), Integer.parseInt(wall1[3]));
        this.wall2Obj = new Wall(Integer.parseInt(wall2[0]), Integer.parseInt(wall2[1]), Integer.parseInt(wall2[2]), Integer.parseInt(wall2[3]));
        this.wall3Obj = new Wall(Integer.parseInt(wall3[0]), Integer.parseInt(wall3[1]), Integer.parseInt(wall3[2]), Integer.parseInt(wall3[3]));
        this.wall4Obj = new Wall(Integer.parseInt(wall4[0]), Integer.parseInt(wall4[1]), Integer.parseInt(wall4[2]), Integer.parseInt(wall4[3]));
        this.wall5Obj = new Wall(Integer.parseInt(wall5[0]), Integer.parseInt(wall5[1]), Integer.parseInt(wall5[2]), Integer.parseInt(wall5[3]));

        walls.add(wall1Obj);
        walls.add(wall2Obj);
        walls.add(wall3Obj);
        walls.add(wall4Obj);
        walls.add(wall5Obj);

        // Random SpawnPoints
        // intruder


        //String[] shaded = stringOfAllVariables[21].split(" ");
        //String[] texture = stringOfAllVariables[22].split(" ");

        this.teleport1 = new Teleport(Integer.parseInt(teleport[0]), Integer.parseInt(teleport[1]), Integer.parseInt(teleport[2]), Integer.parseInt(teleport[3]), Integer.parseInt(teleport[4]), Integer.parseInt(teleport[5]), Double.parseDouble(teleport[6]));
        //this.shade1 = new Shade(Integer.parseInt(shaded[0]), Integer.parseInt(shaded[1]), Integer.parseInt(shaded[2]), Integer.parseInt(shaded[3]));
        //this.texture1 = new Texture(Integer.parseInt(texture[0]),Integer.parseInt(texture[1]),Integer.parseInt(texture[2]),Integer.parseInt(texture[3]),Integer.parseInt(texture[4]),Integer.parseInt(texture[5]));

        portals.add(teleport1);
        //shades.add(shade1);
        //textures.add(texture1);

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

    public void setScaling(float scaling) {
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

    public void setDistanceSmelling(int distance){
        this.distanceSmelling = distance;
    }

    public void setDistanceHearingWalking(int distance){
        this.distanceHearingWalking = distance;
    }

    public void setDistanceHearingSprinting(int distance){
        this.distanceHearingSprinting = distance;
    }

    public void setNumberMarkers(int nr){
        this.numberMarkers = nr;
    }

    public void setWalkingSpeedGuard(float walkingSpeedGuard) {
        this.walkingSpeedGuard = walkingSpeedGuard;
    }

    public void setSprintingSpeedGuard(float sprintingSpeedGuards) {
        this.sprintingSpeedGuard = sprintingSpeedGuards;
    }

    public void setWalkingSpeedIntruder(float walkingSpeedIntruder) {
        this.walkingSpeedIntruder = walkingSpeedIntruder;
    }


    public void setTargetArea(int x1, int y1, int x2, int y2){
        this.target = new Target(x1,y1,x2,y2);
    }

    public void setDistanceViewing(int distanceViewing) {
        this.distanceViewing = distanceViewing;
    }



    /**
     * TODO:methods for first todo
     */
    public ArrayList <Wall> getWalls() {return walls;} //to be PARSED

    public ArrayList<Teleport> getPortals(){return portals;}

    public ArrayList<Shade> getShades(){return shades;}

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

    public double getSprintingSpeedGuard() {
        return sprintingSpeedGuard;
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



    public int getDistanceSmelling(){return this.distanceSmelling;}

    public int getDistanceHearingWalking(){return this.distanceHearingWalking;}

    public int getDistanceHearingSprinting(){return this.distanceHearingSprinting;}

    public int getNumberMakers(){return this.numberMarkers;}

    public int getDistanceViewing(){return this.distanceViewing;}

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


    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("walls: "+walls.size());
        return s.toString();
    }



}
