package agents;

public class Trace {
/*  Trace corresponds to the path, the sequence of location visited by an agent.
    It allows us to track the progress of the exploration of each Agent.
*/

    // int visitCounter; for phase 2
    int mapWidth;
    int mapHeight;
    int[][] trace;
    Agent agentTrace;

    public void Trace(Agent agent, Map map){
        /* Initialize the Trace: it superposes to the map: has the same starting dimensions
                The agent starts with No trace: so the matrix of visited place only contains zero
        */
        agentTrace = agent;
        mapWidth = map.getMapWidth();
        mapHeight = map.getMapHeight();
        trace = new int[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                trace [i][j] = 0;
            }
        }
    }//end of Trace constructor


    public void traceUpdate(/*Agent agent, Map map*/){
       trace[agentTrace.getAgentXPosition()][agentTrace.getAgentYPosition()] = 1; //or visit counter
    }


}
