package Genetic;

import ImageManagment.Pixel;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;

import lib.ICONSTANTS;

public class DistributionTable implements ICONSTANTS{
    HashMap<Color, DistributionNode> distributionPetal;
    HashMap<Color, DistributionNode> distributionCenter;
    int totalPetal;
    int totalCenter;

    public DistributionTable(){
        distributionPetal = new HashMap<>();
        distributionCenter = new HashMap<>();
        totalCenter = totalPetal = 0;
    }

    public void insertDistributionPetal(Vector<Pixel> pixels){
        for(Pixel pixel : pixels){
            insertColorPetal(pixel.color);
        }
    }

    public void insertDistributionCenter(Vector<Pixel> pixels){
        for(Pixel pixel : pixels){
            insertColorCenter(pixel.color);
        }
    }

    public void insertColorPetal(Color color){
        if (!distributionPetal.isEmpty()) {
            for (Color distColor : distributionPetal.keySet()) {
                if (isSimilar(distColor, color)) {
                    distributionPetal.get(distColor).addToFrequency();
                    //System.out.println("Added");
                    totalPetal++;
                    return;
                }
            }
        }
        //System.out.println("New added");
        distributionPetal.put(color, new DistributionNode());
        totalPetal++;
    }

    public void insertColorCenter(Color color){
        if (!distributionCenter.isEmpty()) {
            for (Color distColor : distributionCenter.keySet()) {
                if (isSimilar(distColor, color)) {
                    distributionCenter.get(distColor).addToFrequency();
                    totalCenter++;
                    return;
                }
            }
        }
        distributionCenter.put(color, new DistributionNode());
        totalCenter++;
    }

    public boolean isSimilar(Color color1, Color color2){
        if(color1!=null && color2!=null){
            float distance = (float) Math.sqrt(Math.pow(color1.getRed()-color2.getRed(),2) +
                    Math.pow(color1.getGreen()-color2.getGreen(),2) +
                    Math.pow(color1.getBlue()-color2.getBlue(),2));
            if(distance/COLOR_BASE <= PERCENTAGE_COLOR){
                return true;
            }
        }
        //System.out.println("Not similar");
        return false;
    }

    public void setPercentages(){
        DecimalFormat df = new DecimalFormat("#.####");
        float percentage = 0;
        float frequency = 0;
        for(Color petalColor : distributionPetal.keySet()){
            frequency = distributionPetal.get(petalColor).getFrequency();
            percentage = frequency/totalPetal;
            //System.out.println(percentage*100);
            //percentage = Float.parseFloat(df.format((frequency/totalPetal)));
            //System.out.println(percentage);
            //System.out.println(frequency + " " + totalPetal);
            distributionPetal.get(petalColor).setPercentage(percentage);
        }
        for(Color centerColor: distributionCenter.keySet()){
            frequency = distributionCenter.get(centerColor).getFrequency();
            percentage = Float.parseFloat(df.format(frequency/totalCenter));
            distributionCenter.get(centerColor).setPercentage(percentage);
        }
    }

    public void setMinMaxValues(){
        int minValue = 0;
        for(Color petalColor : distributionPetal.keySet()){
            float percentage = distributionPetal.get(petalColor).getPercentage();
            distributionPetal.get(petalColor).setMin(minValue);
            minValue += (int) (percentage*MAX_CHROMOSONE_VALUE);
            distributionPetal.get(petalColor).setMax(minValue - 1);
        }
        minValue = 0;
        for(Color petalColor : distributionCenter.keySet()){
            float percentage = distributionCenter.get(petalColor).getPercentage();
            distributionCenter.get(petalColor).setMin(minValue);
            minValue += (int) (percentage*MAX_CHROMOSONE_VALUE);
            distributionCenter.get(petalColor).setMax(minValue - 1);
        }

    }

    public void printTable(){
        //System.out.println(this.totalPetal);
        //System.out.println(this.distributionPetal.size());
        for(Color color : distributionPetal.keySet()){
            System.out.println(distributionPetal.get(color).printNode());
        }
    }

    //***************************Setters and Getters **************************************************

    public HashMap<Color, DistributionNode> getDistributionPetal() {
        return distributionPetal;
    }

    public void setDistributionPetal(HashMap<Color, DistributionNode> distributionPetal) {
        this.distributionPetal = distributionPetal;
    }

    public HashMap<Color, DistributionNode> getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(HashMap<Color, DistributionNode> distributionCenter) {
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
