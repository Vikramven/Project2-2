package newShit.RayCasting;

import java.util.LinkedList;
import java.util.Vector;

import static java.lang.Math.*;

public class RayCasting {

    int x;
    int y;

    Cell[] world;
    LinkedList<Edge> edges = new LinkedList<>();

  public RayCasting(int mapX, int mapY){
      this.x = mapX;
      this.y = mapY;
      world = new Cell[this.x*this.y];
      System.out.println(world.length);
      // Add a boundary to the world

  }


  public void convertToRayCastingMap(int startX, int startY, int width, int height, int blockWidth, int pitch){

      edges.clear();
      int count = 0;

      for (int i = 0; i < width; i++) {
          for (int j = 0; j < height; j++) {
              for (int k = 0; k < 4; k++) {
                  int i1 = (j + startY) * pitch + (i + startX);
                  world[i1].edgeExists[k] = false;
                  world[i1].edgeId[k] = 0;
                  count++;
                  System.out.println(i1);
                  System.out.println(count);
              }
          }
      }

      for (int i = 1; i < width-1; i++) {
          for (int j = 1; j < height-1; j++) {

              int currentCellIndex = (j + startY) * pitch + (i + startX);			// This
              int n = (j + startY - 1) * pitch + (i + startX);		// Northern Neighbour
              int s = (j + startY + 1) * pitch + (i + startX);		// Southern Neighbour
              int w = (j + startY) * pitch + (i + startX - 1);	// Western Neighbour
              int e = (j + startY) * pitch + (i + startX + 1);	// Eastern Neighbour

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