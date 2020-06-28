package ImageManagment;

import Greedy.Zone;

import java.awt.image.BufferedImage;
import java.util.List;

public class FlowerImage {
    private BufferedImage image;
    private Zone petal;
    private Zone center;
    private int petalsQuantity;
    private List<Pixel> heightPixels;
    private List<Pixel> widthPixels;

    public FlowerImage(){
        image = null;
        petal = center = null;
        petalsQuantity = 0;
        heightPixels = widthPixels = null;
    }

    public FlowerImage(BufferedImage pImage, Zone pPetal, Zone pCenter, int pPetalsQuantity, List<Pixel> pHeightPixels,List<Pixel> pWidthPixels){
        image = pImage;
        petal = pPetal;
        center = pCenter;
        petalsQuantity = pPetalsQuantity;
        heightPixels = pHeightPixels;
        widthPixels = pWidthPixels;
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

    public List<Pixel> getHeightPixels() {
        return heightPixels;
    }

    public void setHeightPixels(List<Pixel> heightPixels) {
        this.heightPixels = heightPixels;
    }

    public List<Pixel> getWidthPixels() {
        return widthPixels;
    }

    public void setWidthPixels(List<Pixel> widthPixels) {
        this.widthPixels = widthPixels;
    }
}
