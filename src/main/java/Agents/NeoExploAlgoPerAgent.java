package Agents;

import java.util.ArrayList;

public class NeoExploAlgoPerAgent {
    ExplorationState explorationState;
    Map map;

    public NeoExploAlgoPerAgent(){
        explorationState = new ExplorationState();
    }

    static void addToTraceList(Map map, ArrayList<Tile> traceList, int x, int y){
        Tile tile = map.getTile(x, y);
        traceList.add(tile);
        tile.placeTrace();
    }

    static ArrayList<int[]> listToArr(ArrayList<Tile> list){
        ArrayList<int[]> returnList = new ArrayList<>();

        for(Tile tile : list){
            returnList.add(new int[] {tile.getXCoord(), tile.getYCoord()});
        }

        return returnList;
    }

    public int[] explore(Agents givenAgents){

        ExploAgent agent = createCorrespondingExploAgent(givenAgents);
        map = agent.getMap();

        if(!explorationState.isInRegion()){
            explorationState = new ExplorationState();  // create explo state
            explorationState.determineIfNeedsToMoveUpOrDown(agent.getBetaAngle());  // determine if move up or down
            explorationState.setGoRight(getOrientationFromAngle(agent.getBetaAngle()));  // determine if move right or left
            explorationState.setInRegion(true);  // say its in region

            return putInsideOwnRegion(agent); // return int[] in region
        } // if agent is not In region

        else{
            if(explorationState.needsToChangeRow()){
                if(explorationState.needsToGoUp()) {
                    int tileUpY = agent.getCurrentTile().getYCoord() - 1; // get y of tile up
                    int tileUpX = agent.getCurrentTile().getXCoord(); // get x of tile up
                    explorationState.setGoRight(!explorationState.goesRight()); // make it switch

                    while (map.getTile(tileUpX, tileUpY).hasTrace()) { // while tile up has trace
                        if (map.getTile(tileUpX, tileUpY + 1).hasTrace())
                            return null;
                        //go back until tile up doesnt have trace
                        if (explorationState.goesRight()) // if it was original
                            tileUpX = tileUpX + 1; // move baxk towards left
                        else
                            tileUpX = tileUpX - 1; //
                    }
                    explorationState.setNeedsToChangeRow(!explorationState.needsToChangeRow());
                    return new int[]{tileUpX, tileUpY};
                } // if agent needs to move to row up
                else{
                    int tileUpY = agent.getCurrentTile().getYCoord() + 1; // get y of tile down
                    int tileUpX = agent.getCurrentTile().getXCoord(); // get x of tile down
                    explorationState.setGoRight(!explorationState.goesRight()); // make it switch

                    while (map.getTile(tileUpX, tileUpY).hasTrace()) { // while tile up has trace
                        if (map.getTile(tileUpX, tileUpY - 1).hasTrace()) // if there is no space to go there
                            return null;
                        //go back until tile up doesnt have trace
                        if (explorationState.goesRight())
                            tileUpX = tileUpX + 1;
                        else
                            tileUpX = tileUpX - 1; //
                    }
                    explorationState.setNeedsToChangeRow(!explorationState.needsToChangeRow());
                    return new int[]{tileUpX, tileUpY};
                } // if agent needs to move to row down
            } // if agent needs to change row
            else{
                boolean goRight = explorationState.goesRight(); // go right

                ArrayList<Tile> viewingRange = agent.computeViewRange(goRight); // get viewing range
                Tile furthestViewedTile = viewingRange.get(viewingRange.size()-1); // get furthest tile you see

                if (isOnEdgeOfWallOrTrace(furthestViewedTile, goRight)) { // if agent sees wall or trace
                    explorationState.setGoRight(!goRight); // make it changes direction since it sees a wall
                    explorationState.setNeedsToChangeRow(true); // now that it sees a wall, it needs to change row
                }
                return new int[] {furthestViewedTile.getXCoord(), furthestViewedTile.getYCoord()}; // return furthest viewed title coords
            } // if agent does not need to change row and has to continue on its lancee
        } // if agent is in region
    }

    private int[] putInsideOwnRegion(ExploAgent agent){
        Tile tileToMoveAgentOn;
        for(int i = 1; i < agent.getTraceList().size()-1; i++) {
            int[] tileCoords = agent.getTraceList().get(i);
            Tile traceTile = map.getTile(tileCoords[0], tileCoords[1]);
            if (!getTileWhenMovingInCorrectDirection(traceTile, agent.getBetaAngle()).hasTrace()) {
                tileToMoveAgentOn = getTileWhenMovingInCorrectDirection(traceTile, agent.getBetaAngle());
                int tileToMoveOnX = tileToMoveAgentOn.getXCoord();
                int tileToMoveOnY = tileToMoveAgentOn.getYCoord();
                return new int[]{tileToMoveOnX, tileToMoveOnY};
            }
        }
        return null; // will never happen
    }

    private Tile getTileWhenMovingInCorrectDirection(Tile checkTile, double ang){
        boolean moveRight = getOrientationFromAngle(ang);

        if(moveRight)
            return map.getTile(checkTile.getXCoord()+1, checkTile.getYCoord());
        return map.getTile(checkTile.getXCoord()-1, checkTile.getYCoord());
    }

    private boolean getOrientationFromAngle(double ang){
        int angle = (int)Math.toDegrees(ang);
        return (0 < angle && angle <= 90) || (270 < angle && angle < 360);
    }

    private boolean isOnEdgeOfWallOrTrace(Tile tile, boolean agentGoesRight){
        Tile nextTile;
        if (agentGoesRight)
            nextTile = map.getTile(tile.getXCoord() + 1, tile.getYCoord());
        else
            nextTile = map.getTile(tile.getXCoord() - 1, tile.getYCoord());
        return nextTile.hasWall() || nextTile.hasTrace();
    }


    private ExploAgent createCorrespondingExploAgent(Agents agents){
        int x = agents.getAgentPositionX();
        int y = agents.getAgentPositionY();
        Map map = agents.getAgentMap();
        double betaAngle = agents.getInitialAngle(); // in rads
        ArrayList<int[]> traceList = agents.getAgentTrace();

        return new ExploAgent(x, y, map, betaAngle, traceList);
    }
}
