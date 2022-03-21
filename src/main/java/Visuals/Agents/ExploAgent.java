package Visuals.Agents;

import java.util.ArrayList;

public class ExploAgent {

    private int VIEW_RANGE = 10;

    private int x, y;
    private double betaAngle;
    private Tile currentTile;
    private Map map;
    private ArrayList<int[]> traceList;

    public ExploAgent(int x, int y, Map map, double betaAngle, ArrayList<int[]> traceList) {
        this.x = x;
        this.y = y;
        this.betaAngle = betaAngle;
        this.map = map;
        this.traceList = traceList;
        this.currentTile = map.getTile(x, y);
    }

    public void setCurrentTile(int x, int y){
        this.x = x;
        this.y = y;
        currentTile = map.getTile(x, y);
    }

    public Tile getCurrentTile(){
        return currentTile;
    }

    public Map getMap() {
        return map;
    }

    public void setVIEW_RANGE(int view_range){
        this.VIEW_RANGE = view_range;
    }

    public ArrayList<int[]> getTraceList() {
        return traceList;
    }

    public double getBetaAngle() {
        return betaAngle;
    }

    public ArrayList<Tile> computeViewRange(boolean goRight){
        ArrayList<Tile> viewRange = new ArrayList<>();
        if(goRight){
            for(int x = this.x+1; x < this.x + VIEW_RANGE+1; x++) {
                assert map != null;
                if(map.isInMap(x, this.y) && !map.getTile(x, this.y).hasTrace() && !map.getTile(x, this.y).hasWall()){
                    viewRange.add(map.getTile(x, this.y));
                }
                else
                    break;
            }
        }
        else{
            for(int x = this.x-1; x > this.x - VIEW_RANGE-1; x--) {
                assert map != null;
                if(map.isInMap(x, this.y) && !map.getTile(x, this.y).hasTrace() && !map.getTile(x, this.y).hasWall()){
                    viewRange.add(map.getTile(x, this.y));
                }
                else
                    break;
            }
        }
        return viewRange;
    }

}
