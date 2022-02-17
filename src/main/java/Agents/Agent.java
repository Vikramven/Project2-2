
package Agents;
import java.util.ArrayList;
import Controller.Vector;
import Controller.Ray;

public interface Agent  {
    /**
     * @author vikram
     * TODO:create transform matrix method, think we will need it
     */
    Vector move();
    Vector getDirection();
    Vector getPosition();
    double getHearing();
    void updateLocation(Vector endPoint);
    void updateView(ArrayList<Ray> view);



    //need to make one for turning?
}