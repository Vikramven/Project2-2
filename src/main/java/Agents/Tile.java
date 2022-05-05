package Agents;

import java.util.ArrayList;

public class Tile {
    private ArrayList<Integer> dataset = new ArrayList<>(); //0 - agent,1 - wall,2 - shade,3 - teleport, 4 - trace, 5 - explored, 6-has flag, 7 - is seen by an agent
    private int x;

    public ArrayList<Integer> getDataset() {
        return dataset;
    }

    private int y;
    private int value;
    private int TRACE_VALUE = 1; // or any other val
    private int WALL_VALUE = Integer.MAX_VALUE;
    private final int size = 8;

    public Tile(int x, int y){
        for(int i = 0; i< this.size; i++){
            dataset.add(0);
        }
        this.x = x;
        this.y = y;
    }

/* METHOD GROUP : SETTERS PLACEMENT
* Place object on the Tile: Agent, Wall, Shade, Teleport
*
 */

    public void placeAgent(phase2.Agent a){
        if(this.dataset.get(1) == 0){
            if(a.getTeam() == 0){ //zero if intruder 1 if guards
                this.dataset.set(0,1);
            }
            else if(a.getTeam() == 1){ //zero if intruder 1 if guards
                this.dataset.set(0,2);
            }
        }
    }

    public void placeWall(){
        if(this.dataset.get(2) == 0 && this.dataset.get(3)==0){
            this.dataset.set(1,1);
            this.value = this.WALL_VALUE;
        }
    }

    public void placeShade(){
        if(this.dataset.get(1) == 0 && this.dataset.get(1)==0){
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
        this.value += this.TRACE_VALUE;
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
        this.dataset.set(0, 0);
    }


    /* METHOD GROUP : BOOLEAN GETTERS
    *       UTILITY: assess what's contains on a given Tile
    *       INFORMATIONAL SET: Agent Wall Shade Teleport Trace
    *       QUESTION / Remark : are the 3 following outdated : Explored, Flag, Visible ?
    *
    */

    public boolean hasAgent(){
        return this.dataset.get(0);
    } // always return smt make sure it aint zero

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

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
            if(tile.hasAgent()){
                this.placeAgent();
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
}
