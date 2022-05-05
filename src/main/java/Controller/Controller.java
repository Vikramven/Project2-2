package Controller;

import java.util.ArrayList;

public class Controller {
    private phase2.Map map;
    private ArrayList<phase2.Agent> listOfIntruders;
    private ArrayList<phase2.Agent> listOfGuards;


    public Controller(String[] unparsedVars){
        this.map = initializeMap(unparsedVars);
        //listOfGuards = this.map.get
    }

    private phase2.Map initializeMap(String[] unparsedVars){
        return new phase2.Map(unparsedVars);
    }

}
