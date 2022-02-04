
package Agents;

import Controller.Vector;

public interface Agent  {
    void move();
    Vector getDirection();
    double getHearing();
    //need to make one for turning?
}