package Controller;

public class Matrix {
    //REMARKS : the human controling might be choosing an angle
    // its important to convert it into a Move Object;
    // that means to create a move object with the desired properties

    /**
     * 2 by 2 matrix for swapped positions when turning, use trigo formulae
     * [a b]
     * [c d]
     */
    /**
     * constructor storing  converted values
     */
    private double a;
    private double b;
    private double c;
    private double d;
    public Matrix(int a /*left */,int b/*right*/, int c/*up*/, int d/*down*/){
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
    }

    /**
     * rotated matrix method here
     * @param degree
     */
    public Matrix(double degree){

    }

    /**
     * define rotated method, first need to create product to call
     * @parameter vector object, x and y
     * @param u
     */
    public Vector dotProduct(Vector u ){
        double x=a*u.getX()+ b*u.getY();
        double y= c*u.getX()+d*u.getY();
        return new Vector(x,y);
    }
}
