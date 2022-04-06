package newShit.RayCasting;

public class Vector {

    public int x;
    public int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector rotate(double degrees)
    {
        RotationMatrix M = new RotationMatrix(degrees);
        return M.dot(this);
    }

    public Vector dot(RotationMatrix matrix)
    {
        double x = (a2 * v.getY()) + (a1 * v.getX());
        double y = (b2 * v.getY()) + (b1 * v.getX());
        return new Vector(x, y);
    }
}

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }

}