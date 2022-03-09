package Visuals.Path;

import java.util.ArrayList;

public class Node {
    private Node parent;
    private ArrayList<Node> children;
    private int X;
    private int Y;
    private int [] positionMap = new int [2];

    public Node(Node parent, Node children){
        this.parent = parent;
        this.children = new ArrayList<>();
    }
    public void setPosition(int[] positionMap){
        positionMap [0] = positionMap [0] ;
        positionMap [1] = positionMap [1] ;
    }

    public Node getParent(){return parent;}

    public ArrayList<Node> getChildren(){return children;}

    public void addChild(Node newChild){
        children.add(newChild);
    }

    public Node getChild(){
        return(children.get(children.size() - 1));
    }

    public Node getChild(int index){
        return children.get(index);
    }

    public int getX(){return X;};
    public int getY(){return Y;};

}
