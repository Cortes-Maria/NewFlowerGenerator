package GUI;

import ImageManagment.FlowerImage;
import ImageManagment.Pixel;
import java.awt.*;
import java.util.Vector;

public class ImageData {
    private static ImageData instance;
    public Vector<FlowerImage> flowerImages;

    private ImageData(){
        flowerImages = new Vector<FlowerImage>();
    }

    public static ImageData getInstance(){
       if(instance==null)
           instance = new ImageData();
       return instance;
    }

    public void insertPixel(Color pColor,int xCoordinate, int yCoordinate){
        Pixel newPixel = new Pixel(pColor,xCoordinate, yCoordinate);
    }


}
