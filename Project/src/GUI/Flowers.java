package GUI;

import ImageManagment.FlowerImage;

import java.util.Vector;

public class Flowers {
    private static Flowers instance;
    public Vector<FlowerImage> flowerImages;

    private Flowers(){
        flowerImages = new Vector<FlowerImage>();
    }

    public static Flowers getInstance(){
       if(instance==null)
           instance = new Flowers();
       return instance;
    }

    public void addFlowerImage(FlowerImage flowerImage){
        flowerImage.extractPixels();
        flowerImages.add(flowerImage);
    }
}
