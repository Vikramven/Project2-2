package Controller;

public class Shade extends Rectangle{
    /*
    I'm assuming shade works exactly like wall. Could be different i guess?
    interfaces:
    mapObj(I)
    -> rect (I)
        +wall C
        +shade C
    -> entrance (I)
        +door C (like a portal)
        +portal C
     */


    public Shade(int x1, int y1, int x3, int y3){
        super(x1,y1,x3,y3);
    }

}
