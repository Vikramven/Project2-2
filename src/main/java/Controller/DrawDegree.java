package Controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class DrawDegree {
    private Vector a;
    private Vector b;
    private Color color=Color.RED;

    public DrawDegree(Vector a, Vector b){
        this.a=a;
        this.b=b;
    }

    public Vector getA() {
        return a;
    }

    public Vector getB() {
        return b;
    }

    public DrawDegree rotate(double degree){
        Vector minusVector =b.subtract(a);
        Vector turned=minusVector.turn(degree);
        Vector resultVector=a.add(turned);
        return new DrawDegree(a,resultVector);
    }

    public void draw(GraphicsContext g){
        g.setStroke(color);
        g.strokeLine(getA().getX(), getA().getY(), getB().getX(), getB().getY());
    }
}
