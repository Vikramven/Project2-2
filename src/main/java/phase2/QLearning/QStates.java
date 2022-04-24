package phase2.QLearning;
import java.util.ArrayList;
import java.util.Random;

public class QStates {

    /* S STATE CLASS DESCRIPTION
    * OUT-CONNECTION: requires instances from Agent to compute their Q table
    * GOAL: A reinforcement learning method that uses heuristics to associate a grade to a (state; action) pair
    *       We iterate through t step to make the value converge.
    *
    * */
    private final double alpha=0.1;//learning rate, alpha
    private int[][] R;//rewards
    private double[][] Q;
    private int[][] EM;
    private int[][] IN; // instruction table TO BE DONE
    private final double gamma = 0.5;
    int currentState = 0;

    private boolean reachedGoal = false;


    //Table Updators
    public void EMupdate(Agent n){
        for(int i = 0; i < EM.length(); i++){
            //n.get;
        }
    }
    public void QUpdate(int[][] newQTable){

    }


    public int[] rewardTable = {-1,-10,10,-100,100,20,Integer.MIN_VALUE, -100, 1000};

/* STATIC REWARD TABLE

    We associate an arbitrary grade to a given situation

    0 = private int move = -1;
    1 = private int wrongPath = -10; // random move with a low probability.
    2 = private int correctPath = 10;
    3 = private int death = -100;
    4 = private int visionOnIntruder = 100;
    5 = private int hearingOnIntruder = 20;
    6 = private int wall = Integer.MIN_VALUE;
    7 = private int startPoint = -100
    8 = private int goal = 1000;

 */
    private int[][] maze;//init with width and height? is it computationally effective?
    int mazeWidth = 100;
    int mazeHeight = 200;
    private final int numberOfStates = mazeWidth * mazeHeight;
    private final int numberOfActions = 5;
    private int finalState=100;//change this, don't know if correct

    /**
     * necessary general methods
     * init- kind of for reading file
     * calculateQ
     * printQ
     *printPolicy
     * look up Q table
     * decide move;=> update the Agent position
     */

    public void init() {

        R = new int[numberOfStates][5]; // creation of the reward Table
        Q = new double[numberOfStates][5];// creation of the Q Table
        maze = new int[mazeHeight][mazeWidth]; // the whole map, copy of agent (.??.)


        for (int k = 0; k < numberOfStates; k++) {

            /**
             * initiate reward matrix with -1
             */

            for (int l = 0; l < numberOfActions; l++) {

                R[k][l] = -1; // everything value of -1; representing that each move costs -1, to ensure it takes the shortest path.

                //HOW TO RETRIEVE/SET FINAL STATE?
                //if not in the final state, or there is no wall ahead, move in all directions
                //can fill in later, but I will just fill this in for now
                if (!reachedGoal())
                    getpath();
            }
            initializeQ();
        }
    }


        /**
         * below methods for deciding states, possible actions from states
         */


        //Set Q values to R values
        public void initializeQ ()
        {
            for (int i = 0; i < numberOfStates; i++) {
                for (int j = 0; j < numberOfActions; j++) {

                    // read from map

                }
            }
        }

        public void lookupQTable(Agent a){
            /** return the best move for an agent
             * by looking for the max value
             *  Up down, left, right OR Turn left turn right
             */

        int agentPositionX = a.getCurrentX();
        int agentPositionY = a.getCurrentY();
        int currentMax = -100;// set as minimum when tile contains a wall

            int[] maxList = new int[4];
            //look up left
           maxList[0] = R[agentPositionX][agentPositionY - 1];
            //look right
            maxList[1] = R[agentPositionX][agentPositionY + 1];
            //look up Up
            maxList[2] = R[agentPositionX + 1][agentPositionY];
            //look up Down
            maxList[3] = R[agentPositionX - 1][agentPositionY];

            //in case no surrounding position is better than the current one: turn
            for (int i = 0; i < maxList.length; i++){
                if(currentMax < maxList[i]){
                    currentMax = maxList[i];//
                }
                if(currentMax > -1000){
                    //update
                   // a.move();
                //else
                //call turn + or minus : TO DO evaluate the best half average left > average right ?

        }
    /**
     * method for running number of training cycles
     * inital 1000
     */
    public void calculateQValues(){
        Random randomValue = new Random();

    }

    public boolean decideFinalState(int state){
        int i = state / mazeWidth;
        int j = state - i * mazeWidth;

        return maze[i][j]=='F';
    }
    /**
     * define list of next states the agent can turn to, can only exist if value is !=1
     * tracks index of states that can be reached
     */
    public int[] listOfPossibleStates(int state){
        ArrayList<Integer> possibleStates=new ArrayList<>();
        for (int i = 0; i < numberOfStates; i++) {
            if(R[state][i]!=-1){//use tile to refactor this
                possibleStates.add(i);
            }
        }
        return possibleStates.stream().mapToInt(i -> i).toArray();
    }

    double maxQvalues(int nextState){
        int[] actionsFromState = listOfPossibleStates(nextState);
        double maxValue=-10;//resetting later, set initial as -10 according to our model
        for(int action:actionsFromState){
            double value=Q[nextState][action];

            if(value>maxValue){
                maxValue=value;
            }
        }
        return maxValue;

    }

    /**
     * tester
     */
    void printQValues(){
        System.out.println("Q matrix values");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("From state " + i + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                System.out.println((Q[i][j]));
            }
            System.out.println();
        }
    }

}



