import javafx.scene.Camera;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class customStage extends Stage {

    customGroup group;

    public customStage(customGroup group, customScene scene){
        super();
        this.group = group;
        addEventHandlers();

        setTitle("project 2-2 15");
        setScene(scene);
        show();
    }

    private void addEventHandlers(){
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch(event.getCode()){
                case C:
                    this.group.rotateByX(-10);
                    break;
                case V:
                    this.group.rotateByX(10);
                    break;
                case D:
                    this.group.rotateByY(-10);
                    break;
                case F:
                    this.group.rotateByY(10);
                    break;
                case E:
                    this.group.rotateByZ(-10);
                    break;
                case R:
                    this.group.rotateByZ(10);
                    break;
                case T:
                    moveLeft();
                    break;
                case Y:
                    moveRight();
                    break;
                case G:
                    moveUp();
                    break;
                case H:
                    moveDown();
                    break;
                case N:
                    zoomOut();
                    break;
                case B:
                    zoomIn();
                    break;
            }
        });
    }


    private void moveLeft(){
        Camera c = this.getScene().getCamera();
        c.setTranslateX(c.getTranslateX()-50);
    }
    private void moveRight(){
        Camera c = this.getScene().getCamera();
        c.setTranslateX(c.getTranslateX()+50);
    }
    private void moveUp(){
        Camera c = this.getScene().getCamera();
        c.setTranslateY(c.getTranslateY()-50);
    }
    private void moveDown(){
        Camera c = this.getScene().getCamera();
        c.setTranslateY(c.getTranslateY()+50);
    }
    private void zoomOut(){
        Camera c = this.getScene().getCamera();
        c.setTranslateZ(c.getTranslateZ()-50);
    }
    private void zoomIn(){
        Camera c = this.getScene().getCamera();
        c.setTranslateZ(c.getTranslateZ()+50);
        System.out.println(c.getTranslateZ());
    }
}
