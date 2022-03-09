package Visuals.Agents;
//import javafx.scene.paint.Color;
import Visuals.Controller.Vector;

public interface CanPlace {
    Vector getPosition();
//    Color getColor();

    //wall condition
    boolean isHit();
}

