package ImageManagment;

import ImageManagment.Pixel;

import java.awt.*;

public class Zone {
    private Pixel lowerLimit;
    private Pixel upperLimit;

    public Zone(){
    }

    public Zone(Pixel pLowerLimit, Pixel pUpperLimit){
      lowerLimit = pLowerLimit;
      upperLimit = pUpperLimit;
    }

    public void insertLimit(Color color, int x, int y){
        Pixel pixel = new Pixel(color, x, y);
        if(lowerLimit == null){
            setLowerLimit(pixel);
        }else{
            if(lowerLimit.yCoordinate >= pixel.yCoordinate){
                setUpperLimit(pixel);
            }else{
                setUpperLimit(lowerLimit);
                setLowerLimit(pixel);
            }
        }
    }

    public Pixel getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Pixel lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Pixel getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Pixel upperLimit) {
        this.upperLimit = upperLimit;
    }
}
