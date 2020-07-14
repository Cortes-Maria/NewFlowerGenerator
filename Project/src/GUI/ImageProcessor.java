package GUI;

import Genetic.DistributionTable;
import Genetic.GeneticAlgorithm;
import Greedy.GreedyColorAnalyser;
import ImageGenerator.Controller;
import ImageGenerator.Flower;
import ImageGenerator.Thumbnail;
import ImageManagment.FlowerImage;
import ImageManagment.Pixel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class ImageProcessor extends JPanel {
    private BufferedImage img;
    private JLabel label;
    private BufferedImage resizedImage;

    private JPanel fieldsPixel;
    private JPanel fieldsInformation;
    private JTextField red;
    private JTextField green;
    private JTextField blue;
    private JTextField xCoordinate;
    private JTextField yCoordinate;
    private JButton nextButton;
    private ArrayList<File> files;
    private JComboBox<String> comboBox;
    //private JTextField petalQuantity;
    //private JLabel petalQuantityLabel;

    public ImageProcessor(){
    }

    public ImageProcessor(ArrayList<File> pFiles){
        files = pFiles;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        label = new JLabel();

        try{
            img = ImageIO.read(files.get(0));
            files.remove(0);
            ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(400, 400,
                    Image.SCALE_SMOOTH));
            label.setIcon(imageIcon);
            Image image = imageIcon.getImage();
            resizedImage = toBufferedImage(image);
            //Gets imageInformation and sets the bufferedImage
            ImageInformation imageInformation = ImageInformation.getInstance();
            imageInformation.flowerImage.setImage(resizedImage);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        nextButton = new JButton("Next");
        comboBox = new JComboBox<String>();
        //petalQuantityLabel = new JLabel("Petal Quantity");
        setComboBox();

        add(label,gbc);
        red = new JTextField(3);
        green = new JTextField(3);
        blue = new JTextField(3);
        xCoordinate = new JTextField(3);
        yCoordinate = new JTextField(3);

        fieldsPixel = new JPanel();
        //petalQuantity = new JTextField(3);

        fieldsPixel.setBorder(new EmptyBorder(5, 5, 5, 5));
        fieldsPixel.add(red);
        fieldsPixel.add(green);
        fieldsPixel.add(blue);
        fieldsPixel.add(xCoordinate);
        fieldsPixel.add(yCoordinate);

        fieldsInformation = new JPanel();
        fieldsInformation.setBorder(new EmptyBorder(5,5,5,5));
        //fieldsInformation.add(petalQuantityLabel);
        //fieldsInformation.add(petalQuantity);
        fieldsInformation.add(comboBox);

        add(fieldsPixel, gbc);
        add(fieldsInformation, gbc);
        add(nextButton, gbc);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int locX = MouseInfo.getPointerInfo().getLocation().x;

                int locY = MouseInfo.getPointerInfo().getLocation().y;

                if(!isTransparent(resizedImage.getRGB(mouseEvent.getX(), mouseEvent.getY()))){
                    int packedInt = resizedImage.getRGB(mouseEvent.getX(), mouseEvent.getY());
                    Color color = new Color(packedInt, true);
                    fieldsPixel.setBackground(color);
                    red.setText(Integer.toString(color.getRed()));
                    green.setText(Integer.toString(color.getGreen()));
                    blue.setText(Integer.toString(color.getBlue()));
                    xCoordinate.setText(Integer.toString(mouseEvent.getX()));
                    yCoordinate.setText(Integer.toString(mouseEvent.getY()));
                    //System.out.println("LocX: "+ locX + " LocY: "+locY+" MouseX: " + mouseEvent.getX()+" MouseY: "+mouseEvent.getY());

                    insertPixel(color,mouseEvent.getX(),mouseEvent.getY());
                }else{
                    fieldsPixel.setBackground(new Color(255,255,255));
                    red.setText("");
                    green.setText("");
                    blue.setText("");
                    xCoordinate.setText("");
                    yCoordinate.setText("");
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Adds the number of petals in the flower
                ImageInformation imageInformation = ImageInformation.getInstance();
                //imageInformation.flowerImage.setPetalsQuantity(Integer.parseInt(petalQuantity.getText()));

                //Insert ImageInformation in Flowers
                Flowers flowers = Flowers.getInstance();
                flowers.addFlowerImage(imageInformation.getFlowerImage());
                //System.out.println("Flower");
                //System.out.println("Size: " + imageData.flowerImages.size());

                //Deletes the data in ImageInformation
                ImageInformation.setNewInstance();

                if(files.size() >= 1){
                    JComponent comp = (JComponent) actionEvent.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                    JFrame nextFrame = new JFrame("ImageAnalyzer");
                    nextFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    nextFrame.setLayout(new BorderLayout());
                    nextFrame.add(new ImageProcessor(files));
                    nextFrame.pack();
                    nextFrame.setLocationRelativeTo(null);
                    nextFrame.setVisible(true);
                }else{

                    //Should open new Window
                    JComponent comp = (JComponent) actionEvent.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();

                    //Controller prueba = new Controller();
                    JFrame frame = new JFrame("Flower");
                    frame.setSize(500, 500);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null);

                    JPanel flower = new Flower();
                    frame.add(flower);
                    frame.setVisible(true);

                    JFrame thumbNail = new JFrame("Thumbnail");
                    thumbNail.setSize(300,150);
                    thumbNail.add(new Thumbnail());
                    thumbNail.setVisible(true);
                }
            }
        });

    }

    public boolean isTransparent( int pixel ) {
        return (pixel>>24) == 0x00;
    }

    public static BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage){
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bImage;
    }

    public void setComboBox(){
        comboBox.addItem("Petal Zone");
        comboBox.addItem("Central Zone");
        comboBox.addItem("Height Petal");
        comboBox.addItem("Width Petal");
    }

    public void insertPixel(Color color, int x, int y){
        String cbStr = (String) comboBox.getSelectedItem();
        ImageInformation imageInformation = ImageInformation.getInstance();
        if(cbStr.equals("Petal Zone")){
            //System.out.println("Petal Zone" + ImageData.getInstance().flowerImages.size());
            imageInformation.flowerImage.insertPixelZonePetal(color, x, y);
        }else if(cbStr.equals("Central Zone")){
            //System.out.println("Central Zone" + ImageData.getInstance().flowerImages.size());
            imageInformation.flowerImage.insertPixelZoneCenter(color, x, y);
        }else if(cbStr.equals("Height Petal")){
            //System.out.println("Height Petal" + ImageData.getInstance().flowerImages.size());
            imageInformation.flowerImage.insertPixelHeight(color, x, y);
        }else if(cbStr.equals("Width Petal")){
            //System.out.println("Width Petal" + ImageData.getInstance().flowerImages.size());
            imageInformation.flowerImage.insertPixelWidth(color, x, y);
        }else{
            //Error dialog
        }
    }
}
