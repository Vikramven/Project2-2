package phase2;
/* TO DO
 *   CONNECT TO ACCESS IN TILE FOR THIS AGENT to source and update information
 *   Finish writing the method below in connection with the reward table criterias
 * */
public class Trace {

    /* MARKER 1 : Trace aka pheromons
     *       1.0 : WHAT cost is associated (?) should be very tiny or cost free: have a simple implementation
     *       1.1 Type: historical past time information, long distance
     *       1.2  Inspiration <-- the ants communication strategy
     *       1.3 Limits <-- disappears after 10 time steps (EXP PARAMETER)
     *       1.4 Encoded information <-- level alert INTENSITY
     *               level 1. GREEN harvest (add stress aka stimuli )
     *               level 2. ORANGE <-- could need support (potential not yet critical) <-- attract a little
     *               level 3. RED <-- need support <-- attract a lot
     *
     * Remarks: if more than 1 agent leaves a trace on a tile,
     *          then the trace with Highest trace level overwrittes the Tile.
     */

    /* INSTANCE 1: lifeTime
     *   Should be proportional to length so that they evolve in parallel
     *   Should be longer that the vision range to entail this longer distance communication purpose
     */
    private int lifeTime = 10;

    /* INSTANCE 2: length
     * Could be depending on the size of the map
     *  has to grow until this, starts at zero then from 10 to infinite gotta repaint it
     */
    private int length = 10;

    private int TracePath[][] = new int[length][2];//INSTANCE 3:  an  array of integer array
    private int counter = 0;// INSTANCE 4: current position to be filled
    private int stress; //INSTANCE 5: between 1 and 3: green, orange, red alert level
    private Agent agent; //INSTANCE 6: owner/producer

    public Trace(Agent a){//DEFAULT CONSTRUCTOR
        agent = a;
    }

    //HELPER METHODS to modify private instances
    public void increaseCounter(){
        counter++;
    }
    public void resetCounter(){
        if(counter == length){
            counter = 0;
        }// next time step the oldest trace will be Overriden
    }
    public void UpdateLifeTime(){
        lifeTime --;
        //outside loop : make sure to empty the memory space associated to the Trace when its lifeTime becomes zero
    }

    /* METHOD 1:  ALERT LEVEL
     *   Compute the agent's stress based on environmental information such as
     *   * The number of opponent within vision range
     *   * The average distance of the opponent to the Agent
     *           NB: larger d <-- reduces stress for an intruder
     *               larger d <-- increases stress for a guard
     * */
    public int AlertLevel(){
        //no agent detected
        return 0;
        //at least 1 agent from other team detected
        return 1;
        //more than 1 agent of the team detected /OR/ very large d to opponent
        return 2;
    }

    /* METHOD 2: UPDATE TRACE . MOST IMPORTANT BECAUSE ONLY CALL INSIDE OF AGENT
     *   Ensures that the agent current position is added to the Trace
     */
    public void UpdateTrace(Agent a){
        TracePath[counter][0] = a.getCurrentX();
        TracePath[counter][1] = a.getCurrentY();
        // TO DISCUSS: OR SIMPLY fill in the corresponding Tile ?

        //Extract then derive information form the Tile where the Agent is located
        stress = AlertLevel();

        //update main parameters for next time step
        increaseCounter();
        resetCounter();
    }
}
