package ImageManagment;

import Greedy.Zone;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Vector;

public class FlowerImage {
    private BufferedImage image;
    private Zone petal;
    private Zone center;
    private int petalsQuantity;
    private Vector<Pixel> heightPixels;
    private Vector<Pixel> widthPixels;
    private int deleteThis;

    public FlowerImage(){
        image = null;
        petal = new Zone();
        center = new Zone();
        petalsQuantity = 0;
        heightPixels = new Vector<Pixel>();
        widthPixels = new Vector<Pixel>();
    }

    public FlowerImage(BufferedImage pImage, Zone pPetal, Zone pCenter, int pPetalsQuantity,
                       Vector<Pixel> pHeightPixels,Vector<Pixel> pWidthPixels){
        image = pImage;
        petal = pPetal;
        center = pCenter;
        petalsQuantity = pPetalsQuantity;
        heightPixels = pHeightPixels;
        widthPixels = pWidthPixels;
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
}
