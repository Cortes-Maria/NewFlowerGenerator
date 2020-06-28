package Greedy;

import ImageManagment.Pixel;

public class Zone {
    private Pixel lowerLimit;
    private Pixel upperLimit;

    public Zone(Pixel pLowerLimit, Pixel pUpperLimit){
      lowerLimit = pLowerLimit;
      upperLimit = pUpperLimit;
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
