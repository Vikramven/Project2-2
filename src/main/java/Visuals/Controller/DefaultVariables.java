package Visuals.Controller;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector4f;

import java.util.Random;

public class DefaultVariables {

    private String name;
    private String gameFile;
    private int gameMode;
    private int height;
    private int width;
    private float scaling;
    private int numGuards;
    private int numIntruders;
    private float baseSpeedIntruder;
    private float sprintSpeedIntruder;
    private float baseSpeedGuard;
    private float timeStep;
    private Vector4f targetArea;
    private Vector4f spawnAreaIntruders;
    private Vector4f spawnAreaGuards;
    private Vector4f wall1;
    private Vector4f wall2;
    private Vector4f wall3;
    private Vector4f wall4;
    private Vector4f wall5;
    private Vector2f spawnIntruder;
    private Vector2f spawnGuard;

    public DefaultVariables() {
    }

    public void setVariables(String[] stringOfAllVariables){

        this.name = stringOfAllVariables[0];
        this.gameFile = stringOfAllVariables[1];
        this.gameMode = Integer.parseInt(stringOfAllVariables[2]);
        this.height = Integer.parseInt(stringOfAllVariables[3]);
        this.width = Integer.parseInt(stringOfAllVariables[4]);
        this.scaling = Float.parseFloat(stringOfAllVariables[5]);
        this.numGuards = Integer.parseInt(stringOfAllVariables[6]);
        this.numIntruders = Integer.parseInt(stringOfAllVariables[7]);
        this.baseSpeedIntruder = Float.parseFloat(stringOfAllVariables[8]);
        this.sprintSpeedIntruder = Float.parseFloat(stringOfAllVariables[9]);
        this.baseSpeedGuard = Float.parseFloat(stringOfAllVariables[10]);
        this.timeStep = Float.parseFloat(stringOfAllVariables[11]);

        String[] targetArea = stringOfAllVariables[12].split(" ");
        String[] spawnAreaIntruders = stringOfAllVariables[13].split(" ");
        String[] spawnAreaGuards = stringOfAllVariables[14].split(" ");
        String[] wall1 = stringOfAllVariables[15].split(" ");
        String[] wall2 = stringOfAllVariables[16].split(" ");
        String[] wall3 = stringOfAllVariables[17].split(" ");
        String[] wall4 = stringOfAllVariables[18].split(" ");
        String[] wall5 = stringOfAllVariables[19].split(" ");

        this.targetArea = new Vector4f(Float.parseFloat(targetArea[0]), Float.parseFloat(targetArea[1]), Float.parseFloat(targetArea[2]), Float.parseFloat(targetArea[3]));
        this.spawnAreaIntruders = new Vector4f(Float.parseFloat(spawnAreaIntruders[0]), Float.parseFloat(spawnAreaIntruders[1]), Float.parseFloat(spawnAreaIntruders[2]), Float.parseFloat(spawnAreaIntruders[3]));
        this.spawnAreaGuards = new Vector4f(Float.parseFloat(spawnAreaGuards[0]), Float.parseFloat(spawnAreaGuards[1]), Float.parseFloat(spawnAreaGuards[2]), Float.parseFloat(spawnAreaGuards[3]));
        this.wall1 = new Vector4f(Float.parseFloat(wall1[0]), Float.parseFloat(wall1[1]), Float.parseFloat(wall1[2]), Float.parseFloat(wall1[3]));
        this.wall2 = new Vector4f(Float.parseFloat(wall2[0]), Float.parseFloat(wall2[1]), Float.parseFloat(wall2[2]), Float.parseFloat(wall2[3]));
        this.wall3 = new Vector4f(Float.parseFloat(wall3[0]), Float.parseFloat(wall3[1]), Float.parseFloat(wall3[2]), Float.parseFloat(wall3[3]));
        this.wall4 = new Vector4f(Float.parseFloat(wall4[0]), Float.parseFloat(wall4[1]), Float.parseFloat(wall4[2]), Float.parseFloat(wall4[3]));
        this.wall5 = new Vector4f(Float.parseFloat(wall5[0]), Float.parseFloat(wall5[1]), Float.parseFloat(wall5[2]), Float.parseFloat(wall5[3]));

        // Random SpawnPoints
        // intruder
        Random random = new Random();

        float randomXIntruder = Float.parseFloat(spawnAreaIntruders[0]) + random.nextInt((int) Float.parseFloat(spawnAreaIntruders[2]));
        float randomYIntruder = Float.parseFloat(spawnAreaIntruders[1]) + random.nextInt((int) Float.parseFloat(spawnAreaIntruders[3]));

        this.spawnIntruder = new Vector2f(randomXIntruder,randomYIntruder);

        //guard
        float randomXGuard = Float.parseFloat(spawnAreaGuards[0]) + random.nextInt((int) Float.parseFloat(spawnAreaGuards[2]));
        float randomYGuard = Float.parseFloat(spawnAreaGuards[1]) + random.nextInt((int) Float.parseFloat(spawnAreaGuards[3]));

        this.spawnGuard = new Vector2f(randomXGuard,randomYGuard);



    }

    @Override
    public String toString() {
        return "Book [name=" + name + "]";
    }

    // getters n setters

    public String getName() {
        return name;
    }

    public String getGameFile() {
        return gameFile;
    }

    public int getGameMode() {
        return gameMode;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float getScaling() {
        return scaling;
    }

    public Vector2f getSpawnIntruder() {
        return spawnIntruder;
    }

    public Vector2f getSpawnGuard() {
        return spawnGuard;
    }

    public int getNumGuards() {
        return numGuards;
    }

    public int getNumIntruders() {
        return numIntruders;
    }

    public float getBaseSpeedIntruder() {
        return baseSpeedIntruder;
    }

    public float getSprintSpeedIntruder() {
        return sprintSpeedIntruder;
    }

    public float getBaseSpeedGuard() {
        return baseSpeedGuard;
    }

    public float getTimeStep() {
        return timeStep;
    }

    public Vector4f getTargetArea() {
        return targetArea;
    }

    public Vector4f getSpawnAreaIntruders() {
        return spawnAreaIntruders;
    }

    public Vector4f getSpawnAreaGuards() {
        return spawnAreaGuards;
    }

    public Vector4f getWall1() {
        return wall1;
    }

    public Vector4f getWall2() {
        return wall2;
    }

    public Vector4f getWall3() {
        return wall3;
    }

    public Vector4f getWall4() {
        return wall4;
    }

    public Vector4f getWall5() {
        return wall5;
    }

}