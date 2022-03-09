package Visuals.Path;

import Visuals.Agents.Map;
import Visuals.Agents.Agent;
import java.util.*;

public class Tree {
    // recursively creating a Tree from a root
    //this tree corresponds to a path

    public Node root;
    public Move move = new Move();

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return this.root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    /* METHOD: adjacency Matrix for Travel Cost
     *   We build an adjacency matrix that contains the manhattan heuristic distance cost
     * from the CurrentNode (start) to any other tile, position of the Map
     * */
    public void adjacencyMatrixMap(Node currentNode, Map map /*containing walls*/) {
        for (int i = 0; i < map.getMapWidth(); i++) {
            for (int j = 0; j < map.getMapHeight(); j++) {
                //If contains a wall: we do not update the cost
                if (map.getMatrix()[i][j] < Integer.MAX_VALUE) {
                    map.getMatrix()[i][j] = move.manhattanHeuristic(i, j, currentNode.getX(), currentNode.getY()); //check if correct coordinates
                }
            }

        }
    }
/* METHOD: Sorting
    We rank the above Travel cost from min to max
     We use a sorting sort algorithm : #bubble sort?
 */



    public void priorityList(Agent agent, Map map) {
        //get the starting position
        int agentPositionX = agent.getAgentPositionX();
        int agentPositionY = agent.getAgentPositionY();
        Node parent = new Node(null, null );

        int legalMovesSize = move.legalMoveGenerator(agent, map).size();

        int[][] agentGoal = agent.getAgentGoal();
        agentGoal[0] = agentGoal[0];
        agentGoal[1] = agentGoal[1];

        //Create the exploration structure
        ArrayList<ArrayList<Node>> visitedNodes = new ArrayList<ArrayList<Node>>();
        int[][] exploration = new int[1][1];
//        exploration[0] = agentPosition [0];
//        exploration[1] = agentPosition [1];


        while (exploration != agentGoal) {

            //create the initial branches from start position
            for(int i = 0; i < legalMovesSize ; i++){
                //create the Nodes that extend the starting Position
                Node newNode = new Node(parent, null);

                for(int j = 0; j < 2 ; j++){ //dimension of the move.array storing the coordinates
                   //set the correct coordinates of the above created Node
                    //newNode.setPosition(move.legalMoveGenerator(agent, map).get(i)[j]);
                }
                //adding the created Node to the visitedArray
                visitedNodes.get(0).get(i).addChild(newNode);
            }

            //recursively Expands each path stored in the ArrayList
            //changing the zero index so it goes deeper until the goal is reached

            //Conditions for the recursion :
            //          * Jumping from a branch to another
            //          ** updating the exploration
            //          ***

            //  Keep track of current Minimal Cost
            //  If CurrentCost is higher than Minimal cost
            //  Expand the next best path
            //          :> next position in ArrayList for next expanding depth?!

        }
    }
}



