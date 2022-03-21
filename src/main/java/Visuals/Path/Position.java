package Visuals.Path;

import Visuals.Agents.Map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Position {
    int x;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
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



            // check if position has a wall or is outside the map.
            // remove from neighboours if so
        }

        for (Position pos : delete){
            neighbours.remove(pos);
        }


        return neighbours;
    }

    public Collection<Position> getNeighbours(int[][] map){

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

            if (pos.x < 0 || pos.y < 0 || pos.x > map.length || pos.y > map[0].length){
                delete.add(pos);
            }else{
                if (map[pos.x][pos.y] == 1){
                    delete.add(pos);
                }
            }



            // check if position has a wall or is outside the map.
            // remove from neighboours if so
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

    public double euclideanDistance(Position targetPos){
        return Math.sqrt(Math.pow(targetPos.y-this.y, 2) + Math.pow(targetPos.x-this.x, 2));
    }

    public double manhattanDistance(Position targetPos){
        return Math.abs(targetPos.y-this.y) + Math.abs(targetPos.x-this.x);
    }

    public static double euclideanDistanceStatic(Position fromPos, Position targetPos){
        return Math.sqrt(Math.pow(targetPos.y-fromPos.y, 2) + Math.pow(targetPos.x-fromPos.x, 2));
    }

    public static double manhattanDistanceStatic(Position fromPos, Position targetPos){
        return Math.abs(targetPos.y-fromPos.y) + Math.abs(targetPos.x-fromPos.x);
    }
}
