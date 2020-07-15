package Greedy;

import GUI.Flowers;
import ImageManagment.FlowerImage;
import ImageManagment.Pixel;
import lib.ICONSTANTS;
import java.awt.*;
import java.util.Vector;

public class GreedyColorAnalyser implements ICONSTANTS{
    private Vector<Vector<Pixel>> optimalPixelsPETAL;
    private Vector<Vector<Pixel>> optimalPixelsCENTER;

    public GreedyColorAnalyser(){
        optimalPixelsPETAL = new Vector<Vector<Pixel>>();
        optimalPixelsCENTER = new Vector<Vector<Pixel>>();
        extractOptimalPixels();
//        System.out.println("Pixeles optimos petalos: " + " Flor1: "+optimalPixelsPETAL.get(0).size() +" Flor 2: "+ optimalPixelsPETAL.get(1).size()+" Flor 3: "+ optimalPixelsPETAL.get(2));
 //       System.out.println("Pixeles optimos centro: " + " Flor1: "+optimalPixelsCENTER.get(0).size() +" Flor 2: "+ optimalPixelsCENTER.get(1).size()+" Flor 3: "+optimalPixelsCENTER.get(2));


    }

    private void extractOptimalPixels(){
        Vector<FlowerImage> flowersImages = Flowers.getInstance().flowerImages;
        for (FlowerImage currentFlower: flowersImages) {
            optimalPixelsPETAL.add(zoneAnalisis(currentFlower.getPetalZonePixels()));
            optimalPixelsCENTER.add(zoneAnalisis(currentFlower.getCenterZonePixels()));
        }
    }



    private Vector<Pixel> zoneAnalisis(Vector<Pixel> pZonePixels){
        Vector<Pixel> result = new Vector<Pixel>();
        int pixelsPerGroup = pZonePixels.size()/GROUPS_QUANTITY;
        int index=0;
        for(int currentGroup=0; currentGroup<GROUPS_QUANTITY;currentGroup++){
            for(int currentPixel=index;currentPixel<index+pixelsPerGroup;currentPixel++){
                //se extraen los pixeles optimos en el currentGRoup
                float luminance = calculateLuminance(pZonePixels.get(currentPixel).color);
                if(greedyCriteria(luminance)){
                    result.add(pZonePixels.get(currentPixel));
                }
            }
            index += pixelsPerGroup;
        }
        return result;
    }


    private boolean greedyCriteria(float pLuminance){
        if(pLuminance <= LUMINANCE_THRESHOLD){
            return true;
        }
        else return false;
    }
    public void printPixelVector(Vector<Pixel> pVector){
        for(Pixel pixel: pVector){
            pixel.printPixel();
        }
    }


    //convert a gamma encoded RGB to a linear value
    private float sRGBtoLinear(float colorChannel){
        if(colorChannel <= 0.04045f){
            return colorChannel/12.92f;
        }
        else{
            return (float)Math.pow(((colorChannel+0.055f)/1.055f),2.4f);
        }
    }

    //linear measure of light, spectrally weighted for normal vision ( 0.0 - 1.0 )
    private float calculateLuminance(Color pColor){
        //convert all sRGB 8 bit integer values to decimal 0.0-1.0
       float vR = (float)pColor.getRed()/255;
       float vG = (float)pColor.getGreen()/255;
       float vB = (float)pColor.getBlue()/255;

        return   (0.2126f * sRGBtoLinear(vR) + 0.7152f * sRGBtoLinear(vG) + 0.0722f * sRGBtoLinear(vB));

    }

    public Vector<Vector<Pixel>> getOptimalPixelsPETAL() {
        return optimalPixelsPETAL;
    }

    public Vector<Vector<Pixel>> getOptimalPixelsCENTER() {
        return optimalPixelsCENTER;
    }

    public void setOptimalPixelsPETAL(Vector<Vector<Pixel>> optimalPixelsPETAL) {
        this.optimalPixelsPETAL = optimalPixelsPETAL;
    }

    public void setOptimalPixelsCENTER(Vector<Vector<Pixel>> optimalPixelsCENTER) {
        this.optimalPixelsCENTER = optimalPixelsCENTER;
    }
}
