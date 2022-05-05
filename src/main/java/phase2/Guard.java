package phase2;

public class Guard extends Agent{

    public Guard(float initialAngle, int startX, int startY){
            super(initialAngle,startX,startY);
    }

    public String toString(){
        return "Guard "+super.toString();
    }
}
