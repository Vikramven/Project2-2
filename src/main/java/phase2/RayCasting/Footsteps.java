package phase2.RayCasting;

import phase2.Agent;
import phase2.Map;
import phase2.AgentTeam;

import java.util.ArrayList;

public class Footsteps {
    /**
     * principle, iterate through footstep range
     * top square coordinate x-f, y-f
     * if loop finds another agent, parse it through
     * from map class, get array of tiles, and loop through them
     */

    public Agent agent;
    public Map map;

    public Footsteps(float initialAngle, int startX, int startY){
        super(initialAngle,startX,startY);
    }


    private ArrayList<AgentTeam> list;
    //int loopXPosition=agent.getCurrentX();
    //int loopYPosition= agent.getCurrentY();
    int footstepRange=5;
    public void checkSteps(Map map){
        for(AgentTeam team : map.getListOfAllAgents()){
            for(Agent agent : team.getTeam()){
                int[] agentPosition = agent.getPosition();
                int agentX = agentPosition[0], agentY = agentPosition[1];
                int guardX = agent.getPosition()[0], guardY =agent.getPosition()[1];
                if(guardX-agentX<footstepRange && guardY-agentY<footstepRange){
                   // agent.
                }
            }
        }
    }

}
