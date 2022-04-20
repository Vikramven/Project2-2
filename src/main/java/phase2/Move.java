package phase2;

public class Move {
    private int x;
    private int y;

    public Move(int x, int y){

    }

    public void move(int a, int b){
        setX(x+a);
        setY(y+b);
    }

    public void moveUp(){
        move(0,1);
    }

    public void moveUpRight(){
        move(1,1);
    }

    public void moveRight(){
        move(1,0);
    }

    public void moveDownRight(){
        move(1,-1);
    }

    public void moveDown(){
        move(0,-1);
    }

    public void moveDownLeft(){
        move(-1,-1);
    }

    public void moveLeft(){
        move(-1,0);
    }

    public void moveUpLeft(){
        move(-1,1);
    }


    public void setX(int x) {
        this.x=x;
    }

    public void setY(int y){
        this.y=y;
    }


}
