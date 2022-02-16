package Agents;
import javafx.scene.paint.Color;

public interface CanPlace {
    Vector getPosition();
    Color getColor();

    //wall condition
    boolean isHit();
}

