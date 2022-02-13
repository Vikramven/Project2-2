import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class customGroup extends Group {
    Rotate r;
    Transform transform = new Rotate();

    public void rotateByX(int ang){
        r = new Rotate(ang, Rotate.X_AXIS);
        transform = transform.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(transform);
    }

    public void rotateByY(int ang){
        r = new Rotate(ang, Rotate.Y_AXIS);
        transform = transform.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(transform);
    }

    public void rotateByZ(int ang){
        r = new Rotate(ang, Rotate.Z_AXIS);
        transform = transform.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(transform);
    }
}
