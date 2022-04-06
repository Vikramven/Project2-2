package newShit.RayCasting;

import java.util.ArrayList;

public class RayCast {

    private Vector startPos;
    private Vector endPos;

    public RayCast(Vector startVector, Vector endVector){
        this.startPos = startVector;
        this.endPos = endVector;
    }

    public double angle()
    {
        return endPos.sub(u).getAngle();
    }

    public Ray rotate(double degrees)
    {
        Vector a = v.sub(u);
        Vector rotatedVector = a.rotate(degrees);
        Vector b = u.add(rotatedVector);
        return new Ray(this.u, b);
    }

    public double rayLength()
    {
        double yValue = u.getY() - v.getY();
        double xValue = u.getX() - v.getX();
        return Math.sqrt(Math.pow(yValue, 2) + Math.pow(xValue, 2));
    }

}