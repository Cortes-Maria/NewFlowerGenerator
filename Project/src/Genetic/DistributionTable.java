package Genetic;

import ImageManagment.Pixel;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;

import lib.ICONSTANTS;

public class DistributionTable implements ICONSTANTS{
    ColorNode distribution;
    int total;

    public DistributionTable(){
       distribution = new ColorNode();
       total = 0;
    }

    public void insertDistribution(Vector<Pixel> flowerPixels){
        for(Pixel pix : flowerPixels){
            distribution.insertColor(pix.color);
            total++;
        }
    }

    public void setTableValues(){
        distribution.setPercentages(total);
        distribution.setMinMaxValue();
    }

    public void printTable(){
        //System.out.println(this.total);
        System.out.println("Tabla de distribución cromosomática");
        distribution.printNode();

    }

    //***************************Setters and Getters ******************************************

    public ColorNode getDistribution() {
        return distribution;
    }

    public void setDistribution(ColorNode distribution) {
        this.distribution = distribution;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
