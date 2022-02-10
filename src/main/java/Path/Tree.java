package Path;

import Agents.Map;

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
/* METHOD: Priority List
    We rank the above Travel cost from min to max
     We use a sorting sort algorithm : #bubble sort?
 */
    public void priorityList

}



