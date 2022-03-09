package Agents;
//import javafx.scene.paint.Color;
import Controller.Vector;

public interface CanPlace {
    Vector getPosition();
//    Color getColor();

    //wall condition
    boolean isHit();
}

