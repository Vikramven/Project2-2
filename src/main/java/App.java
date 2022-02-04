import GUI.Frame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class App {
    private Stage stage;
    @Override
    public void start(Stage stage) throws IOException{
        this.stage = stage;
        Frame frame = new Frame(800, 500);
        Scene scene = new Scene(frame,800, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Run
     * @param args
     */
    public static void main(String[] args)
    {
        launch();
    }
}
