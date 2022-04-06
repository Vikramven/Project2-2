package newShit.RayCasting;

public class RotationMatrix {
    private double a1;
    private double a2;
    private double b1;
    private double b2;

    // a1 a2
    // b1 b2

    public RotationMatrix(double theta)
    {
        theta = -theta;
        double cosA = Math.cos(Math.toRadians(theta));
        double sinA = Math.sin(Math.toRadians(theta));
        this.a1 = cosA;
        this.a2 = -sinA;
        this.b1 = sinA;
        this.b2 = cosA;
    }







    // ============================== getters n setters ==========================


    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public double getB1() {
        return b1;
    }

    public void setB1(double b1) {
        this.b1 = b1;
    }

    public double getB2() {
        return b2;
    }

    public void setB2(double b2) {
        this.b2 = b2;
    }
}
