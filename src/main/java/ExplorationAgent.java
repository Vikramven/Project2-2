import java.util.ArrayList;

public class ExplorationAgent {
    /**
     * Considering intruder as exploration agent
     * SET speeds that are too fast, too slow, and use degree turns based on that.
     * declare directions, L,R,U,D?
     */
    char direction;
    char currentDirection;
    char desiredDirection;
    double baseSpeed;
    double sprintSpeed;
    double turningDegree;

    /**
     * contains the list of positions it is allowed to move to.
     */
    ArrayList<Move> allLegalMoves;

    /**
     * store current and previous locations
     */
    int x;int y;
    int lastX,lastY;

    boolean teleport;

    boolean notMoving;

    /**
     * Constructor which sets the initial positions
     * set initial directions as random, R?
     */
    public ExplorationAgent(){
        this.x=x;
        this.y=y;
        this.lastX=x;
        this.lastY=y;
        currentDirection='L';
        desiredDirection='L';

    }
}
