package newShit.QLearning;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import Controller.Variables;

public class QStates {
    private final double alpha=0.1;//learning rate, alpha
    private int[][] R;//rewards
    private double[][] Q;
    private int[][] EM;
    private int[][] IN;
    private final double gamma = 0.5;
    int currentState = 0;

    private boolean reachedGoal = false;


    public int[] rewardTable = {-1,-10,10,-100,100,20,Integer.MIN_VALUE, -100, 1000};
/*
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


    /**
     * how do i init the maze? create new one or read in?
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
     */

    public void init(){

        R = new int[numberOfStates][5];
        Q = new double[numberOfStates][5];
        maze = new int[mazeHeight][mazeWidth];


        for (int k = 0; k < numberOfStates; k++) {

            /**
             * initiate reward matrix with -1
             */

            for (int l = 0; l < numberOfActions; l++) {

                R[k][l]=-1; // everything value of -1; representing that each move costs -1, to ensure it takes the shortest path.

            //HOW TO RETRIEVE/SET FINAL STATE?
            //if not in the final state, or there is no wall ahead, move in all directions
            //can fill in later, but I will just fill this in for now
            if(!reachedGoal) {


                /**
                 * if it's not final state,which direction to go?
                 *assume left initially
                 * possible to call this evaluation function?
                 * formula for goal updater - index * width + location of move
                 */
                //if else for moving left

                }
            }
            }
        initializeQ();
        }


    /**
     * below methods for deciding states, possible actions from states
     */


    //Set Q values to R values
    public void initializeQ()
    {
        for (int i = 0; i < numberOfStates; i++){
            for(int j = 0; j < numberOfActions; j++){

                // read from map

            }
        }
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
            if(R[state][i]!=-1){
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



