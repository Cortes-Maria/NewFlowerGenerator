package Genetic;

import ImageManagment.Pixel;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;

import lib.ICONSTANTS;

public class DistributionTable implements ICONSTANTS{
    ColorNode distributionPetal;
    ColorNode distributionCenter;
    int totalPetal;
    int totalCenter;

    public DistributionTable(){
        distributionPetal = new ColorNode();
        distributionCenter = new ColorNode();
        totalCenter = 0;
        totalPetal = 0;
    }

    public void insertDistributionPetal(Vector<Pixel> flowerPixels){
        for(Pixel pix : flowerPixels){
            distributionPetal.insertColor(pix.color);
            totalPetal++;
        }
    }

    public void insertDistributionCenter(Vector<Pixel> flowerPixels){
        for(Pixel pix : flowerPixels){
            distributionCenter.insertColor(pix.color);
            totalCenter++;
        }
    }

    public void setTableValues(){
        distributionPetal.setPercentages(totalPetal);
        distributionCenter.setPercentages(totalCenter);
        distributionPetal.setMinMaxValue();
        distributionCenter.setMinMaxValue();
    }

    public void printTable(){
        //System.out.println(this.totalCenter);
        System.out.println("Center colors");
        distributionCenter.printNode();
        System.out.println();
        System.out.println("Petal colors");
        distributionPetal.printNode();
        System.out.println();
    }

    //***************************Setters and Getters ******************************************

    public ColorNode getDistributionPetal() {
        return distributionPetal;
    }

    public void setDistributionPetal(ColorNode distributionPetal) {
        this.distributionPetal = distributionPetal;
    }

    public ColorNode getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(ColorNode distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public int getTotalPetal() {
        return totalPetal;
    }

    public void setTotalPetal(int totalPetal) {
        this.totalPetal = totalPetal;
    }

    public int getTotalCenter() {
        return totalCenter;
    }

    public void setTotalCenter(int totalCenter) {
        this.totalCenter = totalCenter;
    }
}
