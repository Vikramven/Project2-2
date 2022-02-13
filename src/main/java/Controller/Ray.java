package Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Ray {
    public Vector x;
    public Vector y;
    public Color color=Color.RED;
    /**
     * rotation by 1 degree
     */
    final double rayWidth=1;
    public Ray(Vector x, Vector y){
        this.x=x;
        this.y=y;
    }

    public Vector getX() {
        return x;
    }

    public Vector getY() {
        return y;
    }
    public double angle(){
        return y.subtract(x).getAngle();
    }

    public Ray rotate(double degrees){
        Vector unit=y.subtract(x);
        Vector rotated=unit.turn(degrees);
        Vector result=x.add(rotated);
        return new Ray(this.x,result);
    }
    public void draw(GraphicsContext g){
        g.setStroke(color);
        g.setLineWidth(rayWidth);
        g.strokeLine(getX().getX(), getX().getY(), getY().getX(), getY().getY());
    }
}
