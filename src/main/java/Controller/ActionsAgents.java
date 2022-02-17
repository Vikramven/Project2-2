package Controller;
import Agents.Agent;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class ActionsAgents implements Agent {
    protected final double MAX_WALK = 5;
    protected final double MAX_RUN = 10;

    protected Vector position;
    protected Vector direction;
    protected double radius;
    protected ArrayList<Ray> view;

    public ActionsAgents(Vector position, Vector direction, double radius)
    {
        this.direction = direction;
        this.position = position;
        this.radius = radius;
        view = new ArrayList<>();
    }


    @Override
    public Vector move() {
        double x = Math.random() * MAX_WALK;
        if(Math.random() > 0.5)
            x = x * -1;
        double y = Math.random() * MAX_WALK;
        if(Math.random() > 0.5)
            y = y * -1;

        return new Vector(x, y);
    }

    @Override
    public Vector getDirection() {
        return direction;
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public double getHearing() {
        return 0;
    }

    @Override
    public void updateLocation(Vector endPosition) {
        position=endPosition;
    }

    @Override
    public void updateView(ArrayList<Ray> view) {
        this.view=view;
    }
    public void draw(GraphicsContext g)
    {
        g.setFill(Color.BLACK);
        g.fillOval(position.getX()-(radius/2), position.getY()-(radius/2), radius, radius);
    }


    public boolean isHit(Ray ray)
    {
        return false;
    }

    public Vector intersection(Ray ray)
    {
        return null;
    }
    public boolean validMove(Vector startPoint, Vector endPoint) {
        return false;
    }

}
