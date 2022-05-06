package Agents;

import java.util.ArrayList;

public class Tile {
    private ArrayList<Integer> dataset = new ArrayList<>();
    //0 - guard,1 - wall,2 - shade,3 - teleport, 4 - trace, 5 - explored, 6-has flag, 7 - is seen by an agent 8 -intruder
    private int x;
    private int y;
    private int stressLevel; //
    private int TRACE_VALUE = 1; // or any other val
    private int WALL_VALUE = Integer.MAX_VALUE;
    private final int size = 9;

    public Tile(int x, int y){
        for(int i = 0; i< this.size; i++){
            dataset.add(0);
        }
        this.x = x;
        this.y = y;
    }

    public void placeGuard(){
        if(!this.hasWall() && !this.hasAgent()){
            this.dataset.set(0,1);
        }
    }

    public void placeIntruder(){
        if(!this.hasWall() && !this.hasAgent()){
            this.dataset.set(8,1);
        }
    }


    public void placeWall(){
        if(this.dataset.get(2) == 0 && this.dataset.get(3)==0){
            this.dataset.set(1,1);
            this.stressLevel = this.WALL_VALUE;
        }
    }

    public void placeShade(){
        if(this.dataset.get(1) == 0){
            this.dataset.set(2,1);
        }
    }

    public void placeTeleport(){
        if(this.dataset.get(1) == 0 && this.dataset.get(2)==0){
            this.dataset.set(3,1);
        }
    }

    public void placeTrace(){
        this.dataset.set(4,1);
        this.stressLevel = 1;
    }

    public void placeTrace(int stressLevel){
        this.dataset.set(4,1);
        if(this.stressLevel < stressLevel){
            this.stressLevel = stressLevel;
        }
    }

    public void setAsExplored(){
        this.dataset.set(5,1);
    }

    public void placeFlag(){
        this.dataset.set(6,1);
    }

    public void setAsVisible(){
        this.dataset.set(7,1);
    }

    public void setAsNotVisible(){
        this.dataset.set(7,0);
    }


    public void removeAgent(){
        this.removeGuard();
        this.removeIntruder();
    }

    public void removeGuard(){
        this.dataset.set(0, 0);
    }

    public void removeIntruder(){
        this.dataset.set(8, 0);
    }

    public boolean hasAgent(){
        return hasGuard() || hasIntruder();
    }

    public boolean hasIntruder(){
        return this.dataset.get(8)==1;
    }

    public boolean hasGuard(){
        return this.dataset.get(0)==1;
    }

    public boolean hasWall(){
        return this.dataset.get(1)==1;
    }

    public boolean hasShade(){
        return  this.dataset.get(2)==1;
    }

    public  boolean hasTeleport(){
        return this.dataset.get(3)==1;
    }

    public boolean hasTrace(){
        return this.dataset.get(4)==1;
    }

    public boolean isExplored(){return  this.dataset.get(5)==1;}

    public boolean hasFlag(){return this.dataset.get(6)==1;}

    public boolean isVisible(){
        return this.dataset.get(7) == 1;
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

    public boolean isEmpty(){
        boolean isEmpty = true;
        for(int i=0; i<size; i++){
            if (dataset.get(i) == 1) {
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
            if(tile.hasTeleport()){
                this.placeTeleport();
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
        if(this.hasGuard()){
            s = "G";
        }
        if(this.hasIntruder()){
            s = "I";
        }
        if(this.hasTeleport()){
            s = "T";
        }
        if(this.hasShade()){
            s = "S";
        }
        if(this.hasWall()){
            s = "W";
        }
        return s;
    }
}
