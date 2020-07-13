package ImageGenerator;

import GUI.Flowers;
import ImageManagment.FlowerImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Thumbnail extends JPanel {
    private JPanel flowers;

    public Thumbnail(){
        flowers = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        Flowers flowers = Flowers.getInstance();
        for(FlowerImage flowerImage : flowers.flowerImages){
            ImageIcon imageIcon = new ImageIcon(flowerImage.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
            JLabel newLabel = new JLabel();
            newLabel.setIcon(imageIcon);
            this.flowers.add(newLabel);
        }

        add(this.flowers,gbc);
    }

}
