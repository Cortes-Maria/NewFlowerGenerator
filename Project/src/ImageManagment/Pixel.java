package ImageManagment;

import java.awt.*;

public class Pixel {
    public int xCoordinate;
    public int yCoordinate;
    public Color color;

    public Pixel(){
        xCoordinate = yCoordinate = 0;
        color = null;
    }
    public Pixel(Color pColor, int pXCoodinate, int pYCoordinate){
        xCoordinate = pXCoodinate;
        yCoordinate = pYCoordinate;
        color = pColor;
    }
    public void printPixel(){
        System.out.println("X: " + xCoordinate + " Y: " + yCoordinate + " , " + "Color: " + color.getRGB());
    }
}
