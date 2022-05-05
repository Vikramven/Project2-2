package phase2;

public class Guard extends Agent{

    private static final int YELL_RADIUS = 180;

    public Guard(float initialAngle, int startX, int startY){
            super(initialAngle,startX,startY);
    }

    public void yell(Map map){
        for(AgentTeam team : map.getListOfAllAgents()){
            for(Agent agent : team.getTeam()){
                int[] agentPosition = agent.getPosition();
                int agentX = agentPosition[0], agentY = agentPosition[1];
                int guardX = this.getPosition()[0], guardY = this.getPosition()[1];
                if( guardX - YELL_RADIUS <= agentX && agentY <= guardX + YELL_RADIUS
                        && guardY - YELL_RADIUS <= agentY && agentY <= guardY + YELL_RADIUS){
                    agent.hearsYell(agentPosition);
                }
            }
        }
    }
}
