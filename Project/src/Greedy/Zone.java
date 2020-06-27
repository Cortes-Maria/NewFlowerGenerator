package Greedy;

import java.util.ArrayList;

public class Zone {
    private int xLowerLimit;
    private int yLowerLimit;
    private int xUpperLimit;
    private int yUpperLimit;

    public Zone(int pXLowerLimit, int pYUpperLimit, int pXUpperLimit, int pYLowerLimit){
        xLowerLimit = pXLowerLimit;
        yLowerLimit = pYLowerLimit;
        xUpperLimit = pXUpperLimit;
        yUpperLimit = pYUpperLimit;
    }

    public int getxLowerLimit() {
        return xLowerLimit;
    }

    public int getyLowerLimit() {
        return yLowerLimit;
    }

    public int getxUpperLimit() {
        return xUpperLimit;
    }

    public int getyUpperLimit() {
        return yUpperLimit;
    }
}
