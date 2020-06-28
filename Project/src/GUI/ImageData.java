package GUI;

import ImageManagment.Pixel;

import java.awt.*;
import java.util.Vector;

public class ImageData {
    private static ImageData instance;
    public Vector<Pixel> pixels;

    private ImageData(){
        pixels = new Vector<Pixel>();
    }

    public static ImageData getInstance(){
       if(instance==null)
           instance = new ImageData();
       return instance;
    }

    public void insertPixel(Color pColor,int xCoordinate, int yCoordinate){
        Pixel newPixel = new Pixel(pColor,xCoordinate, yCoordinate);
        pixels.add(newPixel);
    }

    public String getPixelsInfo(){
        String pixelString = "";
        for(Pixel p:pixels){
            pixelString += " X: " + Integer.toString(p.xCoordinate) +
                    " Y: " + Integer.toString(p.yCoordinate) +
                    " Color: " + Integer.toString(p.color.getRed()) +
                    " " + Integer.toString(p.color.getBlue()) +
                    " " + Integer.toString(p.color.getGreen()) + "\n";
        }
        return pixelString;
    }

}
