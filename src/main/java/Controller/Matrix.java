package Controller;

public class Matrix {
    /**
     * @author vikram
     * 2 by 2 matrix for swapped positions when turning, usse trigo formulae
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
    public Matrix(int a,int b, int c, int d){
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
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
