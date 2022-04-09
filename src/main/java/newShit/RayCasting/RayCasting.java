package newShit.RayCasting;

import java.util.LinkedList;
import java.util.Vector;

import static java.lang.Math.*;

public class RayCasting {

    int x = 50;
    int y = 30;

    Cell[] world;
    LinkedList<Edge> edges = new LinkedList<>();

  public RayCasting(){

      world = new Cell[x*y];


  }

  public void convertToRayCastingMap(int startX, int startY, int width, int height, int blockWidth, int pitch){

      edges.clear();

      for (int i = 0; i < width; i++) {
          for (int j = 0; j < height; j++) {
              for (int k = 0; k < 4; k++) {
                  world[(y+startY)*pitch + (x+startX)].edgeExists[j] = false;
                  world[(y+startY)*pitch + (x+startX)].edgeId[j] = 0;
              }
          }
      }

      for (int i = 0; i < width-1; i++) {
          for (int j = 0; j < height-1; j++) {

              int currentCellIndex = (y + startY) * pitch + (x + startX);			// This
              int n = (y + startY - 1) * pitch + (x + startX);		// Northern Neighbour
              int s = (y + startY + 1) * pitch + (x + startX);		// Southern Neighbour
              int w = (y + startY) * pitch + (x + startX - 1);	// Western Neighbour
              int e = (y + startY) * pitch + (x + startX + 1);	// Eastern Neighbour

              // North = 0 | South = 1 | East = 2 | West = 3|

              if(world[currentCellIndex].exists){
                  if(!world[w].exists){
                      if (world[n].edgeExists[3]){
                          float p = edges.get(world[n].edgeId[3]).getEndY();
                          edges.get(world[n].edgeId[3]).setEndY(p+blockWidth);
                          world[currentCellIndex].edgeId[3] = world[n].edgeId[3];
                          world[currentCellIndex].edgeExists[3] = true;
                      }
                      else
                      {
                          // Northern neighbour does not have one, so create one
                          Edge edge = new Edge();
                          float sX = edges.get(world[n].edgeId[3]).getStartX();
                          edges.get(world[n].edgeId[3]).setStartX((sX+i)*blockWidth);
                          float sY = edges.get(world[n].edgeId[3]).getStartY();
                          edges.get(world[n].edgeId[3]).setStartY((sY+j)*blockWidth);
                          float eX = edges.get(world[n].edgeId[3]).getEndX();
                          edges.get(world[n].edgeId[3]).setEndX(eX);
                          float eY = edges.get(world[n].edgeId[3]).getEndY();
                          edges.get(world[n].edgeId[3]).setEndY(eY+blockWidth);

                          // Add edge to Polygon Pool
                          int edge_id = edges.size();
                          edges.add(edge);

                          // Update tile information with edge information
                          world[currentCellIndex].edgeId[3] = edge_id;
                          world[currentCellIndex].edgeExists[3] = true;
                      }
                  }

                  if(!world[e].exists){
                      if (world[n].edgeExists[2]){
                          float p = edges.get(world[n].edgeId[2]).getEndY();
                          edges.get(world[n].edgeId[2]).setEndY(p+blockWidth);
                          world[currentCellIndex].edgeId[2] = world[n].edgeId[2];
                          world[currentCellIndex].edgeExists[2] = true;
                      }
                      else
                      {
                          // Northern neighbour does not have one, so create one
                          Edge edge = new Edge();
                          float sX = edges.get(world[n].edgeId[2]).getStartX();
                          edges.get(world[n].edgeId[2]).setStartX((sX+i+1)*blockWidth);
                          float sY = edges.get(world[n].edgeId[2]).getStartY();
                          edges.get(world[n].edgeId[2]).setStartY((sY+j)*blockWidth);
                          float eX = edges.get(world[n].edgeId[2]).getEndX();
                          edges.get(world[n].edgeId[2]).setEndX(eX);
                          float eY = edges.get(world[n].edgeId[2]).getEndY();
                          edges.get(world[n].edgeId[2]).setEndY(eY+blockWidth);

                          // Add edge to Polygon Pool
                          int edge_id = edges.size();
                          edges.add(edge);

                          // Update tile information with edge information
                          world[currentCellIndex].edgeId[2] = edge_id;
                          world[currentCellIndex].edgeExists[2] = true;
                      }
                  }

                  if(!world[n].exists){
                      if (world[w].edgeExists[0]){
                          float p = edges.get(world[w].edgeId[0]).getEndX();
                          edges.get(world[w].edgeId[0]).setEndX(p+blockWidth);
                          world[currentCellIndex].edgeId[0] = world[n].edgeId[0];
                          world[currentCellIndex].edgeExists[0] = true;
                      }
                      else
                      {
                          // Northern neighbour does not have one, so create one
                          Edge edge = new Edge();
                          float sX = edges.get(world[w].edgeId[0]).getStartX();
                          edges.get(world[n].edgeId[0]).setStartX((sX+i)*blockWidth);
                          float sY = edges.get(world[w].edgeId[0]).getStartY();
                          edges.get(world[n].edgeId[0]).setStartY((sY+j)*blockWidth);
                          float eX = edges.get(world[w].edgeId[0]).getEndX();
                          edges.get(world[n].edgeId[0]).setEndX(eX+blockWidth);
                          float eY = edges.get(world[w].edgeId[0]).getEndY();
                          edges.get(world[n].edgeId[0]).setEndY(eY);

                          // Add edge to Polygon Pool
                          int edge_id = edges.size();
                          edges.add(edge);

                          // Update tile information with edge information
                          world[currentCellIndex].edgeId[0] = edge_id;
                          world[currentCellIndex].edgeExists[0] = true;
                      }
                  }

                  if(!world[s].exists){
                      if (world[w].edgeExists[1]){
                          float p = edges.get(world[w].edgeId[1]).getEndX();
                          edges.get(world[w].edgeId[1]).setEndX(p+blockWidth);
                          world[currentCellIndex].edgeId[1] = world[w].edgeId[1];
                          world[currentCellIndex].edgeExists[1] = true;
                      }
                      else
                      {
                          // Northern neighbour does not have one, so create one
                          Edge edge = new Edge();
                          float sX = edges.get(world[w].edgeId[1]).getStartX();
                          edges.get(world[n].edgeId[1]).setStartX((sX+i)*blockWidth);
                          float sY = edges.get(world[w].edgeId[1]).getStartY();
                          edges.get(world[n].edgeId[1]).setStartY((sY+j+1)*blockWidth);
                          float eX = edges.get(world[w].edgeId[1]).getEndX();
                          edges.get(world[n].edgeId[1]).setEndX(eX+blockWidth);
                          float eY = edges.get(world[w].edgeId[1]).getEndY();
                          edges.get(world[n].edgeId[1]).setEndY(eY);

                          // Add edge to Polygon Pool
                          int edge_id = edges.size();
                          edges.add(edge);

                          // Update tile information with edge information
                          world[currentCellIndex].edgeId[1] = edge_id;
                          world[currentCellIndex].edgeExists[1] = true;
                      }
                  }
              }
          }
      }
  }
}