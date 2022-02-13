import javafx.scene.Scene;

public class GuiCreator {
    double fieldHeight;
    double fieldWidth;
    double scale;
    private static final double FIELD_DEPTH = 20;

    private customGroup group;
    private customScene scene;

    public GuiCreator(double height, double width, double scale) {
        this.scale = scale;
        this.fieldHeight = height/scale;
        this.fieldWidth = width/scale;

        createGroup();
        createScene();
        createStage();
    }

    private void createGroup() {
        Floor floor = new Floor(fieldWidth, fieldHeight, FIELD_DEPTH);

        Wall wall = new Wall(0/scale, 0, 60/scale, 60/scale);

        customGroup myGroup = new customGroup();
        myGroup.getChildren().addAll(floor, wall);

        this.group = myGroup;
    }

    public void createScene(){
        this.scene = new customScene(this.group);
    }

    public customStage createStage(){
        return new customStage(this.group, this.scene);
    }


    public customGroup getGroup() {
        return this.group;
    }

    public Scene getScene(){
        return this.scene;
    }
}
