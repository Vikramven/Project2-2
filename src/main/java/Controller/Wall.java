package Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Wall {
    private Rectangle rectangle;
    private Color color;

    public Wall(Rectangle rectangle){
        this.rectangle=rectangle;
    }
    public void draw(GraphicsContext g){
       //SET COLOR TO BLACK  g.setFill(rectangle.BLACK);
        g.fillRect(rectangle.getX(),rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }
}
