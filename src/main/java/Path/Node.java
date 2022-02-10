package Path;

import java.util.ArrayList;

public class Node {
    private Node parent;
    private ArrayList<Node> children;
    private int X;
    private int Y;

    public Node(Node parent, Node children){
        this.parent = parent;
        this.children = new ArrayList<>();
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
