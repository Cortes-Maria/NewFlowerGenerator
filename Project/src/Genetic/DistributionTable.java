package Genetic;

import ImageManagment.Pixel;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;

import lib.ICONSTANTS;

public class DistributionTable implements ICONSTANTS{
    Vector<ColorNode> distributionPetal;
    Vector<ColorNode> distributionCenter;
    Vector<Color> mainColorPetal;
    Vector<Color> mainColorCenter;
    Vector<Integer> totalPetal;
    Vector<Integer> totalCenter;

    public DistributionTable(){
        distributionPetal = new Vector<>();
        distributionCenter = new Vector<>();
        totalCenter = new Vector<>();
        totalPetal = new Vector<>();
        mainColorCenter = new Vector<>();
        mainColorPetal = new Vector<>();
    }

    public void insertDistributionPetal(Vector<Pixel> flowerPixels){
        distributionPetal.add(new ColorNode());
        totalPetal.add(0);
        for(Pixel pix : flowerPixels){
            insertColorPetal(pix.color);
        }
    }

    public void insertDistributionCenter(Vector<Pixel> flowerPixels){
        distributionCenter.add(new ColorNode());
        totalCenter.add(0);
        for(Pixel pix : flowerPixels){
            insertColorCenter(pix.color);
        }
    }


    public void insertColorPetal(Color color){
        if (!distributionPetal.isEmpty()){
            for (Color colorNode : distributionPetal.lastElement().colorInformation.keySet()) {
                if (isSimilar(colorNode, color)) {
                    distributionPetal.lastElement().colorInformation.get(colorNode).addToFrequency();
                    totalPetal.setElementAt(totalPetal.lastElement()+1, totalPetal.size()-1);
                    return;
                }
            }
        }
        distributionPetal.lastElement().colorInformation.put(color,new InfoNode());
        totalPetal.setElementAt(totalPetal.lastElement()+1, totalPetal.size()-1);
    }

    public void insertColorCenter(Color color){
        if (!distributionCenter.isEmpty()){
            for (Color distColor : distributionCenter.lastElement().colorInformation.keySet()) {
                if (isSimilar(distColor, color)) {
                    //System.out.println("Added Freq");
                    distributionCenter.lastElement().colorInformation.get(distColor).addToFrequency();
                    totalCenter.setElementAt(totalCenter.lastElement()+1, totalCenter.size()-1);
                    return;
                }
            }
        }
        //System.out.println("Added color");
        distributionCenter.lastElement().colorInformation.put(color,new InfoNode());
        totalCenter.setElementAt(totalCenter.lastElement()+1, totalCenter.size()-1);
    }

    public void setMainColor(){
        if(!distributionPetal.isEmpty()){
            for(ColorNode colorNode : distributionPetal){
                Color mainColor = (Color)colorNode.mainColor();
                mainColorPetal.add(mainColor);
            }
        }
        if(!distributionCenter.isEmpty()){
            for(ColorNode colorNode : distributionCenter){
                Color mainColor = (Color)colorNode.mainColor();
                mainColorCenter.add(mainColor);
            }
        }
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
        return false;
    }

    public float similarity(Color color1, Color color2){
        if(color1!=null && color2!=null){
            float distance = (float) Math.sqrt(Math.pow(color1.getRed()-color2.getRed(),2) +
                    Math.pow(color1.getGreen()-color2.getGreen(),2) +
                    Math.pow(color1.getBlue()-color2.getBlue(),2));
            //System.out.println(distance/COLOR_BASE); //Percetage
            return distance/COLOR_BASE;
        }
        return 0;
    }

    public void setPercentages(){
        DecimalFormat df = new DecimalFormat("#.####");
        float percentage = 0;
        float frequency = 0;
        for(int i=0; i<distributionPetal.size(); i++){
            for(Color petalColor : distributionPetal.elementAt(i).colorInformation.keySet()){
                frequency = distributionPetal.elementAt(i).colorInformation.get(petalColor).getFrequency();
                percentage = Float.parseFloat(df.format(frequency/totalPetal.elementAt(i)));
                distributionPetal.elementAt(i).colorInformation.get(petalColor).setPercentage(percentage);
            }
        }
        for(int i=0; i<distributionCenter.size(); i++){
            for(Color petalColor : distributionCenter.elementAt(i).colorInformation.keySet()){
                frequency = distributionCenter.elementAt(i).colorInformation.get(petalColor).getFrequency();
                percentage = Float.parseFloat(df.format(frequency/totalCenter.elementAt(i)));
                //System.out.println("F: " + frequency + " T: " + totalCenter.elementAt(i));
                distributionCenter.elementAt(i).colorInformation.get(petalColor).setPercentage(percentage);
            }
        }
    }

    public void setMinMaxValues(){
        int minValue = 0;
        for(int i=0; i<distributionPetal.size(); i++){
            for(Color petalColor : distributionPetal.elementAt(i).colorInformation.keySet()){
                float percentage = distributionPetal.elementAt(i).colorInformation.get(petalColor).getPercentage();
                distributionPetal.elementAt(i).colorInformation.get(petalColor).setMin(minValue);
                minValue += (int) (percentage*MAX_CHROMOSOME_VALUE);
                distributionPetal.elementAt(i).colorInformation.get(petalColor).setMax(minValue);
                minValue++;
            }
        }
        minValue = 0;
        for(int i=0; i<distributionCenter.size(); i++){
            for(Color centerColor : distributionCenter.elementAt(i).colorInformation.keySet()){
                float percentage = distributionCenter.elementAt(i).colorInformation.get(centerColor).getPercentage();
                //System.out.println(percentage);
                distributionCenter.elementAt(i).colorInformation.get(centerColor).setMin(minValue);
                minValue += (int) (percentage*MAX_CHROMOSOME_VALUE);
                distributionCenter.elementAt(i).colorInformation.get(centerColor).setMax(minValue);
                minValue++;
            }
        }
    }

    public void printTable(){
        //System.out.println(this.totalCenter);
        for(ColorNode colorNode : distributionCenter){
            colorNode.printNode();
        }
        System.out.println();
        /*System.out.println();
        System.out.println(this.totalPetal);*/
        for(ColorNode colorNode : distributionPetal){
            colorNode.printNode();
        }
    }

    //***************************Setters and Getters **************************************************

    public Vector<ColorNode> getDistributionPetal() {
        return distributionPetal;
    }

    public void setDistributionPetal(Vector<ColorNode> distributionPetal) {
        this.distributionPetal = distributionPetal;
    }

    public Vector<ColorNode> getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(Vector<ColorNode> distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public Vector<Color> getMainColorPetal() {
        return mainColorPetal;
    }

    public void setMainColorPetal(Vector<Color> mainColorPetal) {
        this.mainColorPetal = mainColorPetal;
    }

    public Vector<Color> getMainColorCenter() {
        return mainColorCenter;
    }

    public void setMainColorCenter(Vector<Color> mainColorCenter) {
        this.mainColorCenter = mainColorCenter;
    }

    public Vector<Integer> getTotalPetal() {
        return totalPetal;
    }

    public void setTotalPetal(Vector<Integer> totalPetal) {
        this.totalPetal = totalPetal;
    }

    public Vector<Integer> getTotalCenter() {
        return totalCenter;
    }

    public void setTotalCenter(Vector<Integer> totalCenter) {
        this.totalCenter = totalCenter;
    }
}
