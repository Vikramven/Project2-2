package Controller;
import javafx.scene.canvas.GraphicsContext;

public interface Boundary {
    void draw(GraphicsContext gc);

    boolean isHit(Ray ray);

    Vector intersection(Ray ray);

    boolean validMove(Vector startPosition,Vector endPosition );
}
