package phase2.Agent;

public class Intruder extends Agent{

    public Intruder(float initialAngle, int startX, int startY){
        super(initialAngle,startX,startY);
    }

    public String toString(){
        return "Intruder "+super.toString();
    }
}