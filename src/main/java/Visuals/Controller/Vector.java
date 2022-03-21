package Visuals.Controller;

public class Vector {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private double angle;
    private int distance;

    public Vector(int x, int y, double angle, int distance){
        this.x1=x;
        this.y1=y;
        this.distance = distance;
        this.angle = angle;
        int a = (int)( this.distance*Math.cos(this.angle));
        int b = (int)(this.distance*Math.sin(this.angle));
        this.x2 = this.x1 + a;
        this.y2 = this.y1 + b;
    }

    public Vector(int x1, int y1, int x2, int y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        double distDouble = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        this.distance = (int) distDouble;
        this.angle = (y2-y1)/distDouble;
    }



    public void turn(double angle){
        /**
         * always counter-clockwise
         * */
        double newAngle = (this.angle + angle);
        if(newAngle>Math.toRadians(360)){
            int iter = (int) (newAngle/Math.toRadians(360));
            newAngle = newAngle - iter*Math.toRadians(360);
        }
        this.angle = newAngle;
    }

    public void setAngle(double newAngle){
        this.angle = newAngle;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2(){
        return this.x2;
    }

    public int getY2(){
        return this.y2;
    }

    public int[] getEndCoords(){
        int[] coords = new int[2];
        coords[0] = x2;
        coords[1] = y2;
        return coords;
    }

    public double getAngle(){
        return this.angle;
    }

    public int[] getStartCoords(){
        int[] coords = new int[2];
        coords[0] = x1;
        coords[1] = y1;
        return coords;
    }

    public Vector unitCopy(){return new Vector(this.x1,this.y1,this.angle,1);}
    /*public Vector add(Vector number){
        return new Vector(this.x+number.getX(), this.y+number.getY());
    }

    public Vector subtract(Vector number){
        return new Vector(this.x-number.getX(), this.y-number.getY());
    }

    public Vector scale(double scale){
        return new Vector(scale*this.getX(),scale*this.getX() );
    }


     */

    /**
     * TODO:create cross and dot product methods
     * a^2-b^2 methods, distance between 2 points euclidean?
     */




    //copy method for A Star
    public Vector copy(){
        return new Vector(this.x1,this.y1,this.x2,this.y2);
    }

    public void setLength(int length) {
        this.distance = length;
    }
}
