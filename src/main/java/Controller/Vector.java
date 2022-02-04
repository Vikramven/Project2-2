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

    //copy method for A Star
    public Vector copy(){
        return new Vector(x,y);
    }
}
