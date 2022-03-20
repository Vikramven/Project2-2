package Agents;

import java.util.ArrayList;

public class NeoExploAlgoPerAgent {
    ArrayList<ExploAgent> listOfActualAgents = new ArrayList<>();
    ArrayList<ExplorationState> listOfAgentsExplorationState = new ArrayList<>();
    Map map;

    public NeoExploAlgoPerAgent(){
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

    public int[] explore(Agent givenAgent){
        // create corresponding exploAgent

        ExploAgent agent = createCorrespondingExploAgent(givenAgent);

        // if given agent is not in list
        // add to list
        // and create explostate


        map = agent.getMap();

        if(!listOfActualAgents.contains(agent)){
            listOfActualAgents.add(agent);
            ExplorationState explorationState = new ExplorationState();
            explorationState.determineIfNeedsToMoveUpOrDown(agent.getBetaAngle());
            listOfAgentsExplorationState.add(explorationState);

            explorationState.setGoRight(getOrientationFromAngle(agent.getBetaAngle()));
            explorationState.setInRegion(true);

            return putInsideOwnRegion(agent);
        }

        else{
            int indexInList = listOfActualAgents.indexOf(agent);
            ExplorationState exploState = listOfAgentsExplorationState.get(indexInList);

            if(exploState.needsToChangeRow()){
                if(exploState.needsToGoUp()){
                    int tileUpY = agent.getCurrentTile().getYCoord()-1;
                    exploState.setNeedsToChangeRow(!exploState.needsToChangeRow());
                    if (map.isInMap(agent.getCurrentTile().getXCoord(), tileUpY))
                        return new int[] {agent.getCurrentTile().getXCoord(), tileUpY};
                    else
                        return null;
                }
                else{
                    int tileUpY = agent.getCurrentTile().getYCoord()+1;
                    exploState.setNeedsToChangeRow(!exploState.needsToChangeRow());
                    if (map.isInMap(agent.getCurrentTile().getXCoord(), tileUpY))
                        return new int[] {agent.getCurrentTile().getXCoord(), tileUpY};
                    else
                        return null;
                }
            }
            else{
                boolean goRight = exploState.goesRight();

                ArrayList<Tile> viewingRange = agent.computeViewRange(goRight);

                Tile furthestViewedTile = viewingRange.get(viewingRange.size()-1);

                if (isOnEdgeOfWallOrTrace(furthestViewedTile, goRight)) {
                    exploState.setGoRight(!goRight);
                    exploState.setNeedsToChangeRow(true);
                }

                return new int[] {furthestViewedTile.getXCoord(), furthestViewedTile.getYCoord()};
            }
        }
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


    private ExploAgent createCorrespondingExploAgent(Agent agent){
        int x = agent.getAgentPositionX();
        int y = agent.getAgentPositionY();
        Map map = agent.getAgentMap();
        double betaAngle = agent.getInitialAngle(); // in rads
        ArrayList<int[]> traceList = agent.getAgentTrace();

        return new ExploAgent(x, y, map, betaAngle, traceList);
    }
}
