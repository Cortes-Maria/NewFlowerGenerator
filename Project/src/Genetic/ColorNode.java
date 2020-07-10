package Genetic;

import lib.ICONSTANTS;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;

public class ColorNode implements ICONSTANTS {
    HashMap<Color, InfoNode> colorInformation;

    public ColorNode(){
        colorInformation = new HashMap<>();
    }

    public void insertColor(Color newColor){
        for(Color color : colorInformation.keySet()){
            if (isSimilar(color, newColor)) {
                colorInformation.get(color).addToFrequency();
                return;
            }
        }
        colorInformation.put(newColor, new InfoNode());
    }

    public boolean isSimilar(Color color1, Color color2){
        if(color1!=null && color2!=null){
            float distance = (float) Math.sqrt(Math.pow(color1.getRed()-color2.getRed(),2) +
                    Math.pow(color1.getGreen()-color2.getGreen(),2) +
                    Math.pow(color1.getBlue()-color2.getBlue(),2));
            if(distance/COLOR_BASE <= DISTRIBUTION_COLOR_PERCENTAGE){
                return true;
            }
        }
        return false;
    }

    public float getSimilarity(int value){
        Color color = this.getValueOf(value);
        if(color!=null){
            if(colorInformation.isEmpty()){
                Color mainColor = this.getMainColor();
                float distance = (float) Math.sqrt(Math.pow(mainColor.getRed()-color.getRed(),2) +
                        Math.pow(mainColor.getGreen()-color.getGreen(),2) +
                        Math.pow(mainColor.getBlue()-color.getBlue(),2));
                return distance/COLOR_BASE;
            }
        }
        return 0;
    }

    public void setMinMaxValue(){
        int minValue = 0;
        for(Color color : colorInformation.keySet()){
            float percentage = colorInformation.get(color).getPercentage();
            colorInformation.get(color).setMin(minValue);
            minValue += (int) (percentage*MAX_CHROMOSOME_VALUE);
            colorInformation.get(color).setMax(minValue);
            minValue++;
        }

    }

    public void setPercentages(int total){
        DecimalFormat df = new DecimalFormat("#.####");
        float percentage = 0;
        float frequency = 0;
        for(Color color : colorInformation.keySet()){
            frequency = colorInformation.get(color).getFrequency();
            percentage = Float.parseFloat(df.format(frequency/total));
            colorInformation.get(color).setPercentage(percentage);
        }
    }

    public Color getMainColor(){
        if(!colorInformation.isEmpty()){
            Color mainColor = (Color)colorInformation.keySet().toArray()[0];
            for(Color color : colorInformation.keySet()){
                if(colorInformation.get(mainColor).percentage < colorInformation.get(color).percentage){
                    mainColor = color;
                }
            }
            return mainColor;
        }
        return null;
    }

    public Color getValueOf(int individual){
        if(!colorInformation.isEmpty()){
            for(Color color : colorInformation.keySet()){
                if(individual>=colorInformation.get(color).min && individual<=colorInformation.get(color).max){
                    return color;
                }
            }
        }
        return null;
    }

    public boolean isMainColor(int value){
        Color mainColor = this.getMainColor();
        if(value>=colorInformation.get(mainColor).min && value<=colorInformation.get(mainColor).max){
            return true;
        }
        return false;
    }

    public void printNode(){
        for(Color color : colorInformation.keySet()){
            System.out.print("Color: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ");
            System.out.println(colorInformation.get(color).printNode());
        }
    }

    public void printColor(int value){
        Color color = getValueOf(value);
        System.out.println("Color: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue()
        + " " + colorInformation.get(color).printNode());
    }
}
