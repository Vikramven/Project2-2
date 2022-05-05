package phase2.RayCasting;

import phase2.Agent;

import java.util.ArrayList;

/*
 *       INFO CONTAINED IN THE REWARD TABLE
 */

    //0 = private int move = -1; move ? in connection to EM infos?
    //path correctness (?)
    //1 = private int wrongPath = -10;  random move with a low probability.
    //2 = private int correctPath = 10;

    //3 = private int death = -100; (?)

    //vision & hearing : sensorial informations
    //4 = private int visionOnIntruder = 100; for a guard !! because he wants to catch the other !
    // 5 = private int hearingOnIntruder = 20; <-- FOOT STEPS
    //6 = private int wall = Integer.MIN_VALUE;

    //end and start
    //7 = private int startPoint = -100
    // 8 = private int goal = 1000;

    //required from Markers
    //9= Trace survey along the stress
    // 10= Yell
    }

public class rewardTable {

    private Agent owner;

    private phase2.Map currentMap; // to access the rewards criterias info UML main link

    private ArrayList<Integer> dataSet = new ArrayList<>();

    /* METHOD (0): Default Constructor
    * */
    public rewardTable(Agent a){
        owner = a;
        initDataSet();
    }

    /* METHOD(1): intDataSet
     * initialize the basic structure of the reward table shared by all agent
     * */
    public void initDataSet() { // should we use GA to twcih it or start with random value between 1 and 1000 ?
        //move ? in connection to EM infos?
        for (int i = 0; i < dataSet.size(); i++)
            dataSet.set(1, 0);
    }

    /* HELPER METHOD(1): Reward
     * places the correct new value to the criteria
     * */
    public void Reward(int criteria, int value){
        int currentValue = dataSet.get(criteria);
        int UpdatedValue = currentValue + value;
        dataSet.set(criteria, UpdatedValue);
    }

    /* METHOD(3): updateDataSet
     * For every Agent on the map: one team after the other, currently only for guards
     * loops through tiles within the vision Area
     *  compute immediate environment info and compute associated reward
     * */
    public void updateDataSet() {
        //info access by this class from the map class !
        ArrayList<Agent> Guards = currentMap.getGuardsTeam().getTeam();// a pointer exploiting pre existing memeory space

        for (int i = 0; i < Guards.size(); i++) { /* each agent in List*/

            phase2.Agent agent = i;
            ArrayList<int[]> VisionArea = agent.getVisionArea();

            for( int j = 0; j < VisionArea.size(); j ++ ) { /*loop through tile of vision range */
                int [] loc = j;
                Agents.Tile currentTile = currentMap.getTile(loc[0],loc[1]);

                //(3) the agent is dead ?? makes no sence!
                if(agent.isDead() == true){
                    Reward(3,-100);
                }

                // (4) is another agent in vision Area
                int OtherAgent = visionOnAgent(i,currentTile);
                if( OtherAgent == 2) //Other Team detected
                    Reward(4,100);// are the value correct ?
                else if( OtherAgent == 1)
                    Reward(4,20); //i think the reward should be null

                //(5) hearingOnIntruder <-- foot steps
                //(6) wall
                if(currentTile.hasWall() == true)
                    Reward(6, Integer.MIN_VALUE);
            }
        }
    }//END of UpdateDataSet


    /* HELPER METHOD (2) :visionOnAgent
    * Returns an integer based on if and then what type on agent is visible in visionArea
    */
    public int visionOnAgent(Agent a, Agents.Tile t){
        if(t.hasAgent() == 0){
            return 0; //no agent found
        }
        else if( a.getTeam() == t.hasAgent() ){
            return 1; //same team
        }
        return 2; //opposite team
    }//END of visionOnAgent

}//end of class rewardTable
