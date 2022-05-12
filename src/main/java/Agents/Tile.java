package Agents;

import java.util.ArrayList;

public class Tile {
    private ArrayList<Integer> dataset = new ArrayList<>();
    //0 - guard,1 - wall,2 - shade,3 - teleport, 4 - trace, 5 - explored, 6-has flag, 7 - is seen by an agent 8 -intruder 9
    private int x;
    private int y;
    private int GUARD = 0;
    private int INTRUDER = 1;
    private int SHADE = 2;
    private int WALL = 3;
    private int T_IN = 4;
    private int T_OUT = 5;
    private int TRACE = 6;
    private int EXP = 7;
    private int FLAG = 8;
    private int SEEN = 9;

    private int stressLevel; //
    private int TRACE_VALUE = 1; // or any other val
    private int WALL_VALUE = Integer.MAX_VALUE;
    private int portalID = 0;
    private int[] portalOut = new int[2];
    private final int size = 10;


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";



    public Tile(int x, int y){
        for(int i = 0; i< this.size; i++){
            dataset.add(0);
        }
        this.x = x;
        this.y = y;
    }

    public void placeGuard(){
        if(!this.hasWall() && !this.hasAgent()){
            this.dataset.set(this.GUARD,1);
        }
    }

    public void placeIntruder(){
        if(!this.hasWall() && !this.hasAgent()){
            this.dataset.set(this.INTRUDER,1);
        }
    }


    public void placeWall(){
        if(!hasShade() && !hasTeleportIn() && !hasTeleportOut()){
            this.dataset.set(this.WALL,1);
            this.stressLevel = this.WALL_VALUE;
        }
    }

    public void placeShade(){
        if(!hasWall()){
            this.dataset.set(this.SHADE,1);
        }
    }

    public void placeTeleportIN(int portalID,int[] portalOut){
        if(!hasWall()){
            this.dataset.set(this.T_IN,1);
            this.portalID = portalID;
            this.portalOut = portalOut;
        }
    }

    public void placeTeleportOUT(int portalID){
        if(!hasWall()){
            this.dataset.set(this.T_OUT,1);
            this.portalID = portalID;
        }
    }

    public void placeTrace(){
        this.dataset.set(4,1);
        this.stressLevel = 1;
    }

    public void placeTrace(int stressLevel){
        this.dataset.set(TRACE,1);
        if(this.stressLevel < stressLevel){
            this.stressLevel = stressLevel;
        }
    }

    public void setAsExplored(){
        this.dataset.set(EXP,1);
    }

    public void placeFlag(){
        this.dataset.set(FLAG,1);
    }

    public void setAsVisible(){
        this.dataset.set(SEEN,1);
    }

    public void setAsNotVisible(){
        this.dataset.set(SEEN,0);
    }


    public void removeAgent(){
        this.removeGuard();
        this.removeIntruder();
    }

    public void removeGuard(){
        this.dataset.set(GUARD, 0);
    }

    public void removeIntruder(){
        this.dataset.set(INTRUDER, 0);
    }

    public boolean hasAgent(){
        return hasGuard() || hasIntruder();
    }

    public boolean hasIntruder(){
        return this.dataset.get(INTRUDER)==1;
    }

    public boolean hasGuard(){
        return this.dataset.get(GUARD)==1;
    }

    public boolean hasWall(){
        return this.dataset.get(WALL)==1;
    }

    public boolean hasShade(){
        return  this.dataset.get(SHADE)==1;
    }

    public  boolean hasTeleportIn(){
        return this.dataset.get(T_IN)==1;
    }

    public  boolean hasTeleportOut(){
        return this.dataset.get(T_OUT)==1;
    }
    public boolean hasTrace(){
        return this.dataset.get(TRACE)==1;
    }

    public boolean isExplored(){return  this.dataset.get(EXP)==1;}

    public boolean hasFlag(){return this.dataset.get(FLAG)==1;}

    public boolean isVisible(){
        return this.dataset.get(SEEN) == 1;
    }

    public int getStressLevel(){
        return stressLevel;
    }

    public void setStressLevel(int stressLevel) {
        this.stressLevel = stressLevel;
    }

    public ArrayList<Integer> getDataset() {
        return dataset;
    }

    public int[] getPortalOut(){return this.portalOut;}

    public boolean isEmpty(){
        boolean isEmpty = true;
        for(int i=0; i<size; i++){
            if (dataset.get(i) != 0) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    public void overlay(Tile tile){
        if(!tile.hasWall() && !this.hasWall()){
            if(tile.hasTrace()){
                this.placeTrace();
            }
            if(tile.hasGuard()){
                this.placeGuard();
            }
            if(tile.hasIntruder()){
                this.placeIntruder();
            }
            if(tile.hasFlag()){
                this.placeFlag();
            }
            if(tile.hasShade()){
                this.placeShade();
            }
            if(tile.hasTeleportIn()){
                this.placeTeleportIN(this.portalID,this.portalOut);
            }
        }
    }

    public int getXCoord() {
        return this.x;
    }

    public int getYCoord() {
        return this.y;
    }

    public String toString(){
        String s = "_";

        if(this.hasTeleportIn()){
            s = "T";
        }
        if(this.hasTeleportOut()){
            s = "O";
        }
        if(this.hasShade()){
            s = "S";
        }
        if(this.hasWall()) {
            s = "W";
        }
        if(this.hasShade()){
            s = "H";
        }
        if(this.hasGuard()){
            s = "G";
        }
        if(this.hasIntruder()){
            s = "I";
        }

        if(this.isVisible()){
            s = ANSI_GREEN+s+ANSI_RESET;
        }

        return s;
    }
}
