import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final double FIELD_HEIGHT = 80;
    public static final double FIELD_WIDTH = 120;
    public static final double SCALE = 0.3;

    @Override
    public void start(Stage primaryStage) throws Exception{

        new GuiCreator(FIELD_HEIGHT, FIELD_WIDTH, SCALE);

    }
}

