package GUI;

import ImageManagment.FlowerImage;

public class ImageInformation {
    //Singleton class to store the information of a individual flower before adding it to ImageData
    private static ImageInformation instance;
    public FlowerImage flowerImage;

    private ImageInformation(){
        flowerImage = new FlowerImage();
    }

    public static ImageInformation getInstance(){
        if(instance == null){
            instance = new ImageInformation();
        }
        return instance;
    }

    public static void setNewInstance(){
        instance = new ImageInformation();
    }

    //Methods to add to flowerImage
}
