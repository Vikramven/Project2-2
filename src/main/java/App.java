import Controller.Controller;
import Controller.FileParser;
import Controller.PlayOut;
import Controller.ReadFiles;

public class App {

    public App() {
    }

    /**
     * Run
     * @param args
     */
    public static void main(String[] args)
    {
        String path = "";
        String[] unparsedVars = new String[0];
        try {
            unparsedVars = ReadFiles.readFileAsString("recources/testmap.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //PlayOut playOut = new PlayOut(unparsedVars);

        Controller controller= new Controller(unparsedVars);
    }
}