package Controller;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y){
        this.x=x;
        this.y=y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector add(Vector number){
        return new Vector(this.x+number.getX(), this.y+number.getY());
    }

    public Vector subtract(Vector number){
        return new Vector(this.x-number.getX(), this.y-number.getY());
    }

    public Vector scale(double scale){
        return new Vector(scale*this.getX(),scale*this.getX() );
    }

    /**
     * TODO:create cross and dot product methods
     * a^2-b^2 methods, distance between 2 points euclidean?distance method
     */

    /**
     * 3i+4j, 3 4
     * 2i+7j, cross
      * @param crossProduct
     * @return
     */
    public double cross(Vector crossProduct){
        double a=(this.getY() * crossProduct.getX());
        double b=(this.getX() * crossProduct.getY());
        return a-b;

    }

    public double dotProduct(Vector dot){
        return(this.getX() * dot.getX() + this.getY() * dot.getY());
    }

    /**
     * rott(a^2+b^2), euclidean, x2-x1
     * @param distance
     * @return
     */
    public double distance(Vector distance){
        double a=Math.pow(this.getX()-distance.getX(),2);
        double b=Math.pow(this.getY()-distance.getX(),2);
        return Math.sqrt(a+b);
    }

    /**
     * a^2+b^2
     * @return
     */
    public double length(){
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }
    public Vector normalize(){
        Vector p=new Vector(x,y);
        double length=p.length();
        Vector normalizedVector=p.scale(1/length);
        return normalizedVector;
    }

    //source:wiki
    public double getAngle(){
        double absoluteX=Math.abs(x);
        double absoluteY=Math.abs(y);
        double angle=0;

        if(x >= 0 && y >= 0 && absoluteY >= absoluteX)
            angle = Math.toDegrees(Math.atan(absoluteX / absoluteY));
        else if(x >= 0 && y >= 0 && absoluteY < absoluteX)
            angle = 90 - Math.toDegrees(Math.atan(absoluteY / absoluteX));
        else if(x >= 0 && y < 0 && absoluteY >= absoluteX)
            angle = 90 + Math.toDegrees(Math.atan(absoluteY / absoluteX));
        else if(x >= 0 && y < 0 && absoluteY < absoluteX)
            angle = 180 - Math.toDegrees(Math.atan(absoluteX / absoluteY));
            // Q3
        else if(x < 0 && y < 0 && absoluteY >= absoluteX)
            angle = 180 + Math.toDegrees(Math.atan(absoluteX / absoluteY));
        else if(x < 0 && y < 0 && absoluteY < absoluteX)
            angle = 270 - Math.toDegrees(Math.atan(absoluteY / absoluteX));
            // Q4
        else if(x < 0 && y >= 0 && absoluteX >= absoluteY)
            angle = 270 + Math.toDegrees(Math.atan(absoluteY / absoluteX));
        else if(x < 0 && y >= 0 && absoluteY < absoluteX)
            angle = 180 - Math.toDegrees(Math.atan(absoluteX / absoluteY));

        return angle;
    }

    //copy method for A Star
    public Vector copy(){
        return new Vector(x,y);
    }

    public Vector turn(double degree) {
        Matrix turned=new Matrix(degree);
        return new Vector(0,0);
    }
}
