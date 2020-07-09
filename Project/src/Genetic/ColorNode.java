package Genetic;

import java.awt.*;
import java.util.HashMap;

public class ColorNode {
    HashMap<Color, InfoNode> colorInformation;

    public ColorNode(){
        colorInformation = new HashMap<>();
    }

    public Color mainColor(){
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

    public HashMap<Color, InfoNode> getColorInformation() {
        return colorInformation;
    }

    public void setColorInformation(HashMap<Color, InfoNode> colorInformation) {
        this.colorInformation = colorInformation;
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
        Color mainColor = this.mainColor();
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
}
