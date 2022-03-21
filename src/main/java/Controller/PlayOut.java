package Controller;

import Agents.Map;
import Agents.Agent;

import java.util.ArrayList;

public class PlayOut {

    private Map map;
    private ArrayList<int[]> flags;
    private final int STOP = 2000;
    Variables variables = new Variables();

    public PlayOut(String[] unparsedVars){
        this.variables = FileParser.readFile(unparsedVars);
        this.map = new Map(variables);
    }

    public void compute(){
        int i = 0;
        do{
            i++;
            map.mapUpdate();
        }while(!this.map.explored() || i>this.STOP);
    }

    public void computeWithPrint(){
        do{
            map.mapUpdate();
            System.out.print(map);
        }while(!this.map.explored());
    }

    public ArrayList<ArrayList<int[]>> getPaths(){

        compute();

        ArrayList<ArrayList<int[]>> paths = new ArrayList<>();
        Agent[] agents = map.getTeamGuards();
        for (Agent a : agents){
            paths.add(a.getPath());
        }
        return paths;
    }

    public ArrayList<int[]> getFlags(){
        flags = map.getFlags();
        return this.flags;
    }
}
