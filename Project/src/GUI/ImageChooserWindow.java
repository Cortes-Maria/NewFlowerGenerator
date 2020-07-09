package GUI;

import Genetic.GeneticAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageChooserWindow {
    private JButton continueButton;
    private JButton chooseButton1;
    private JLabel imagePath1;
    private JLabel imagePath2;
    private JLabel imagePath3;
    private JPanel ImageChooserPanel;
    private JLabel image1;
    private JLabel image2;
    private JLabel image3;
    private JLabel path1;
    private JLabel path2;
    private JLabel path3;
    public File[] files;
    public static String flowerWindowPath = "/home/ixel/Desktop/Flowers";

    public ImageChooserWindow() {
        setWindow();
        chooseButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File f = new File(flowerWindowPath);
                JFileChooser chooser = new JFileChooser(f);
                chooser.setMultiSelectionEnabled(true);
                chooser.showOpenDialog(null);
                files = chooser.getSelectedFiles();

                //Sets paths text in window
                if (files.length != 0) {
                    if(files.length == 3){
                        // set the label to the path of the selected directory
                        path1.setText(files[0].getAbsolutePath());
                        path2.setText(files[1].getAbsolutePath());
                        path3.setText(files[2].getAbsolutePath());
                    }else if(files.length == 2){
                        // set the label to the path of the selected directory
                        path1.setText(files[0].getAbsolutePath());
                        path2.setText(files[1].getAbsolutePath());
                        path3.setText("No file selected");
                    }else{
                        // set the label to the path of the selected directory
                        path1.setText(files[0].getAbsolutePath());
                        path2.setText("No file selected");
                        path3.setText("No file selected");
                    }
                }else{
                    path1.setText("No file selected");
                    path2.setText("No file selected");
                    path3.setText("No file selected");
                }
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComponent comp = (JComponent) actionEvent.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
                JFrame frame2 = new JFrame("ImageAnalyzer");
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame2.setLayout(new BorderLayout());
                ArrayList<File> images = new ArrayList<File>(Arrays.asList(files));
                frame2.add(new ImageProcessor(images));
                frame2.pack();
                frame2.setLocationRelativeTo(null);
                frame2.setVisible(true);
            }
        });
    }

    public void setWindow(){
        // Import ImageIcon
        ImageIcon iconLogo1 =
                new ImageIcon(new ImageIcon(flowerWindowPath + "/flower1.png").
                        getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        ImageIcon iconLogo2 =
                new ImageIcon(new ImageIcon(flowerWindowPath + "/flower2.png").
                        getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        ImageIcon iconLogo3 =
                new ImageIcon(new ImageIcon(flowerWindowPath + "/flower3.png").
                        getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        // In init() method write this code
        image1.setIcon(iconLogo1);
        image2.setIcon(iconLogo2);
        image3.setIcon(iconLogo3);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChooseImageWindow");
        frame.setContentPane(new ImageChooserWindow().ImageChooserPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        //frame.pack();
        frame.setVisible(true);
    }
}
