package Controller;

import Agents.Map;
import Agents.Agent;
import Path.Move;
import Path.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayOut {

    private Map map;
    private ArrayList<int[]> flags;
    private final int STOP = 2000;
    Variables variables = new Variables();

    public List<Position> path1;
    public List<Position> path2;
    public List<Position> path3;
    public List<Position> path4;
    public List<Position> path5;

    public PlayOut(String[] unparsedVars){
        this.variables = new Variables();
        this.variables.setVariables(unparsedVars);
        this.map = new Map(variables);

        //System.out.println(Arrays.stream(this.map.getTiles()).toList());
        //path1 = Move.getPath(new Position(this.map.getGuardPositionX(0), this.map.getGuardPositionY(1),end,maze)
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
