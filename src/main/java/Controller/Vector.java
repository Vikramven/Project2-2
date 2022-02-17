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
     * a^2-b^2 methods, distance between 2 points euclidean?
     */




    //copy method for A Star
    public Vector copy(){
        return new Vector(x,y);
    }

    public Vector turn(double degree) {
        //Matrix turned=new Matrix(degree);
        return new Vector(0,0);
    }
}
