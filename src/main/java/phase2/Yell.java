package phase2;
/* TO DO
 *   CONNECT TO ACCESS IN TILE FOR THIS AGENT to source and update information
 *   Finish writing the method below in connection with the reward table criterias
 *
 *      WONT LAST IN TIME SO : NO NEED TO STORE => ONLY EXIST AS A CONVENIENT INFORMATION PACKAGE
 *
 * */
public class Yell {

    /*  MARKER 2: Waggle dance
     *       2.1 Type: real time update immediate information in time and space
     *       2.2 Inspiration <-- the bees communication strategy
     *       2.3 Limits <--  within visual range
     *       2.4 Encoded information <-- ANGLE of threat direction, INTENSITY distance to threat or objective
     */

    private int angle;
    private int dObjective;
    private Agent agent;
    public Yell(Agent a){//CONSTRUCTOR
        agent = a;    //STAYS EMPTY
    }

    /* METHOD 1: UPDATE DANCE . MOST IMPORTANT BECAUSE ONLY CALL INSIDE OF AGENT
     *   Ensures that the agent current position is added to the Trace
     */
    public void UpdateTrace(Agent a){

    }
}
