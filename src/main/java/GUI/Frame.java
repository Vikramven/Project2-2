package GUI;

import javafx.scene.layout.BorderPane;


public class Frame extends BorderPane{
    /**
     * construtor that will create the object, with basic parameters
     */
    public Frame(int width, int height){
        Render renderer= new Render();
        this.setCenter(renderer);

    }
}