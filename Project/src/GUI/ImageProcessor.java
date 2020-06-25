package GUI;

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

public class ImageProcessor extends JPanel {
    private BufferedImage img;
    private JLabel label;
    private BufferedImage resizedImage;

    private JPanel fields;
    private JTextField red;
    private JTextField green;
    private JTextField blue;
    private JTextField xCoordinate;
    private JTextField yCoordinate;
    private JButton nextButton;
    private ArrayList<File> files;

    public ImageProcessor(){

    }
    public ImageProcessor(ArrayList<File> pFiles){
        files = pFiles;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        label = new JLabel();
        nextButton = new JButton("Siguiente");

        try{
            img = ImageIO.read(files.get(0));
            files.remove(0);
            ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(400, 400,
                    Image.SCALE_SMOOTH));
            label.setIcon(imageIcon);
            Image image = imageIcon.getImage();
            resizedImage = toBufferedImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        add(label,gbc);
        fields = new JPanel();
        fields.setBorder(new EmptyBorder(5, 5, 5, 5));
        red = new JTextField(3);
        green = new JTextField(3);
        blue = new JTextField(3);
        xCoordinate = new JTextField(3);
        yCoordinate = new JTextField(3);
        fields.add(red);
        fields.add(green);
        fields.add(blue);
        fields.add(xCoordinate);
        fields.add(yCoordinate);
        add(fields, gbc);
        add(nextButton, gbc);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int locX = MouseInfo.getPointerInfo().getLocation().x;
                int locY = MouseInfo.getPointerInfo().getLocation().y;

                if(!isTransparent(resizedImage.getRGB(mouseEvent.getX(), mouseEvent.getY()))){

                    int packedInt = resizedImage.getRGB(mouseEvent.getX(), mouseEvent.getY());
                    Color color = new Color(packedInt, true);
                    fields.setBackground(color);
                    red.setText(Integer.toString(color.getRed()));
                    green.setText(Integer.toString(color.getGreen()));
                    blue.setText(Integer.toString(color.getBlue()));
                    xCoordinate.setText(Integer.toString(locX));
                    yCoordinate.setText(Integer.toString(locY));
                    ImageData imageData = ImageData.getInstance();
                    imageData.insertPixel(color, locX, locY);
                }else{
                    fields.setBackground(new Color(255,255,255));
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
                if(files.size() >= 1){
                    JComponent comp = (JComponent) actionEvent.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                    JFrame frame2 = new JFrame("ImageAnalyzer");
                    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame2.setLayout(new BorderLayout());
                    frame2.add(new ImageProcessor(files));
                    frame2.pack();
                    frame2.setLocationRelativeTo(null);
                    frame2.setVisible(true);
                }else{
                    /*JComponent comp = (JComponent) actionEvent.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();*/
                    ImageData imageData = ImageData.getInstance();
                    System.out.println(imageData.getPixelsInfo());
                }
                //else abre la siguiente ventana
            }
        });

    }

    public boolean isTransparent( int pixel ) {
        if( (pixel>>24) == 0x00 ) {
            return true;
        }
        return false;
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

}
