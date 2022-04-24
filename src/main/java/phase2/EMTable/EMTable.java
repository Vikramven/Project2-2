package phase2.EMTable;


import phase2.Position;
import java.util.HashMap;

public class EMTable {

        HashMap<Position, Integer> emTable = new HashMap<>();
        HashMap<Position, Position> instructions = new HashMap<>();


        Position startPosition;
        Position previousPosition;

        public EMTable(Position startPos){
            this.startPosition = startPos;
            this.previousPosition = startPos;
        }



        public void updateEMtable(int step, Position currentPos, int[][] map){

            // We need the QTable here as a parameter

            //if episode <= max episode then -- check if we need this
            //Position startPos = path.get(i);
            //Position currentPos = path.get(i);

            if (!previousPosition.samePos(currentPos)){
                if(!instructions.containsKey(previousPosition)){
                    instructions.put(previousPosition, currentPos);
                }
            }


            //while (path.indexOf(currentPos) < path.size()){
                if (!emTable.containsKey(currentPos)){

                    //step - to be provided from QTable
                    emTable.put(currentPos, step); //nice to test with euclidean distance

                }else{
                    if(step < emTable.get(currentPos)){
                        emTable.put(currentPos, step);
                    }
                }

                // add reachable state nodes into Q table
                for (Position neighbour : currentPos.getNeighbours(map)){
                    if (!emTable.containsKey(neighbour)){
                        //step - to be provided from QTable
                        emTable.put(neighbour, step+1); //nice to test with euclidean distance
                        //instructions.put(neighbour, currentPos);

                    }else{
                        if(step < emTable.get(neighbour)){
                            emTable.put(neighbour, step+1);
                            //instructions.put(neighbour, currentPos);
                        }
                    }
                }

                previousPosition = currentPos;

                // choose a_t using policy derived from Q table

                // execute action a_t, observe R(s_t, a_t) and s_(t+1)
                // action a_t in state s_t at time t, it would get immediate reward R(s_t , a_t )


                //update Q(s, a) by using Bellman Equation


                //currentPos = path.get(i);
                //Position newPos = path.get(i);

            //}






        }









}
