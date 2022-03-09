
package Agents;

import Controller.Vector;

public interface Agent  {
    /**
     * @author vikram
     * TODO:create transform matrix method, think we will need it
     */
    void move();
    Vector getDirection();
    double getHearing();
    //need to make one for turning?
}