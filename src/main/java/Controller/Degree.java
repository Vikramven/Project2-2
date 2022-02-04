package Controller;

public class Degree {
    /**
     * This class is to rotate the agent by the turning degree, can change it
     * to implement the speed? dont think so, keep it as one function
     * @parameters Vector objects of origin of agent, and desired Vector direction
     */

    public Vector initial;
    public Vector goDirection;

    /**
     * constructor to return above
     */
    public Degree(Vector initial, Vector goDirection){
        this.initial=initial;
        this.goDirection=goDirection;
    }

    /**
     * getter setter methods
     */

    public Vector getGoDirection() {
        return goDirection;
    }

    public Vector getInitial() {
        return initial;
    }

    /**
     * how to rotate?
     *This recursion really gon be a pain
     * @param degree
     * @return
     */
    public Degree turn(double degree){
        Vector minusVector = goDirection.subtract(initial);
        Vector turned=minusVector.turn(degree);
        Vector resultVector=initial.add(turned);
        return new Degree(initial,resultVector);
    }
}
