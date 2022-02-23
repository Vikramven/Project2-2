package Path;

import Agents.Agent;
import Agents.Map;
import java.lang.Integer;
import java.util.*;

public class Move {
    // 8 possible moves: each is 45° angles
    //convertor: angle to director based on a range

    //Left: -90 ° = -45 x 2
public void leftMove(Agent agent){
    agent.setAgentPositionX(agent.getAgentPositionX()-1);
}
    //Right: +90° = +45 x 2
public void rightMove(Agent agent){
    agent.setAgentPositionX(agent.getAgentPositionX()+1);
}
    //Up: +0°
public void upMove(Agent agent){
    agent.setAgentPositionY(agent.getAgentPositionY()+1);
}
    //Down: -180° = -45 x 4
public void downMove(Agent agent){
    agent.setAgentPositionY(agent.getAgentPositionY()-1);
}
    //Diagonal left-Up: +45°
public void diagonalLeftUpMove(Agent agent){
    agent.setAgentPositionX(agent.getAgentPositionX()-1);
    agent.setAgentPositionY(agent.getAgentPositionY()+1);
}
    //Diagonal Right-Up: -45°
public void diagonalRightUpMove(Agent agent){
    agent.setAgentPositionX(agent.getAgentPositionX()+1);
    agent.setAgentPositionY(agent.getAgentPositionY()+1);
}
    //Diagonal left-Down: +225 = -45 x 5
public void diagonalLeftDownMove(Agent agent){
    agent.setAgentPositionX(agent.getAgentPositionX()-1);
    agent.setAgentPositionY(agent.getAgentPositionY()-1);
}
    //Diagonal Right-Down: +135° = +45 x 3
public void diagonalRightDownMove(Agent agent){
    agent.setAgentPositionX(agent.getAgentPositionX()+1);
    agent.setAgentPositionY(agent.getAgentPositionY()-1);
}

public boolean canMoveThere(Map map, int x, int y){
    /**
     * returns true if an agent can move to the (x,y) field
     * on the map that is passed to this method
     */

    if(map.getFieldCost(x,y)==Integer.MAX_VALUE){
        return false;
    }
    else{
        return true;
    }
}

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

    // Calculates the Manhattan distance
    public int manhattanHeuristic(int x, int y, int goalX, int goalY){
        return (x - goalX) + (y - goalY);
    }



   /* A_Star : Path Finding Algorithm
   * OUTPUT: an integer that describes how costy(beneficial) the potential Move is to the Agent
   * */
   public int aStar(Agent agent, Map map, int initialCost){
        int x = agent.getAgentPositionX();
        int y = agent.getAgentPositionY();

        int[] newGoal = goalUpdator(agent, map);

        /*What are the possible Moves to reach the Goal ?
            Candidate Paths
        */

       int goalDistance =  manhattanHeuristic(x, y, newGoal[0], newGoal[1]);

       return 0;
    }


    public Collection<Position> getPath(Agent agent, Map map, Position targetPos){

        HashMap<Position, Boolean> vis = new HashMap<>();
        HashMap<Position, Position> prev = new HashMap<>();

        List<Position> directions = new LinkedList<>();
        Queue<Position> q = new LinkedList<>();
        Position current = new Position(agent.getAgentPositionX(), agent.getAgentPositionY());
        q.add(current);
        vis.put(current, true);
        while(!q.isEmpty()){
            current = q.remove();
            if (current.equals(targetPos)){
                break;
            }else{
                for(Position node : current.getNeighbours(map)){
                    if(!vis.containsKey(node)){
                        q.add(node);
                        vis.put(node, true);
                        prev.put(node, current);
                    }
                }
            }
        }
        if (!current.samePos(targetPos)){
            //Finished
        }
        for(Position pos = targetPos; pos != null; pos = prev.get(pos)) {
            directions.add(pos);
        }
        Collections.reverse(directions);
        return directions;
    }



    class Position{

       int x;
       int y;

       public Position(int x, int y){
           this.x = x;
           this.y = y;
       }


       public float getDistance(){
            return 0;
       }

       public boolean samePos(Position pos){
           return this.x == pos.x && this.y == pos.y;
       }

       public Collection<Position> getNeighbours(Map map){

           List<Position> neighbours = new ArrayList<>();
           neighbours.add(new Position(this.x+1, this.y));
           neighbours.add(new Position(this.x+1, this.y-1));
           neighbours.add(new Position(this.x+1, this.y+1));
           neighbours.add(new Position(this.x, this.y-1));

           neighbours.add(new Position(this.x, this.y+1));
           neighbours.add(new Position(this.x-1, this.y+1));
           neighbours.add(new Position(this.x-1, this.y-1));
           neighbours.add(new Position(this.x-1, this.y));

           List<Position> delete = new ArrayList<>();
           for(Position pos : neighbours){
               if (map.getFieldCost(pos.x, pos.y) == Integer.MAX_VALUE){
                   delete.add(pos);
               }else if (pos.x > map.getMapWidth()+1 || pos.y > map.getMapHeight()+1){
                   delete.add(pos);
               }
               // check if position has a wall or is outside the map.
               // remove from neighboours if so
           }

           for (Position pos : delete){
               neighbours.remove(pos);
           }


           return neighbours;
       }



    }

//public int
//       while(/* Goal not reached, equivalent to Position of Agent != Goal Position */){
//        aStar(agent, map, initalCost);
//    }


   //D*
}
