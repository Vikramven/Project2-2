package Controller;

import java.util.ArrayList;

public class Controller {
    private phase2.Map map;
    private ArrayList<phase2.Agent> listOfIntruders;
    private ArrayList<phase2.Agent> listOfGuards;


    public Controller(Variables v){
        this.map = initializeMap(v);
        //listOfGuards = this.map.get
    }

    private phase2.Map initializeMap(Variables v){
        return new phase2.Map(v);
    }

}
