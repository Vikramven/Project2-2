package Agents;

public class Trace {
/*  Trace corresponds to the path, the sequence of location visited by an agent.
    It allows us to track the progress of the exploration of each Agent.
*/

    //    int visitCounter; //for phase 2

    public Trace(Agent agent, Map map) {

        /* Initialize the Trace
            Trace surperposes to the map: has the same starting dimensions
         */
        double[][] Trace = [map.getWidth()][map.getWidth()];

        // The agent starts with No trace: so the matrix of visited place only contains zero
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                map[i][j] = 0;
            }
        }

    }//end of Trace constructor

    //trace update
    public void traceUpdate(Agent agent, Map map){
       Agent get
    }

}
