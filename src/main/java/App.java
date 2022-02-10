import GUI.Frame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class App {
    private Stage stage;


    public void start(Stage stage) throws IOException{
        this.stage = stage;
        Frame frame = new Frame(800, 500);
        Scene scene = new Scene(frame,800, 500);
        //Image topLeft=new Image()
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
