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
            float distance = colorDistance(color1.getRGB(),color2.getRGB());
            if(distance <= COLOR_SIMILARITY){
                return true;
            }
        }
        return false;
    }

    public static int colorDistance(int pColor1, int pColor2){
        int deltaBlue = Math.abs(((pColor1)&0xFF) - ((pColor2)&0xFF));
        int deltaGreen = Math.abs(((pColor1>>8)&0xFF) - ((pColor2>>8)&0xFF));
        int deltaRed = Math.abs(((pColor1>>16)&0xFF) - ((pColor2>>16)&0xFF));
        return deltaRed + deltaGreen + deltaBlue;
    }


    public float getSimilarity(int value){
        Color color = this.getValueOf(value);
        if(color!=null){
            if(!colorInformation.isEmpty()){
                Color mainColor = this.getMainColor();
                    return colorDistance(color.getRGB(),mainColor.getRGB());
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
