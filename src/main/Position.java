package main;

import java.awt.*;

public class Position extends Button {
    private int x;
    private int y;

    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }

    /**
     * Get X coordinate
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Set X coordinate
     * @param x y coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get Y coordinate
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Set Y coordinate
     * @param y y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Clone the object
     * @return clone of the Spot
     */
    public Position clone(){
        return new Position(x, y);
    }


}
