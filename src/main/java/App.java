import Path.Move;
import Path.Position;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static javafx.application.Application.launch;

public class App {
    private Stage stage;

    /*
    @Override
    public void start(Stage stage) throws IOException{
        this.stage = stage;
    }

     */

    /**
     * Run
     * @param args
     */
    public static void main(String[] args)
    {
        //System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        List<Position> pos = Move.getPath(new Position(0,0), new Position(9,9));

        for (Position p : pos){
            System.out.println(p);
        }
    }
}

