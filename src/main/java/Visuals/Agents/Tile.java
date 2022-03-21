package Visuals.Agents;

public class Tile {
    private int[] dataset; //0 - agent,1 - wall,2 - shade,3 - teleport, 4 - trace, 5 - explored, 6-has flag, 7 - is seen by an agent
    private int x;
    private int y;
    private int value;
    private int TRACE_VALUE = 1; // or any other val
    private int WALL_VALUE = Integer.MAX_VALUE;
    private final int size = 8;

    public Tile(int x, int y){
        this.dataset = new int[size];
        for(int i = 0; i< this.size; i++){
            this.dataset[i]=0;
        }
        this.x = x;
        this.y = y;
    }

    public void placeAgent(){
        if(this.dataset[1] == 0){
            this.dataset[0] = 1;
        }
    }

    public void placeWall(){
        if(this.dataset[2] == 0 && this.dataset[3]==0){
            this.dataset[1] = 1;
            this.value = this.WALL_VALUE;
        }
    }

    public void placeShade(){
        if(this.dataset[1] == 0 && this.dataset[3]==0){
            this.dataset[2] = 1;
        }
    }

    public void placeTeleport(){
        if(this.dataset[1] == 0 && this.dataset[2]==0){
            this.dataset[3] = 1;
        }
    }

    public void placeTrace(){
        this.dataset[4]=1;
        this.value += this.TRACE_VALUE;
    }

    public void setAsExplored(){
        this.dataset[5]=1;
    }

    public void placeFlag(){
        this.dataset[6]=1;
    }

    public void setAsVisible(){
        this.dataset[7]=1;
    }

    public void setAsNotVisible(){
        this.dataset[7]=0;
    }



    public void removeAgent(){
        this.dataset[0]=0;
    }

    public boolean hasAgent(){
        return this.dataset[0]==1;
    }

    public boolean hasWall(){
        return this.dataset[1]==1;
    }

    public boolean hasShade(){
        return  this.dataset[2]==1;
    }

    public  boolean hasTeleport(){
        return this.dataset[3]==1;
    }

    public boolean hasTrace(){
        return this.dataset[4]==1;
    }

    public boolean isExplored(){return  this.dataset[5]==1;}

    public boolean hasFlag(){return this.dataset[6]==1;}

    public boolean isVisible(){
        return this.dataset[7]==1;
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
            if (dataset[i] == 1) {
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
            if(tile.hasTrace()){
                this.placeTrace();
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
        return this.x;
    }
}
