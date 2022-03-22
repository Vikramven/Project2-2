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
            unparsedVars = ReadFiles.readFileAsString("C:\\Users\\Zaker\\IdeaProjects\\Project2-2Zofia\\recources\\testmap.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlayOut playOut = new PlayOut(unparsedVars);
        playOut.computeWithPrint();
    }
}