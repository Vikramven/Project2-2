public class Move {
    /**
     * the co-ordinate to move to
     */
    private int x;
    private int y;

    private Intruder intruder;

    private double cost;

    private int intruderLocationX;
    private int intruderLocationY;

    public Move(int x, int y, Intruder intruder, double cost, int intruderLocationX, int intruderLocationY) {
        this.x = x;
        this.y = y;
        this.intruder = intruder;
        this.cost = cost;
        this.intruderLocationX = intruderLocationX;
        this.intruderLocationY = intruderLocationY;
    }


    public int getIntruderLocationX() {
        return intruderLocationX;
    }

    /**
     *
     * @param intruderLocationX
     */
    public void setIntruderLocationX(int intruderLocationX) {
        this.intruderLocationX = intruderLocationX;
    }

    /**
     *
     * @return intuderLocationY
     */
    public int getIntruderLocationY() {
        return intruderLocationY;
    }

    /**
     *
     * @param intruderLocationY
     */
    public void setIntruderLocationY(int intruderLocationY) {
        this.intruderLocationY = intruderLocationY;
    }

    /**
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return cost
     */
    public double getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
}
