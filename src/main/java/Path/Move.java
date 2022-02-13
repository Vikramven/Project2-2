package Path;

import Agents.Agent;
import Agents.Map;
import java.util.*;

public class Move {
    // 8 possible moves: each is 45° angles
    //convertor: angle to director based on a range


    //Left: -90 ° = -45 x 2
public int[] leftMove(){}
    //Right: +90° = +45 x 2

public int[] rightMove(){}
    //Up: +0°
public int[] upMove(){}
    //Down: -180° = -45 x 4
public int[]downMove(){}
    //Diagonal left-Up: +45°
public int[] diagonalLeftUpMove(){}
    //Diagonal Right-Up: -45°
public int[] diagonalRightUpMove(){}
    //Diagonal left-Down: +225 = -45 x 5
public int[] diagonalLeftDownMove(){}
    //Diagonal Right-Down: +135° = +45 x 3
public int[] diagonalRightDownMove(){}


    public  ArrayList <int[]> legalMoveGenerator(Agent agent, Map map){

    //call all the above methods

    //check for validity;
    // if output coordonates == MAX_VALUE
    //if another agent is currently on this position

    //return the list of possible tile coordonates to visit


    }

       /* HELPER METHOD FOR A_STAR:
        if we make 1 move in direction of Goal
                the cost of the Move
                        Empty_Cell is 1 : later we will discriminate on
                        Wall is positive_infinity (Integer.MAX_VALUE)

                the remaining distance to Local_Goal
                        Map_Limit
                        TeamMate_Trace
                        Opponent_Trace
        */




    public int[] goalUpdator(Agent agent, Map map) {
    /* Sort the option from most to less costly
        Need to sort the distance from AgentPosition to all know Map Limits
        Need to sort the distance from AgentPosition to all know Traces
     Outputs the Minimum of both
     */


    }

    public int manhattanHeuristic(int x, int y, int goalX, int goalY){
        return (x - goalX) + (y - goalY);
    }



   /* A_Star : Path Finding Algorithm
   * OUTPUT: an integer that describes how costy(beneficial) the potencialMove is to the Agent
   * */
   public int aStar(Agent agent, Map map, int initialCost){
        int x = agent.getAgentXPosition();
        int y = agent.getAgentYPosition();

        int[] newGoal = goalUpdator(agent, map);

        /*What are the possible Moves to reach the Goal ?
            Candidate Paths
        */

       int goalDistance =  manhattanHeuristic(x, y, newGoal[0], newGoal[1]);

       return 0;
    }

//public int
//       while(/* Goal not reached, equivalent to Position of Agent != Goal Position */){
//        aStar(agent, map, initalCost);
//    }


   //D*
}
