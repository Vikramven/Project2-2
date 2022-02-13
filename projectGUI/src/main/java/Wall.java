import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class Wall extends Box {

    private static final double WALL_WIDTH = 10;
    private static final double WALL_DEPTH = 60;
    private double wallHeight;

    private double x1, y1, x2, y2;

    PhongMaterial wallMaterial = new PhongMaterial();

    public Wall(double x1, double y1, double x2, double y2){
        super(WALL_WIDTH, 10, WALL_DEPTH); //height set to 10 but will be changed anw  doesnt matter

        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        getHeightFromCoords();
        this.setHeight(wallHeight);
        rotateCorrectly();
        normalizeCoordsToFloor(Main.FIELD_WIDTH/Main.SCALE, Main.FIELD_HEIGHT/Main.SCALE);
        setMaterialAsColor();

    }

    private void getHeightFromCoords(){
        this.wallHeight = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }

    private void rotateCorrectly(){
        double ang = getRotationAngle();

        Rotate r = new Rotate(ang, Rotate.Z_AXIS);
        Transform transform = new Rotate();
        transform = transform.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(transform);
    }

    private double  getRotationAngle(){
        double angRad = Math.atan((y1-y2)/(x1-x2));
        double angDeg = (angRad*180)/Math.PI;
        return -1*angDeg;
    }

    private void setMaterialAsColor(){
        wallMaterial.setDiffuseColor(Color.GREY);
        setMaterial(wallMaterial);
    }

    private void normalizeCoordsToFloor(double floorWidth, double floorHeight){

        System.out.println(getTranslateX());
        System.out.println(getTranslateY());

        double midX, midY;
        midX = (x1+x2)/2;
        midY = (y1+y2)/2;
        setTranslateX(midX-floorWidth/2);
        setTranslateY(midY-floorHeight/2);

        System.out.println("x1: "+x1);
        System.out.println("x2: "+x2);
        System.out.println("y1: "+y1);
        System.out.println("y2: "+y2);
        System.out.println("midX: "+midX);
        System.out.println("midY: "+midY);

        System.out.println(getTranslateX());
        System.out.println(getTranslateY());
    }
}
