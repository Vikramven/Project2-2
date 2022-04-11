package newShit.QLearning;
import java.io.File;
import java.util.Random;

import Controller.Variables;

public class QStates {
    private Variables v;
    private final double alpha=0.1;//learning rate, alpha
    private int[][] R;//rewards
    private double[][] Q;


    private final int reward=-1;//for every move
    private final int penalty=-10;//it's not a state, so when agent moves incorrectly
    /**
     * how do i init the maze? create new one or read in?
     */
    private char[][] maze;//init with width and height? is it computationally effective?
    int mazeWidth= v.getWidth();
    int mazeHeight=v.getHeight();
    private final int numberOfStates=mazeWidth*mazeHeight;
    private int finalState=100;//change this, don't know if correct

    /**
     * necessary general methods
     * init- kind of for reading file
     * calculateQ
     * printQ
     *printPolicy
     */

    public void init(){
        File file=new File("recources/testmap.txt");

        R=new int[numberOfStates][numberOfStates];
        Q=new double[numberOfStates][numberOfStates];
        maze=new char[mazeHeight][mazeWidth];

        for (int k = 0; k < numberOfStates; k++) {
            int i=k/mazeWidth;
            int j=k-i*mazeWidth;
            /**
             * initiate reward matrix with -1
             */
            for (int l = 0; l < numberOfStates; l++) {
                R[k][l]=-1;
            }
            //HOW TO RETTRIEVE/SET FINAL STATE?
            //if not in the final state, or there is no wall ahead, move in all directions
            //can fill in later, but I will just fill this in for now
            if(maze[i][j]!=finalState){
                /**
                 * if it's not final state,which direction to go?
                 *assume left initially
                 * possible to call this evaluation function?
                 */
                //if else for moving left
                int moveLeft=j-1;
                if(moveLeft>=0){
                    int goal=i*mazeWidth+moveLeft;
                    if (maze[i][moveLeft] == '0') {
                        R[k][goal] = 0;
                    } else if (maze[i][moveLeft] == 'F') {
                        R[k][goal] = reward;
                    } else {
                        R[k][goal] = penalty;
                    }
                }
                //if else for moving right
                int moveRight=j+1;
                if(moveRight>=0){
                    int goal=i*mazeWidth+moveRight;
                    if (maze[i][moveRight] == '0') {
                        R[k][goal] = 0;
                    } else if (maze[i][moveLeft] == 'F') {
                        R[k][goal] = reward;
                    } else {
                        R[k][goal] = penalty;
                    }
                }
                //if else for moving up
                int moveup=i-1;
                if(moveup>=0){
                    int goal=i*mazeWidth+moveup;
                    if (maze[i][moveup] == '0') {
                        R[k][goal] = 0;
                    } else if (maze[i][moveup] == 'F') {
                        R[k][goal] = reward;
                    } else {
                        R[k][goal] = penalty;
                    }
                }
                //if else for moving down
                int moveDown=j-1;
                if(moveDown>=0){
                    int goal=i*mazeWidth+moveDown;
                    if (maze[i][moveDown] == '0') {
                        R[k][goal] = 0;
                    } else if (maze[i][moveDown] == 'F') {
                        R[k][goal] = reward;
                    } else {
                        R[k][goal] = penalty;
                    }
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
            for(int j = 0; j < numberOfStates; j++){
                Q[i][j] = (double)R[i][j];
            }
        }
    }

    /**
     * method for running number of training cycles
     * inital 1000
     */
    public void calculateQValues{
        Random randomVelue=new Random();

        for (int i = 0; i <1000 ; i++) {

        }
    }

    public boolean decidefinalState(int state){
        int i = state / mazeWidth;
        int j = state - i * mazeWidth;

        return maze[i][j]=='F';
    }
    /**
     * define list of next states the agent can turn to, can only exist if value is !--1
     */
    }

