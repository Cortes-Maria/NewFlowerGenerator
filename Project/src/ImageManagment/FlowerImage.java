package ImageManagment;

import Greedy.Zone;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import lib.ICONSTANTS;

public class FlowerImage implements ICONSTANTS{
    private BufferedImage image;
    private Vector<Pixel> petalZonePixels;
    private Vector<Pixel> centerZonePixels;
    private Zone petal;
    private Zone center;
    private int petalsQuantity;
    private Vector<Pixel> heightPixels;
    private Vector<Pixel> widthPixels;

    public FlowerImage(){
        image = null;
        petal = new Zone();
        center = new Zone();
        petalsQuantity = 0;
        heightPixels = new Vector<Pixel>();
        widthPixels = new Vector<Pixel>();
        petalZonePixels = new Vector<Pixel>();
        centerZonePixels = new Vector<Pixel>();
    }

    public FlowerImage(BufferedImage pImage, Zone pPetal, Zone pCenter, int pPetalsQuantity,
                       Vector<Pixel> pHeightPixels,Vector<Pixel> pWidthPixels){
        image = pImage;
        petal = pPetal;
        center = pCenter;
        petalsQuantity = pPetalsQuantity;
        heightPixels = pHeightPixels;
        widthPixels = pWidthPixels;
        petalZonePixels = new Vector<Pixel>();
        centerZonePixels = new Vector<Pixel>();
    }
    private void ExtractPetalZonePixels(){
        int[] measures = PetalwidthHeigthCalculator();
        int xLimit = measures[WIDTH_POS] + petal.getUpperLimit().xCoordinate;
        int yLimit = measures[HEIGHT_POS] + petal.getUpperLimit().yCoordinate;
        for(int x=petal.getUpperLimit().xCoordinate; x<=xLimit;x++){
            for(int y=petal.getUpperLimit().yCoordinate;y<=yLimit;y++){
                Color color = new Color(image.getRGB(x,y),true);
                Pixel pixel = new Pixel(color,x,y);
                petalZonePixels.add(pixel);
            }
        }
    }
    private void ExtractCenterZonePixels(){
        int centerWidth = center.getLowerLimit().xCoordinate - center.getUpperLimit().xCoordinate;
        int xLimit = center.getUpperLimit().xCoordinate + centerWidth;

        int centerHeight = center.getLowerLimit().yCoordinate - center.getUpperLimit().yCoordinate;
        int yLimit = center.getUpperLimit().yCoordinate + centerHeight;

        for(int x=center.getUpperLimit().xCoordinate; x<=xLimit;x++){
            for(int y=center.getUpperLimit().yCoordinate;y<=yLimit;y++){
                Color color = new Color(image.getRGB(x,y),true);
                Pixel pixel = new Pixel(color,x,y);
                centerZonePixels.add(pixel);
            }
        }
    }
    public void extractPixels(){
        ExtractPetalZonePixels();
        ExtractCenterZonePixels();
    }

    private int[] PetalwidthHeigthCalculator(){
        int[] result = new int[2];
        int X1 = heightPixels.get(0).xCoordinate;
        int Y1 = heightPixels.get(0).yCoordinate;

        int X2 = heightPixels.get(1).xCoordinate;
        int Y2 = heightPixels.get(1).yCoordinate;

        int height =(int) Math.hypot(X1-X2, Y1-Y2);
        result[HEIGHT_POS] = height;

        X1 = widthPixels.get(0).xCoordinate;
        Y1 = widthPixels.get(0).yCoordinate;

        X2 = widthPixels.get(1).xCoordinate;
        Y2 = widthPixels.get(1).yCoordinate;

        int width = (int) Math.hypot(X1-X2, Y1-Y2);
        result[WIDTH_POS] = width;

        return result;
    }

    public void insertPixelZonePetal(Color color, int x, int y){
        petal.insertLimit(color, x, y);
    }

    public void insertPixelZoneCenter(Color color, int x, int y){
        center.insertLimit(color, x, y);
    }

    public void insertPixelHeight(Color color, int x, int y){
        heightPixels.add(new Pixel(color, x, y));
    }

    public void insertPixelWidth(Color color, int x, int y){
        widthPixels.add(new Pixel(color, x, y));
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Zone getPetal() {
        return petal;
    }

    public void setPetal(Zone petal) {
        this.petal = petal;
    }

    public Zone getCenter() {
        return center;
    }

    public void setCenter(Zone center) {
        this.center = center;
    }

    public int getPetalsQuantity() {
        return petalsQuantity;
    }

    public void setPetalsQuantity(int petalsQuantity) {
        this.petalsQuantity = petalsQuantity;
    }

    public Vector<Pixel> getHeightPixels() {
        return heightPixels;
    }

    public void setHeightPixels(Vector<Pixel> heightPixels) {
        this.heightPixels = heightPixels;
    }

    public Vector<Pixel> getWidthPixels() {
        return widthPixels;
    }

    public void setWidthPixels(Vector<Pixel> widthPixels) {
        this.widthPixels = widthPixels;
    }

    public Vector<Pixel> getPetalZonePixels() {
        return petalZonePixels;
    }

    public void setPetalZonePixels(Vector<Pixel> petalZonePixels) {
        this.petalZonePixels = petalZonePixels;
    }

    public Vector<Pixel> getCenterZonePixels() {
        return centerZonePixels;
    }

    public void setCenterZonePixels(Vector<Pixel> centerZonePixels) {
        this.centerZonePixels = centerZonePixels;
    }
}
