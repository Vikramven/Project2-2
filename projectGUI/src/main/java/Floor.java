import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Floor extends Box {

    PhongMaterial floorMaterial = new PhongMaterial();

    public Floor(double height, double width, double depth) {
        super(height, width, depth);

        //setMarbleTexture();
        setMaterialAsColor();

        this.setMaterial(floorMaterial);

        System.out.println("floorX: "+getTranslateX());
        System.out.println("floorY: "+getTranslateY());
    }

    private void setMaterialAsColor(){
        this.floorMaterial.setDiffuseColor(Color.BEIGE);
    }

    private void setMarbleTexture(){  // to fix  dont know whats wrong for now
        Image floorTexture = null;
        try {
            floorTexture = new Image(new FileInputStream("resource/floorTxture.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found dummy");
        }
        this.floorMaterial.setDiffuseMap(floorTexture);
    }
}
