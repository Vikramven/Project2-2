package Path;

import Agents.Map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Position {
    int x;
    int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

            if (pos.x < 0 || pos.y < 0 || pos.x >= map.getMapWidth() || pos.y >= map.getMapHeight()){
                delete.add(pos);
            }else{
                if (map.getFieldCost(pos.x, pos.y) == Integer.MAX_VALUE){
                    delete.add(pos);
                }

            }
        }

        for (Position pos : delete){
            neighbours.remove(pos);
        }


        return neighbours;
    }


    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}